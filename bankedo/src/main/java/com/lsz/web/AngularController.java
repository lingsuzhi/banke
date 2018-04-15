package com.lsz.web;

import com.lsz.common.SoaConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
@RequestMapping("/angular")
public class AngularController {
    protected static Logger log = LoggerFactory.getLogger(AngularController.class);

    @RequestMapping("/test.php")
    public String face(Model model){
        return "angular";
    }

}
