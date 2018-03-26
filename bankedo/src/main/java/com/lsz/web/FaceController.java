package com.lsz.web;

import com.lsz.common.SoaConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
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
@Controller("/face")
public class FaceController {
    protected static Logger log = LoggerFactory.getLogger(FaceController.class);

       @RequestMapping("/face.php")
    public String face(Model model){
        return "face";
    }
    @PostMapping("/facedo")
    @ResponseBody
    public Object faceDo( @RequestBody Map<String,String> map){
        String url = map.get("url");
        String post = map.get("post");
        String data = map.get("data");

        ResponseEntity<String>  entity = SoaConnectionFactory.goFase(url,post,data);
        return entity;
    }
}
