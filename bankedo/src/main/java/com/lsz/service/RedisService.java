package com.lsz.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by ex-lingsuzhi on 2018/3/30.
 */

@Service
public class RedisService {


    @Cacheable(value="user-key")
    public String getStr(){
        System.out.println("call getStr");
        return  "service str";
    }
}
