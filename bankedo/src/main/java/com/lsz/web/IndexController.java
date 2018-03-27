package com.lsz.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
public class IndexController {



    @RequestMapping("/index.php")
    public String index(Model model){
        return "index";
    }


}
