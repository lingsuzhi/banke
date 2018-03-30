package com.lsz.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ex-lingsuzhi on 2018/3/29.
 */
@Controller
@RequestMapping("/qrcode")
public class QrcodeController {
    protected static Logger log = LoggerFactory.getLogger(QrcodeController.class);
    @RequestMapping("/qrcode.php")
    public String face(){
        return "qrcode";
    }
}
