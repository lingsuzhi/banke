package com.lsz.web.bankeshell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ex-lingsuzhi on 2018/3/29.
 */
@Controller
@RequestMapping("/bankeshell")
public class DbpxyController {
    protected static Logger log = LoggerFactory.getLogger(DbpxyController.class);

    /**
     * http://127.0.0.1:8080/bankeshell/dbpxy.php
     * @return
     */
    @RequestMapping("/dbpxy.php")
    public String face(){
        return "bankeshell/dbpxy";
    }
}
