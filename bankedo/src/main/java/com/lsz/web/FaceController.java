package com.lsz.web;

import com.lsz.common.MSWordPoi4;
import com.lsz.common.SoaConnectionFactory;
import com.lsz.common.soa.ResponseInfo;
import com.lsz.model.bo.face.FacePostBO;
import com.lsz.model.bo.face.SavePostBO;
import com.lsz.service.SaveFacesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
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
@RequestMapping("/face")
public class FaceController {
    protected static Logger log = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    private SaveFacesService saveFacesService;

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
    public String savedo(@RequestBody SavePostBO savePostBO) {
        String postMan = saveFacesService.saveDo(savePostBO);
        return postMan;
    }

    /**
     * 打开json文件
     *
     * @param fileStr
     * @return
     */
    @RequestMapping("/opendo")
    public String opendo(@RequestParam String fileStr, Model model) {

        SavePostBO savePostBO = saveFacesService.openFile(fileStr);
        model.addAttribute("obj", savePostBO);
        return "face";
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

    @RequestMapping("/deldo")
    @ResponseBody
    public ResponseInfo<String> delFace(String name) {
        if (!StringUtils.isEmpty(name) && saveFacesService.deldo(name)) {
            return ResponseInfo.error("删除成功");
        }
        return ResponseInfo.success("删除失败");
    }

    /**
     * 文档
     *
     * @param name
     * @return
     */
    @RequestMapping("/docdo")
    public void docdo(String name, HttpServletResponse response) {
        if (!StringUtils.isEmpty(name)) {
            SavePostBO savePostBO = saveFacesService.getFileJsonPostBO(name);
            if(savePostBO == null){
               return;
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("${name}", savePostBO.getName());
            map.put("${url}", savePostBO.getUrl());
            map.put("${method}", savePostBO.getMethod());

            String head = savePostBO.getHead();
            if(!StringUtils.isEmpty(head)){
                head = head.replace("\t:\t" ,"\r\n");
            }
            map.put("${head}", head);

            map.put("${parameter}", savePostBO.getParameter());
            map.put("${returnStr}", savePostBO.getReturnStr());
            map.put("${describe}", savePostBO.getDescribe());

            String fileName = map.get("${name}") + "接口.doc";
            try {
                ByteArrayOutputStream outputStream = MSWordPoi4.readwriteWord("/file/face.doc", map);
                if (outputStream != null) {

                    response.reset();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("multipart/form-data");
                    response.setHeader("Content-Disposition",
                            "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                    OutputStream out = response.getOutputStream();
                    out.write(outputStream.toByteArray());
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
