package com.lsz.web;

import com.lsz.common.HtmlUtil;
import com.lsz.common.MD5Utils;
import com.lsz.common.MSWordPoi4;
import com.lsz.common.SoaConnectionFactory;
import com.lsz.common.soa.ResponseInfo;
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

    @RequestMapping("/face.php")
    public String face(@RequestParam String name, Model model) {
        if (!StringUtils.isEmpty(name)) {
            SavePostBO savePostBO = saveFacesService.getFileJsonPostBO(MD5Utils.decodeUtf8(name));
            if (savePostBO == null) {
                return null;
            }
            model.addAttribute("obj", savePostBO);
            openDoEx(savePostBO, model, null);
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
        model.addAttribute("pathName", MD5Utils.encodeUtf8(dirName + File.separator + "api" + File.separator + fileStr));
        if ("dto".equals(type)) {
            DtoBO dtoBO = saveFacesService.openDtoFile(fileStr, dirName, File.separator + "dto");

            if (dtoBO != null && !CollectionUtils.isEmpty(dtoBO.getAttrList())) {
                for (DtoAttrBO dtoAttrBO : dtoBO.getAttrList()) {
                    dtoAttrBO.setTypeStr(HtmlUtil.strToHtml(dtoAttrBO.getTypeStr()));
                }
            }
            if(dtoBO != null && StringUtils.isEmpty(dtoBO.getDescribe())){
                dtoBO.setDescribe(SaveFacesService.LazyDesc);
            }
            model.addAttribute("obj", dtoBO);
            return "dtoDoc";

        } else {

            SavePostBO savePostBO = saveFacesService.openFile(fileStr, dirName, File.separator + "api");
            List<String> dtoList = saveFacesService.getDtoList(dirName);
            savePostBO.setReturnTypeStr(HtmlUtil.strToHtml(savePostBO.getReturnTypeStr()));
            model.addAttribute("obj", savePostBO);

            if (!StringUtils.isEmpty(savePostBO.getReturnTypeStr())) {
                String tmpStr = savePostBO.getReturnTypeStr().replace("ResponseInfo", "")

                        .replace("List&lt;", "")
                        .replace("&lt;", "")
                        .replace("&gt;", "");
                if (dtoList.contains(tmpStr)) {
                    savePostBO.setReturnTypeStr(addHtmlA(savePostBO.getReturnTypeStr(), savePostBO.getProjectName(), tmpStr));
                }
            }
            openDoEx(savePostBO, model, dtoList);
            return "faceDoc";
        }
    }

    private String addHtmlA(String str, String projectName, String fileStr) {
        String bfz5c = "%5C";
        if (File.separator.equals("\\")) {
        } else {
            bfz5c = "%2F";
        }
        return "<a class=\"myabq\" href=\"javascript:;\" target=\"_blank\" a-href=\"/face/opendo?type=dto&fileStr=" + fileStr + bfz5c + fileStr + ".json&dirName=" + projectName + "\">" + str + "</a>";
    }

    private void openDoEx(SavePostBO savePostBO, Model model, List<String> dtoList) {
        String param = savePostBO.getParameterRem();
        List<ParameBO> list = new ArrayList();
        if (!StringUtils.isEmpty(param)) {
            String[] sArr = param.split("\r\n");
            for (String s : sArr) {
                String[] colArr = s.split("\t\t");
                if (colArr.length >= 3) {
                    ParameBO parameBO = new ParameBO(colArr[0], colArr[1], colArr[2], "");
                    if (colArr.length > 3) {
                        parameBO.setParameRem(colArr[3]);
                    }
                    if (!CollectionUtils.isEmpty(dtoList) && dtoList.contains(parameBO.getParameType())) {
                        parameBO.setParameType(addHtmlA(parameBO.getParameType(), savePostBO.getProjectName(), parameBO.getParameType()));
                    }

                    list.add(parameBO);
                }
            }
        }
        if (list.size() > 0) {
            model.addAttribute("parameList", list);
        }else{
            if(StringUtils.isEmpty(savePostBO.getParameterRem())){
                savePostBO.setParameterRem("无");
            }
        }
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
     * @param name
     * @return
     */
    @RequestMapping("/docdo")
    public void docdo(String name, HttpServletResponse response) {
        if (!StringUtils.isEmpty(name)) {
            SavePostBO savePostBO = saveFacesService.getFileJsonPostBO(MD5Utils.decodeUtf8(name));
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
