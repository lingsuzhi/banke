package com.lsz.web;

import com.lsz.common.HtmlUtil;
import com.lsz.common.MD5Utils;
import com.lsz.common.MSWordPoi4;
import com.lsz.common.SoaConnectionFactory;
import com.lsz.common.soa.ResponseInfo;
import com.lsz.dto.EditFaceDTO;
import com.lsz.dto.OpenDoDTO;
import com.lsz.model.bo.face.*;
import com.lsz.service.SaveFacesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
@RequestMapping("/face")
public class FaceController {
    protected static Logger log = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    private SaveFacesService saveFacesService;

    @RequestMapping("/writeRem")
    @ResponseBody
    public String writeRem(@RequestBody EditFaceDTO editFaceDTO) {
        saveFacesService.writeRem(editFaceDTO.getId(),editFaceDTO.getKey(),editFaceDTO.getContent());
        return "ok";
    }
    @RequestMapping("/editFace.php")
    public String editFace(@RequestParam String key,@RequestParam  String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("key", key);
        String content = saveFacesService.readRem(id,key);
        if(!StringUtils.isEmpty(content)){
            model.addAttribute("content", content);
        }
        return "editFace";
    }
    @RequestMapping("/face.php")
    public String face(@RequestParam String tid, Model model) {
        if (!StringUtils.isEmpty(tid)) {
            SavePostBO savePostBO = saveFacesService.tidGetSavePostBo(tid);
            if (savePostBO == null) {
                return null;
            }
            model.addAttribute("obj", savePostBO);
            saveFacesService.openDoEx(savePostBO, model, null);
        }
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
    public String opendo(@RequestParam String fileStr, @RequestParam String dirName, String type, Model model) {
        fileStr = saveFacesService.fileStrDo(fileStr,dirName,File.separator +  type);
        return saveFacesService.opendo(fileStr,dirName,type,model);
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

    private String wordNToRN(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        return s
                .replace("false\t", "否\t")
                .replace("true\t", "是\t");
    }

    /**
     * 文档
     *
     * @param tid
     * @return
     */
    @RequestMapping("/docdo")
    public void docdo(String tid, HttpServletResponse response) {
        if (!StringUtils.isEmpty(tid)) {
            SavePostBO savePostBO = saveFacesService.tidGetSavePostBo(tid);
            if (savePostBO == null) {
                return;
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("${name}", savePostBO.getName());
            map.put("${url}", savePostBO.getUrl());
            map.put("${method}", savePostBO.getMethod());
            map.put("${parameterRem}", wordNToRN(savePostBO.getParameterRem()));

            String head = savePostBO.getHead();
            if (!StringUtils.isEmpty(head)) {
                head = head.replace("\t:\t", "\r\n");
            }
            map.put("${head}", head);

            map.put("${parameter}", savePostBO.getParameter());
            String returnStr = savePostBO.getReturnStr();
            if (!StringUtils.isEmpty(returnStr)) {
                returnStr = returnStr.replace("\n", "\r\n");
            }
            map.put("${returnStr}", returnStr);
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

    /**
     * 批量生成
     *
     * @param map
     * @return
     */
    @PostMapping("/batchGenerate")
    @ResponseBody
    public ResponseInfo<String> batchGenerate(@RequestBody Map<String, String> map) {
        String path = map.get("path");
        saveFacesService.batchGenerateDo(path);
        return ResponseInfo.success("成功");
    }
}
