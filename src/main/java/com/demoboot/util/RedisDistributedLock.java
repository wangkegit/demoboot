package com.demoboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import javax.annotation.Resource;
import java.util.*;

@Component
public class RedisDistributedLock {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    public boolean setLock(String key, long expire) {
        try {
            RedisCallback<String> callback = (connection) -> {
                Jedis jedis=new Jedis("127.0.0.1",6379);
                String uuid = UUID.randomUUID().toString();
                return jedis.set(key, uuid, "NX", "PX", expire);
            };
            String result = redisTemplate.execute(callback);

            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }

    public String get(String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                Jedis jedis=new Jedis("127.0.0.1",6379);
                return jedis.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            logger.error("get redis occured an exception", e);
        }
        return "";
    }


    private static final Long lockReleaseOK = 1L;
    static String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";// lua脚本，用来释放分布式锁

    public static boolean releaseLock(String key ,String lockValue){
        if(key == null || lockValue == null) {
            return false;
        }
        try {
            Jedis jedis=new Jedis("127.0.0.1",6379);
            Object res =jedis.eval(luaScript, Collections.singletonList(key),Collections.singletonList(lockValue));
            jedis.close();
            return res!=null && res.equals(lockReleaseOK);
        } catch (Exception e) {
            return false;
        }
    }


}