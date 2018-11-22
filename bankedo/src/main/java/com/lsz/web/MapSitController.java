package com.lsz.web;

import com.lsz.common.MSWordPoi4;
import com.lsz.common.SoaConnectionFactory;
import com.lsz.common.soa.ResponseInfo;
import com.lsz.dto.EditFaceDTO;
import com.lsz.model.bo.face.FacePostBO;
import com.lsz.model.bo.face.SavePostBO;
import com.lsz.service.SaveFacesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
@RequestMapping("/map")
public class MapSitController {
    protected static Logger log = LoggerFactory.getLogger(MapSitController.class);

    @Autowired
    private SaveFacesService saveFacesService;



    @GetMapping("/sit")
    public String faceDo(@RequestParam  String userId, Model model) {
        FacePostBO facePostBO = new FacePostBO();
        facePostBO.setData("{\"userId\":\""+userId+"\",\"ipAddress\":\"lszzz\",\"encPwd\":\"JGam0xdMqhd9S8GZrBtcBwMr5HLG6bde3yUSaurRJtpt+5ENN8HBJGk+WlWGmqQN9CrkZvIBT8/cmWmuQ3fTg35Kx1hHLJQOOpSmrK0Ic2nLU1kHYo2hA2v8nR8gz78+t5aS5A1HnKXh2XSDm6vVnVP13CQsxLhNffr508R07gw=\"}");
        facePostBO.setPost("POST");
        facePostBO.setUrl("http://pxy-ma-sit2.banketech.com/api/login");
        ResponseEntity<String> entity = SoaConnectionFactory.goFase(
                facePostBO.getUrl(), facePostBO.getPost(), facePostBO.getData(), facePostBO.getHead());
        if (entity != null){
            String json = entity.getBody();
            model.addAttribute("json",json);
        }
        return "/mapsit/token";
    }


}
