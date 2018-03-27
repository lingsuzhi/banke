package com.lsz.web;

import com.lsz.mapper.UserDao;
import com.lsz.model.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/1.php")
    @ResponseBody
    public ResponseEntity customerList(HttpServletRequest request, Model model) {
        return ResponseEntity.ok("成功123");
    }

    @RequestMapping("/freemarker")
    public String freemarker(Map<String, Object> map){
        map.put("name", "Joe");
        map.put("sex", 1);    //sex:性别，1：男；0：女；

        // 模拟数据
        List<Map<String, Object>> friends = new ArrayList<Map<String, Object>>();
        Map<String, Object> friend = new HashMap<String, Object>();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = new HashMap<String, Object>();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
        return "freemarker";
    }

    @Autowired
    private UserDao userDao;

    @RequestMapping("/mybatis")
    @ResponseBody
    public User hello(@RequestParam String id){
        User user = userDao.getUserById(id);
        System.out.println(user);
        return user;
    }
}
