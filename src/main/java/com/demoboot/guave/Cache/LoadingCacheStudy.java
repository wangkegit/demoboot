package com.demoboot.guave.Cache;

import com.demoboot.entity.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


import static com.google.common.base.Predicates.notNull;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LoadingCacheStudy {
    LoadingCache<String,User> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(5)
            .expireAfterAccess(300, TimeUnit.SECONDS)
            .build(getLoaderByUserName());

    private CacheLoader<String, User> getLoaderByUserName() {
        return new CacheLoader<String, User>() {
            @Override
            public User load(String key) throws Exception {
                return findUserByName(key);
            }
        };
    }

    @Test
    public  void  test1() throws ExecutionException, InterruptedException {

        User user = loadingCache.get("Davi");
        System.out.println(user);
        User user2 = loadingCache.get("Davi");
        System.out.println(user2);
        assertThat(user2,notNullValue());
        TimeUnit.MILLISECONDS.sleep(30);
    }

    private User findUserByName(final String name){
        System.out.println("load from  DB  By "+name);
        return  new User(name,name);
    }
}
