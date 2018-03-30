package com.lsz.web;

import com.lsz.model.bo.User;
import com.lsz.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * Created by ex-lingsuzhi on 2018/3/29.
 */
@Controller
@RequestMapping("/redis")
public class RedisController {
    protected static Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/setRedis.php")
    @ResponseBody
    public String face(){
        User user = new User();
        user.setName("lsz");
        user.setId("1");
        redisTemplate.opsForValue().set("lsztest",user,10,TimeUnit.SECONDS);
        return "设置完成";
    }
    @RequestMapping("/getRedis.php")
    @ResponseBody
    public String gerredis(){
        Object str = redisTemplate.opsForValue().get("lsztest") ;

        return "redis读取到的数据" + str.toString();
    }
    @RequestMapping("/get.php")
    @ResponseBody
    public String gerdis(){
        String str = redisService.getStr();

        return "redis读取到的数据" + str;
    }

}
