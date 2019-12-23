package com.demoboot.controller;

import com.demoboot.util.RedisDistributedLock;
import com.demoboot.util.RedisTemplateLock;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@RestController
public class TestLockController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Redisson redisson;
    @Autowired
    private RedisDistributedLock  jedisLock;
    @Autowired
    private RedisTemplateLock redisTemplateLock;

    private String redisTemplateKey="redisTemplateLockKey";
    private String jedisKey="jedisLockKey";
    private String lockKey="redissonLockKey";
    private static final int TIMEOUT= 10*1000;
    private Integer count;



    @RequestMapping("/redisTemplateLock")
    public void redisTemplateLock(){
        long time = System.currentTimeMillis()+TIMEOUT;
        try{
           if (redisTemplateLock.lock(redisTemplateKey, String.valueOf(time))){
               if (Integer.parseInt(stringRedisTemplate.opsForValue().get("myKey"))> 0) {
                   count= Math.toIntExact((stringRedisTemplate.opsForValue().increment("myKey", -1L)));
                   System.out.println(Thread.currentThread().getName() + "抢票成功，剩余" + count+ "张票");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               }else {
                   System.out.println("车票已售罄！！！");
               }
           }else {
               redisTemplateLock();
           }
        }finally {
            redisTemplateLock.unlock(redisTemplateKey, String.valueOf(time));
        }
    }



    @RequestMapping("/JedisLock")
    public void jedisLock(){
        String uuid = UUID.randomUUID().toString();
        Jedis jedis=new Jedis("127.0.0.1",6379);
        try{
            if(jedisLock.setLock(jedisKey,10L)) {
                if (Integer.parseInt(stringRedisTemplate.opsForValue().get("myKey"))> 0) {
                    count= Math.toIntExact((stringRedisTemplate.opsForValue().increment("myKey", -1L)));
                    System.out.println(Thread.currentThread().getName() + "抢票成功，剩余" + count+ "张票");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("车票已售罄！！！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            String value=jedisLock.get(jedisKey);
            jedisLock.releaseLock( jedisKey,value);
        }
    }


    @RequestMapping("/redissonLock")
    public void redissonLock(){
        RLock lock=redisson.getLock(lockKey);
        try{
            lock.lock();
            count= Math.toIntExact((stringRedisTemplate.opsForValue().increment("myKey", -1L)));
            if (count > 0) {
                System.out.println(Thread.currentThread().getName() + "抢票成功，剩余" + count+ "张票");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("车票已售罄！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
