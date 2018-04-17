package com.lsz.web;

import com.lsz.common.SoaConnectionFactory;
import com.lsz.model.bo.face.FacePostBO;
import com.lsz.model.bo.face.PostMan;
import com.lsz.service.SaveFaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
@RequestMapping("/face")
public class FaceController {
    protected static Logger log = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    private SaveFaceService saveFaceService;

    @RequestMapping("/face.php")
    public String face(Model model) {
        return "face";
    }

    @PostMapping("/facedo")
    @ResponseBody
    public Object faceDo(@RequestBody FacePostBO facePostBO) {
        ResponseEntity<String> entity = SoaConnectionFactory.goFase(
                facePostBO.getUrl(), facePostBO.getPost(), facePostBO.getData(), facePostBO.getHead());
        return entity;
    }

    @PostMapping("/savedo")
    @ResponseBody
    public PostMan savedo(@RequestBody FacePostBO facePostBO) {
        PostMan  postMan =  saveFaceService.saveDo(facePostBO);
        saveFaceService.saveFile(postMan);
        return postMan;
    }

    @PostMapping("/facetest")
    @ResponseBody
    public Object facetest(HttpServletRequest request, @RequestBody Map<String, String> map) {
        String s = request.getHeader("head");
        log.info(s);
        ResponseEntity entity = ResponseEntity.ok(map);
        return entity;
    }

    @RequestMapping("/facetest2")
    @ResponseBody
    public Object facetest2(HttpServletRequest request) {

        Enumeration<String> head = request.getHeaderNames();
        String s = request.getHeader("head");
        ResponseEntity entity = ResponseEntity.ok(s);
        return entity;
    }
}
