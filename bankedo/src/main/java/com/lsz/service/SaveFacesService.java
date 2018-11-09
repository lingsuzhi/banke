package com.lsz.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsz.common.FileUtils;
import com.lsz.common.HtmlUtil;
import com.lsz.common.MD5Utils;
import com.lsz.dto.FileTypeEnum;
import com.lsz.dto.OpenDoDTO;
import com.lsz.dto.RemPostDTO;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.model.bo.face.DtoAttrBO;
import com.lsz.model.bo.face.DtoBO;
import com.lsz.model.bo.face.ParameBO;
import com.lsz.model.bo.face.SavePostBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
@Service
@Slf4j
public class SaveFacesService {

    private static final String FILE_MAP_NAME = "urlmap.txt";
    public static String FileDirP = "post";
    private static String FileDirStr = "";
    private static List<OpenDoDTO> FileMap = null;
    public static final String LazyDesc = "这个人很懒，什么都没没没有留下~！";
    public static Map<String, String> baseTypeMap;

    static {
        if (File.separator.equals("\\")) {
            FileDirP = "d:\\post";
        }
        String json = FileUtils.FileUTF8ToStr(new File(FileDirP + File.separator + FILE_MAP_NAME));
        if (!StringUtils.isEmpty(json)) {
            try {
                FileMap = JSONObject.parseArray(json, OpenDoDTO.class);
            } catch (Exception e) {
                log.info("SaveFacesService  static Exception ....");
            }
        }
        baseTypeMap = new HashMap<>();
        baseTypeMap.put("String", "字符串");
//        baseTypeMap.put("int", "整型");
//        baseTypeMap.put("Integer", "整型");
//        baseTypeMap.put("long", "长整型");
//        baseTypeMap.put("Long", "长整型");
//        baseTypeMap.put("Boolean", "布尔型");
//        baseTypeMap.put("boolean", "布尔型");

//        baseTypeMap.put("double", "浮点型");
//        baseTypeMap.put("Double", "浮点型");
//        baseTypeMap.put("Date", "Date时间");
        baseTypeMap.put("LocalDateTime", "LocalDateTime 时间");
    }

    public String opendo(String fileStr, String dirName, String type, Model model) {
        if ("dto".equals(type)) {
            DtoBO dtoBO = openDtoFile(fileStr);

            if (dtoBO != null && !CollectionUtils.isEmpty(dtoBO.getAttrList())) {
                for (DtoAttrBO dtoAttrBO : dtoBO.getAttrList()) {
                    dtoAttrBO.setTypeStr(HtmlUtil.strToHtml(dtoAttrBO.getTypeStr()));

                    String tmpType = baseTypeMap.get(dtoAttrBO.getTypeStr());
                    //基础类型替换成中文
                    if (!StringUtils.isEmpty(tmpType)) {
                        dtoAttrBO.setTypeStr(tmpType);
                    }
                }
            }
            if (dtoBO != null && StringUtils.isEmpty(dtoBO.getDescribe())) {
                dtoBO.setDescribe(SaveFacesService.LazyDesc);
            }
            model.addAttribute("obj", dtoBO);
            return "dtoDoc";

        } else {

            SavePostBO savePostBO = openFile(fileStr);
            List<String> dtoList = getDtoList(dirName);
            savePostBO.setReturnTypeStr(HtmlUtil.strToHtml(savePostBO.getReturnTypeStr()));
            model.addAttribute("obj", savePostBO);

            if (!StringUtils.isEmpty(savePostBO.getReturnTypeStr())) {
                final String resStr = "ResponseInfo&lt;";
                String typeStr = savePostBO.getReturnTypeStr();
                if(typeStr.startsWith("ResponseInfo&lt;")){
                    typeStr = typeStr.substring(resStr.length());
                    String endStr = "&gt;";
                    if(typeStr.endsWith(endStr)){
                        typeStr = typeStr.substring(0,typeStr.length() -  endStr.length());
                        savePostBO.setReturnTypeStr(typeStr);
                    }
                }

                if ("?".equals(savePostBO.getReturnTypeStr())) {
                    savePostBO.setReturnTypeStr("");
                    if (StringUtils.isEmpty(savePostBO.getReturnStr())) {
                        savePostBO.setReturnStr(SaveFacesService.LazyDesc);
                    }
                } else {
                    String tmpStr = savePostBO.getReturnTypeStr().replace("ResponseInfo", "")
                            .replace("Page&lt;", "")
                            .replace("List&lt;", "")
                            .replace("&lt;", "")
                            .replace("&gt;", "");
                    if (dtoList.contains(tmpStr)) {
                        savePostBO.setReturnTypeStr(addHtmlA(savePostBO.getReturnTypeStr(), savePostBO.getProjectName(), tmpStr));
                    }
                }

            }
            openDoEx(savePostBO, model, dtoList);
            openDoRem(savePostBO.getId(),model);
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

    public void openDoEx(SavePostBO savePostBO, Model model, List<String> dtoList) {
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
                    String tmpType = baseTypeMap.get(parameBO.getParameType());
                    //基础类型替换成中文
                    if (!StringUtils.isEmpty(tmpType)) {
                        parameBO.setParameType(tmpType);
                    } else if (!CollectionUtils.isEmpty(dtoList) && dtoList.contains(parameBO.getParameType())) {

                        parameBO.setParameType(addHtmlA(parameBO.getParameType(), savePostBO.getProjectName(), parameBO.getParameType()));
                    }

                    list.add(parameBO);
                }
            }
        }
        if (list.size() > 0) {
            model.addAttribute("parameList", list);
        } else {
            if (StringUtils.isEmpty(savePostBO.getParameterRem())) {
                savePostBO.setParameterRem("无");
            }
        }
    }

    public OpenDoDTO getMappingStr(String key) {
        if (FileMap == null || StringUtils.isEmpty(key)) {
            return null;
        }
        for (OpenDoDTO openDoDTO : FileMap) {
            if (key.equals(openDoDTO.getId())) {
                return openDoDTO;
            }
        }
        return null;
    }

    public String getHeadMenu() {
//          <li class="layui-nav-item"><a href="">控制台</a></li>
//            <li class="layui-nav-item"><a href="">商品管理</a></li>
//            <li class="layui-nav-item"><a href="">用户</a></li>
//            <li class="layui-nav-item">
//                <a href="javascript:;">其它系统</a>
//                <dl class="layui-nav-child">
//                    <dd><a href="">邮件管理</a></dd>
//                    <dd><a href="">消息管理</a></dd>
//                    <dd><a href="">授权管理</a></dd>
//                </dl>
//            </li>
        String returnStr = "";
        File file = new File(FileDirP);
        if (!file.exists()) {
            return returnStr;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return returnStr;
        }
        List<String> list = new ArrayList();
        for (File f : files) {
            if (f.isDirectory()) {
                list.add(f.getName());
            }
        }
        File fileHead = new File(FileDirP + File.separator + "head.json");
        if (fileHead.exists()) {
            String json = FileUtils.FileUTF8ToStr(fileHead);
            JSONArray jsonArray = null;
            try {
                jsonArray = JSONObject.parseArray(json);

            } catch (Exception e) {
                return null;
            }
            for (Object obj : jsonArray) {
                if (obj instanceof String) {
                    String name = headListDo(list, (String) obj);
                    if (!StringUtils.isEmpty(name)) {
                        returnStr += strDo1(name);
                    }

                } else if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    for (Object ob : jsonObject.keySet()) {
                        Object map = jsonObject.get(ob);


                        if (map instanceof JSONArray) {
                            JSONArray hashMap = (JSONArray) map;
                            returnStr += "<li class=\"layui-nav-item\">";
                            returnStr += "<a href=\"javascript:;\">" + ob.toString() + "</a>";
                            returnStr += "<dl class=\"layui-nav-child\">";
                            for (Object entry : hashMap) {

                                String sVal = entry.toString();

                                String name = headListDo(list, sVal);
                                if (!StringUtils.isEmpty(name)) {
                                    returnStr += strDo2(name);
                                }


                            }

                            returnStr += "</dl>";
                            returnStr += "</li>";
                        }
                    }
                }
            }

            if (list.size() > 0) {
                returnStr += "<li class=\"layui-nav-item\">";
                returnStr += "<a href=\"javascript:;\">其它系统</a>";
                returnStr += "<dl class=\"layui-nav-child\">";
                for (int i = 0; i < 500; i++) {
                    if (i >= list.size()) {
                        break;
                    }
                    String name = list.get(i);
                    returnStr += "<dd><a href=\"javascript:layui.app.funSetMenu('" + name + "')" + "\">" + name + "</a></dd>\r\n";
                }
                returnStr += "</dl>";
                returnStr += "</li>";
            }
        } else {

            for (int i = 0; i < 10; i++) {
                if (i >= list.size()) {
                    break;
                }
                String name = list.get(i);
                returnStr += strDo1(name);
            }
            if (list.size() > 10) {
                returnStr += "<li class=\"layui-nav-item\">";
                returnStr += "<a href=\"javascript:;\">其它系统</a>";
                returnStr += "<dl class=\"layui-nav-child\">";
                for (int i = 10; i < 500; i++) {
                    if (i >= list.size()) {
                        break;
                    }
                    String name = list.get(i);
                    returnStr += "<dd><a href=\"javascript:layui.app.funSetMenu('" + name + "')" + "\">" + name + "</a></dd>\r\n";
                }
                returnStr += "</dl>";
                returnStr += "</li>";
            }

        }
        return returnStr;
    }

    private String strDo2(String name) {
        return "<dd><a href=\"javascript:layui.app.funSetMenu('" + name + "')" + "\">" + name + "</a></dd>\r\n";
    }

    private String strDo1(String name) {
        return "<li class=\"layui-nav-item\"><a href=\"javascript:layui.app.funSetMenu('" + name + "')" + "\">" + name + "</a></li>\r\n";
    }

    private String headListDo(List<String> list, String str) {
        for (String s : list) {
            if (str.equals(s)) {
                list.remove(s);
                return s;
            }
        }
        return null;
    }

    public String saveDo(SavePostBO savePostBO) {
        return saveDo(savePostBO, "Default");
    }

    public String saveFileMap() {
        String json = JSONObject.toJSONString(FileMap);
        FileUtils.strToFileUTF8(FileDirP + File.separator + FILE_MAP_NAME, json);
        return "ok";
    }

    public String saveDo(SavePostBO savePostBO, String dirName) {
        if (StringUtils.isEmpty(savePostBO.getName())) {
            log.info("saveDo异常 name不能为空");
            return "";
        }
        savePostBO.setName(fileNameDo(savePostBO.getName()));
        String path = FileDirStr + File.separator + dirName;
        checkFilePath(path);
        String json = JSONObject.toJSONString(savePostBO);
        String name = savePostBO.getName();
        String fileName = path + File.separator + name + ".json";
        saveFile(fileName, json, savePostBO.getId(), FileTypeEnum.api, savePostBO.getProjectName());
        return "ok";
    }

    private void saveFile(String fileName, String json, String id, FileTypeEnum type, String projectName) {
        FileUtils.strToFileUTF8(fileName, json);
        FileMap.add(new OpenDoDTO(id, fileName, type.toString(), projectName));
    }

    private static String fileNameDo(String filename) {
        return filename.replace("*", "")
                .replace("/", "")
                .replace("\\", "")
                .replace("\"", "")
                .replace(":", "")
                .replace("?", "")
                .replace("|", "")
                .replace("@", "")
                .replace("<", "")
                .replace(">", "");
    }

    public String saveDoDto(DtoBO dtoBO, String dirName) {
        if (StringUtils.isEmpty(dtoBO.getName())) {
            log.info("saveDoDto name不能为空");
            return "";
        }
        dtoBO.setName(fileNameDo(dtoBO.getName()));
        String path = FileDirStr + File.separator + dirName;
        checkFilePath(path);
        String json = JSONObject.toJSONString(dtoBO);
        String name = dtoBO.getName();
        String fileName = path + File.separator + name + ".json";
        saveFile(fileName, json, dtoBO.getId(), FileTypeEnum.dto, dtoBO.getProjectName());
        return "ok";
    }

    private void checkFilePath(String fileDir) {
        File file = new File(fileDir);
        if (file.exists() && file.isDirectory()) {
            //文件夹存在
        } else {
            file.mkdirs();
        }

    }

    public SavePostBO openFileEx(String fileStr, String dirName, String apiDir) {
        final String finalStr = "fileStr=";
        int pos = fileStr.indexOf(finalStr);
        if (pos == -1) return null;
        int pos2 = fileStr.indexOf("&", pos);
        if (pos2 < pos) {
            pos2 = fileStr.length();
        }
        return openFile(fileStr.substring(pos + finalStr.length(), pos2), dirName, apiDir);
    }

    /**
     * @param fileStr
     * @param dirName
     * @param apiDir  带斜杠的  /api
     * @return
     */
    public SavePostBO openFile(String fileStr, String dirName, String apiDir) {
        fileStr = MD5Utils.decodeUtf8(fileStr);
        return openFile(FileDirP + File.separator + dirName + apiDir + File.separator + fileStr);
    }

    public SavePostBO openFile(String fileStr) {
        File file = new File(fileStr);
        if (file.exists()) {
            String fileS = FileUtils.FileUTF8ToStr(file);
            if (!StringUtils.isEmpty(fileS)) {
                SavePostBO savePostBO = JSONObject.parseObject(fileS, SavePostBO.class);
                return savePostBO;
            }
        }
        return null;
    }

    public List<String> getDtoList(String dirName) {
        List<String> list = new ArrayList<>();
        File file = new File(FileDirP + File.separator + dirName + File.separator + "dto");
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                list.add(f.getName());

            }
            return list;
        }
        return null;
    }

    public String fileStrDo(String fileStr, String dirName, String type) {
        fileStr = MD5Utils.decodeUtf8(fileStr);
        return (FileDirP + File.separator + dirName + type + File.separator + fileStr);
    }

    public DtoBO openDtoFile(String fileStr) {
        File file = new File(fileStr);
        if (file.exists()) {
            String fileS = FileUtils.FileUTF8ToStr(file);
            if (!StringUtils.isEmpty(fileS)) {
                DtoBO dtoBO = JSONObject.parseObject(fileS, DtoBO.class);
                return dtoBO;
            }
        }
        return null;
    }

    public boolean deldo(String name) {
        File file = new File(FileDirP + File.separator + name + ".json");
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public String getFileJson(String name) {
        String str = "";
        File file = new File(FileDirP + File.separator + name);
        if (file.exists()) {
            str = FileUtils.FileUTF8ToStr(file);
        }
        return str;
    }

    public SavePostBO getFileJsonPostBO(String name) {
        String str = getFileJson(name);
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SavePostBO savePostBO = JSONObject.parseObject(str, SavePostBO.class);
        return savePostBO;
    }

    public LayuiNavbarBO navbarFind(String txtUrl, String txtName, String projectName, String findUrl) {
        List<LayuiNavbarBO> list = getNavbar(projectName, "api");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<LayuiNavbarBO> tmpList = new LinkedList<>();
        for (int fori = 0; fori < list.size(); fori++) {
            LayuiNavbarBO layuiNavbarBO = list.get(fori);

            // tmpList.add(layuiNavbarBO);
            List<LayuiNavbarBO> childList = layuiNavbarBO.getChildren();
            for (LayuiNavbarBO obj : childList) {
                tmpList.add(obj);
            }
        }
        List<LayuiNavbarBO> tmpList2 = null;
        if (!StringUtils.isEmpty(findUrl)) {
            LayuiNavbarBO tmpLayuiNavbarBO = null;
            for (LayuiNavbarBO layuiNavbarBO : tmpList) {
                if (tmpLayuiNavbarBO != null) {
                    tmpList2.add(layuiNavbarBO);
                }
                if (findUrl.equals(layuiNavbarBO.getUrl())) {

                    tmpList2 = new LinkedList<>();
                    tmpLayuiNavbarBO = layuiNavbarBO;
                }

            }
            if (tmpList2 != null) {
                for (LayuiNavbarBO layuiNavbarBO : tmpList) {
                    if (layuiNavbarBO == tmpLayuiNavbarBO) {
                        break;
                    }
                    tmpList2.add(layuiNavbarBO);
                }
            }
            tmpList = tmpList2;
        }

        if (tmpList.size() != 0) {
            for (LayuiNavbarBO layuiNavbarBO : tmpList) {
                if (!StringUtils.isEmpty(txtName)) {
                    if (StringUtils.isEmpty(layuiNavbarBO.getTitle())) {
                        continue;
                    }
                    int posName = layuiNavbarBO.getTitle().indexOf(txtName);
                    if (posName == -1) {
                        continue;
                    }

                }
                if (!StringUtils.isEmpty(txtUrl)) {
                    if (StringUtils.isEmpty(layuiNavbarBO.getUrl())) {
                        continue;
                    }
                    SavePostBO savePostBO = openFileEx(layuiNavbarBO.getUrl(), projectName, File.separator + "api");
                    if (savePostBO == null) {
                        continue;
                    }
                    int posUrl = savePostBO.getUrl().indexOf(txtUrl);
                    if (posUrl == -1) {
                        continue;
                    }

                }
                return layuiNavbarBO;

            }
        }
        return null;
    }

    public List<LayuiNavbarBO> getNavbar(String dirName, String typeName) {
        File dir = new File(FileDirP + File.separator + dirName + File.separator + typeName);
        List<LayuiNavbarBO> list = new LinkedList<>();
        if (!dir.exists()) {
            list.add(new LayuiNavbarBO());
            return list;
        }

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            list.add(new LayuiNavbarBO());
            return list;
        }
        int id = 0;
        boolean dirB1 = true;
        for (File file : files) {
            id += 12;
            LayuiNavbarBO layuiNavbarBO = new LayuiNavbarBO();
            if (file.isDirectory()) {
                layuiNavbarBO.setSpread(false);
                layuiNavbarBO.setTitle(file.getName());
                layuiNavbarBO.setIcon("fa-cubes");
                layuiNavbarBO.setId(dirName + String.valueOf(id) + typeName);
                if (dirB1) {
                    layuiNavbarBO.setSpread(false);
                    dirB1 = !dirB1;
                }
                File[] files2 = file.listFiles();
                if (files2 == null || files2.length == 0) {
                    //空文件夹不要
                    continue;
                }
                for (File file2 : files2) {
                    id++;
                    if (file2.isDirectory()) {
//文件夹不处理，只支持2级菜单
                    } else {
                        /////
                        LayuiNavbarBO layuiNavbarBO2 = new LayuiNavbarBO();
                        String fileStr = file.getName() + File.separator + file2.getName();


                        layuiNavbarBO2.setUrl("face/opendo?type=" + typeName + "&fileStr=" + MD5Utils.encodeUtf8(fileStr) + "&dirName=" + dirName);
                        layuiNavbarBO2.setSpread(true);
                        layuiNavbarBO2.setTitleEx(file2.getName());
                        layuiNavbarBO2.setIcon("fa-stop-circle");
                        layuiNavbarBO2.setId(dirName + String.valueOf(id) + typeName);
                        layuiNavbarBO.getChildren().add(layuiNavbarBO2);
                    }

                }

            } else {
                layuiNavbarBO.setUrl("face/opendo?type=" + typeName + "&fileStr=" + MD5Utils.encodeUtf8(file.getName()));
                layuiNavbarBO.setSpread(true);
                layuiNavbarBO.setTitleEx(file.getName());
                layuiNavbarBO.setIcon("fa-stop-circle");
                layuiNavbarBO.setId(dirName + String.valueOf(id) + typeName);
                //layuiNavbarBO.setChildren(null);
            }

            list.add(layuiNavbarBO);
        }
        //把Default目录 放到最前面 并且状态改为打开
        if (list != null && list.size() > 0) {
            for (LayuiNavbarBO layuiNavbarBO : list) {
                if ("Default".equals(layuiNavbarBO.getTitle())) {
                    list.remove(layuiNavbarBO);
                    layuiNavbarBO.setSpread(true);
                    list.add(0, layuiNavbarBO);
                    break;
                }
            }

        }

        if (list.size() == 0) {
            list.add(new LayuiNavbarBO());
        }
        return list;
    }

    public void batchGenerateDo(String path) {
        //过来的是一个源代码目录，里面有很多微服务
        FileMap = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        //对文件进行循环遍历
        for (File file : files) {
            doJavaFileDo(file);
            doJavaFileDoDto(file);
        }
        saveFileMap();
        log.info("执行完毕。。。");
    }

    public void doJavaFileDoDto(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        String apiPath = findPath(file.getAbsolutePath(), "dto");
        if (StringUtils.isEmpty(apiPath)) {
            log.info("路径:{} 找不到dto目录", file.getAbsolutePath());
            return;
        }
        FileDirStr = FileDirP + File.separator + file.getName() + File.separator + "dto";
        File dir = new File(FileDirStr);
        if (dir.exists()) {
            deleteDir(dir);
        }
        //创建项目文件夹
        dir.mkdirs();
        batchGenerateDto(apiPath, file.getName());
    }

    public void doJavaFileDo(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        String apiPath = findPath(file.getAbsolutePath(), "api");
        if (StringUtils.isEmpty(apiPath)) {
            log.info("路径:{} 找不到api目录", file.getAbsolutePath());
            return;
        }
        log.info("正在处理项目{}", file.getName());
        FileDirStr = FileDirP + File.separator + file.getName() + File.separator + "api";
        File dir = new File(FileDirStr);
        if (dir.exists()) {
            deleteDir(dir);
        }
        //创建项目文件夹
        dir.mkdirs();
        batchGenerate(apiPath, file.getName());
    }

    public String findPath(String path, String dirName) {
        sFindPath = "";
        traverseFolder(path, dirName);
        return sFindPath;
    }

    public void batchGenerate(String path, String projectName) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        //对文件进行循环遍历
        for (File file : files) {
            doJavaFile(file, projectName);
        }
    }

    public void batchGenerateDto(String path, String projectName) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        //对文件进行循环遍历
        for (File file : files) {
            doJavaFileDto(file, projectName);
        }
    }

    private void doJavaFile(File file, String projectName) {
        String className = file.getName();
        className = className.replace(".java", "");
        String tmpPath = FileDirP + File.separator + projectName + File.separator + "api";

        tmpPath = tmpPath + File.separator + className;
        //如果目录存在 则删除
        File dir = new File(tmpPath);
        if (dir.exists()) {
            deleteDir(dir);
        }
        //创建java api文件夹
        dir.mkdir();
        String fileStr = FileUtils.FileUTF8ToStr(file);
        if (StringUtils.isEmpty(fileStr)) return;
        final String keyVal = "Mapping(";
        boolean RequestMethod_GET =false;
        boolean RequestMethod_POST =false;

        int mappingPos1 = fileStr.indexOf(keyVal);
        if (mappingPos1 == -1) return;
        //原理：第一个 mapping 后面第一个引号的内容 就是控制器路径
        String classPathValue = getYinhao(fileStr, mappingPos1);

        String mapHeadStr = getEnterRow(fileStr, mappingPos1);
        if(!StringUtils.isEmpty(mapHeadStr)){
            if(mapHeadStr.contains(".GET")){
                RequestMethod_GET = true;
            }
            if(mapHeadStr.contains(".POST")){
                RequestMethod_POST = true;
            }
        }

        int mapPos = mappingPos1 + keyVal.length();
        while (true) {
            //循环往下找 mapping
            int tmpMapPos = fileStr.indexOf(keyVal, mapPos);
            if (tmpMapPos == -1) {
                //找不到 说明没了
                break;
            }
            SavePostBO savePostBO = doJavaFileEx(fileStr, tmpMapPos, mapPos);

            mapPos = tmpMapPos + keyVal.length();
            if (savePostBO != null && !StringUtils.isEmpty(savePostBO.getName())) {
                if(!"GET".equals(savePostBO.getMethod())){
                    if(!RequestMethod_GET){
                        RequestMethod_GET = true;
                    }
                }
                if(!"POST".equals(savePostBO.getMethod())){
                    if(!RequestMethod_POST){
                        RequestMethod_POST = true;
                    }
                }
                if(RequestMethod_GET){
                    savePostBO.setMethod("GET");
                }
                if(RequestMethod_POST){
                    if(RequestMethod_GET){
                        savePostBO.setMethod("GET、POST");
                    }else{
                        savePostBO.setMethod("POST");
                    }
                }
                String tmpUrl = classPathValue + savePostBO.getUrl();
                tmpUrl = tmpUrl.replace("//", "/");//有个小Bug

                savePostBO.setUrl(tmpUrl);
                savePostBO.setProjectName(projectName);

                savePostBO.setDescribe(savePostBO.getName());
                if (savePostBO.getName().length() > 12) {
                    savePostBO.setName(savePostBO.getName().substring(0, 12));
                }

                saveDo(savePostBO, className);
            }
        }

    }

    private void doJavaFileDto(File file, String projectName) {
        String className = file.getName();
        className = className.replace(".java", "");
        String tmpPath = FileDirP + File.separator + projectName + File.separator + "dto";

        tmpPath = tmpPath + File.separator + className;
        //如果目录存在 则删除
        File dir = new File(tmpPath);
        if (dir.exists()) {
            deleteDir(dir);
        }
        //创建java dto文件夹
        dir.mkdir();
        String fileStr = FileUtils.FileUTF8ToStr(file);
        if (StringUtils.isEmpty(fileStr)) return;


        final String classStr = " class ";
        int classPos = fileStr.indexOf(classStr);
        if (classPos == -1) return;
        int classdkhPos = fileStr.indexOf("{", classPos);
        if (classdkhPos == -1) return;
        //private
        int mapPos = classdkhPos;
        String keyVal = "private ";
        DtoBO dtoBO = new DtoBO();
        while (true) {
            //循环往下找 mapping
            int tmpMapPos = fileStr.indexOf(keyVal, mapPos);
            if (tmpMapPos == -1) {
                //找不到 说明没了
                break;
            }
            //找到了 private 同时行内有分号
            int fenhaoPos = fileStr.indexOf(";", tmpMapPos);
            int rowPos = fileStr.indexOf("\n", tmpMapPos);
            if (rowPos == -1) {
                break;
            }
            if (fenhaoPos != -1 && fenhaoPos < rowPos) {
                String tmpStr = fileStr.substring(tmpMapPos + keyVal.length(), fenhaoPos).trim();
                if (!StringUtils.isEmpty(tmpStr) && -1 == tmpStr.indexOf("static ") && -1 == tmpStr.indexOf("final ")) {
                    int kgPos = tmpStr.indexOf(" ");
                    if (kgPos != -1) {
                        String typeStr = tmpStr.substring(0, kgPos);
                        String nameStr = tmpStr.substring(kgPos + 1);
                        String remStr = getRemStr(fileStr, mapPos, tmpMapPos);

                        DtoAttrBO dtoAttrBO = new DtoAttrBO();
                        int notNullPos = fileStr.indexOf("@NotNull", mapPos);
                        if (notNullPos != -1 && notNullPos < tmpMapPos) {
                            dtoAttrBO.setParameRequired("true");
                        } else {
                            dtoAttrBO.setParameRequired("false");
                        }
                        dtoAttrBO.setTypeStr(typeStr);
                        dtoAttrBO.setNameStr(nameStr);
                        dtoAttrBO.setRemStr(remStr);
                        dtoBO.getAttrList().add(dtoAttrBO);
                    }
                }

            }
            mapPos = tmpMapPos + keyVal.length();
        }//end while
        dtoBO.setName(file.getName().replace(".java", ""));
        if (dtoBO != null && !StringUtils.isEmpty(dtoBO.getName())) {

            dtoBO.setProjectName(projectName);

            dtoBO.setDescribe(getRemStr(fileStr, 0, classPos));
//                if (!StringUtils.isEmpty(dtoBO.getDescribe())) {
//                    dtoBO.setName(dtoBO.getDescribe().length() > 12 ? dtoBO.getDescribe().substring(0, 12) : dtoBO.getDescribe());
//                }
            saveDoDto(dtoBO, className);
        }
    }

    private static int findStrLast(String str, int pos, String findStr) {
        return str.substring(0, pos).lastIndexOf(findStr);
    }

    private String getRemStr(String codeStr, int leftPos, int rightPos) {
        int pos1 = findStrLast(codeStr, rightPos, "/*");
        int pos2 = findStrLast(codeStr, rightPos, "*/");
        String returnStr = "";
        if (pos1 != -1 && pos2 != -1 && pos1 > leftPos) {
            if (pos1 > pos2) return null;
            String zhujieStr = codeStr.substring(pos1, pos2);
            String[] sArr = zhujieStr.split("\n");

            if (sArr != null) {
                for (int i = 1; i < sArr.length - 1; i++) {
                    //第一行 最后一行 不要
                    String tmpS = sArr[i].trim();
                    if (tmpS.length() > 1 && "*".equals(tmpS.substring(0, 1))) {
                        tmpS = tmpS.substring(1).trim();
                    }
                    returnStr += tmpS;
                }
            }
        }
        //如果没有，就找双斜杠 //
        if (StringUtils.isEmpty(returnStr)) {
            int pos3 = findStrLast(codeStr, rightPos, "//");
            if (pos3 != -1 && pos3 > leftPos) {
                int endPos = codeStr.indexOf("\n", pos3);
                if (endPos > pos3 + 3) {
                    returnStr = codeStr.substring(pos3 + 2, endPos - 1);
                }
            }
        }
        return returnStr;
    }

    /**
     * @param fileStr
     * @param pos     mapping 的位置
     * @param leftPos 不能超过的位置
     * @return
     */
    private SavePostBO doJavaFileEx(String fileStr, int pos, int leftPos) {
        SavePostBO savePostBO = new SavePostBO();
        String url = getYinhao(fileStr, pos);
        savePostBO.setUrl(url);
        savePostBO.setMethod("GET");
        //获取这一行的值
        String rowStr = getEnterRow(fileStr, pos);
        if (rowStr != null) {
            if (rowStr.contains("RequestMethod.POST") || rowStr.contains("PostMapping")) {
                savePostBO.setMethod("POST");
            }
        }
        //找注释
        int pos1 = findStrLast(fileStr, pos, "/*");
        int pos2 = findStrLast(fileStr, pos, "*/");
        if (pos1 != -1 && pos2 != -1 && pos1 > leftPos) {
            if (pos1 > pos2) return null;
            String zhujieStr = fileStr.substring(pos1, pos2);
            String[] sArr = zhujieStr.split("\n");
            if (sArr != null) {
                String paramS = "";
                String returnS = "";
                String nameS = "";
                boolean tmpNameB = true;
                for (int i = 1; i < sArr.length - 1; i++) {
                    //第一行 最后一行 不要
                    String tmpS = sArr[i];
                    int paramPos = tmpS.indexOf("@param");
                    if (paramPos != -1) {
                        String paramStr = tmpS.substring(paramPos + 6).trim();
                        //第一个空格前面是 参数名 后面是 中文注释
                        int kgPos = paramStr.indexOf(" ");
                        if (kgPos != -1) {
                            String paramName = paramStr.substring(0, kgPos);
                            String paramRem = paramStr.substring(kgPos + 1);
                            paramS += paramName.trim() + "\t\t" + paramRem.trim() + "\r\n";
                        }
                        tmpNameB = false;
                        continue;
                    }
                    int returnPos = tmpS.indexOf("@return");
                    if (returnPos != -1) {
                        String returnStr = tmpS.substring(returnPos + 7);

                        returnS = returnStr.trim();
                        tmpNameB = false;
                        continue;
                    }
                    if (tmpNameB) {
                        nameS += tmpS.replace("* ", "").replace("\t", "").trim();

                    }

                }
                String paramStr = paramHandle(fileStr, pos, paramS);
                String returnTypeStr = returnHandle(fileStr, pos);

                savePostBO.setParameterRem(paramStr);
                savePostBO.setReturnTypeStr(returnTypeStr);
                savePostBO.setReturnStr(returnS);
                if (!StringUtils.isEmpty(nameS)) {
                    if ("*".equals(nameS.substring(nameS.length() - 1))) {
                        nameS = nameS.substring(0, nameS.length() - 1);
                    }
                }
                savePostBO.setName(nameS);
            }
        }
        return savePostBO;
    }

    /**
     * 处理返回类型
     *
     * @param fileStr
     * @param pos
     * @return
     */
    private String returnHandle(String fileStr, int pos) {
        int nPos = fileStr.indexOf("\n", pos);
        //这里获取返回类型     ResponseInfo<List<StaffInfoDTO>>
        if (nPos == -1) return "";
        int leftKuohao = fileStr.indexOf("(", nPos);
        if (leftKuohao == -1) return "";
        String returnType = fileStr.substring(nPos, leftKuohao - 1).trim();
        int konggePos = returnType.lastIndexOf(" ");
        if (konggePos == -1) {
            konggePos = returnType.lastIndexOf(">");
            if (konggePos != -1) {
                konggePos++;
            }
        }
        if (konggePos != -1) {
            returnType = returnType.substring(0, konggePos);
            final String publicStr = "public";
            int publicPos = returnType.indexOf(publicStr);
            if (publicPos != -1) {
                returnType = returnType.substring(publicPos + publicStr.length()).trim();
            }

        }
        return returnType;
    }

    /**
     * 处理参数
     *
     * @param fileStr 文件内容
     * @param pos     位置
     * @param paramS  注解读出来的参数描述
     * @return
     */
    private String paramHandle(String fileStr, int pos, String paramS) {
//        @RequestMapping("/api/staffInfo/queryStaffInfo")
//        ResponseInfo<List<StaffInfoDTO>> queryStaffInfo(@RequestParam(value = "roleId", required = false) String roleId,@RequestParam(value = "branchNo", required = false)String branchNo,
//                @RequestParam(value = "userId", required = false) String userid,@RequestParam(value= "staffName", required = false) String staffName );
        //pos 是 mapping的位置
        //第一步 根据mapping位置找到下一行的位置
        int nPos = fileStr.indexOf("\n", pos);
        //这里不考虑其他注解的情况 因为其他注解一般也不会有括号
        //然后开始找括号


        StringBuffer sb = new StringBuffer();
        if (nPos == -1) return paramS;
        int leftKuohao = fileStr.indexOf("(", nPos);
        if (leftKuohao == -1) return paramS;
        int leftK = 1;
        for (int fori = leftKuohao + 1; ; fori++) {
            if (fori >= fileStr.length()) break;
            String tmpS = fileStr.substring(fori, fori + 1);
            if ("(".equals(tmpS) || "<".equals(tmpS)) {
                leftK++;
            } else if (")".equals(tmpS) || ">".equals(tmpS)) {
                leftK--;
                if (leftK <= 0) {
                    break;
                }
            } else if (",".equals(tmpS)) {
                if (leftK > 1) {
                    tmpS = "，";
                }
            }
            sb.append(tmpS);
        }
        String sbStr = sb.toString().trim();
        if (StringUtils.isEmpty(sbStr)) {
            return paramS;
        }
        String[] sArr = sbStr.split(",");
        if (sArr == null || sArr.length == 0) {
            return paramS;
        }
        String[] paramOldArr = paramS.split("\r\n");
        StringBuffer returnSb = new StringBuffer();
        for (String s : sArr) {
            //@RequestParam(value = "roleId", required = false) String roleId
            s = s.trim();
            int posKG = s.lastIndexOf(" ");
            if (posKG == -1) {
                log.info("paramHandle找不到空格 {}", s);
                continue;
            }
            //最后一个空格
            String paramName = s.substring(posKG + 1);
            s = s.substring(0, posKG).trim();
            posKG = s.lastIndexOf(")");
            if (posKG == -1) {
                posKG = s.lastIndexOf(" ");
            }

            String paramType = s.substring(posKG + 1).trim();
            String required = "false";
            int tmpPos = s.indexOf("@RequestParam");
            if (tmpPos != -1) {
                tmpPos = s.replace(" ", "").indexOf("required=false");
                required = "true";
                if (tmpPos != -1) {
                    required = "false";
                }
            }
            String paramDec = "";
            if (paramOldArr.length > 0) {
                //说明
                for (String sParam : paramOldArr) {
                    String[] tmpArr = sParam.split("\t\t");
                    if (tmpArr.length >= 2) {
                        if (paramName.equals(tmpArr[0])) {
                            paramDec = tmpArr[1];
                            break;
                        }
                    }
                }
            }
            returnSb.append(paramName + "\t\t" + required + "\t\t" + paramType + "\t\t" + paramDec + "\r\n");
        }
        return returnSb.toString();
    }

    /**
     * 返回指定位置后面 第一个 引号 内的值
     *
     * @param str
     * @param pos
     * @return
     */
    private static String getYinhao(String str, int pos) {
        int pos1 = str.indexOf("\"", pos);
        if (pos1 == -1) return "";
        int pos2 = str.indexOf("\"", pos1 + 1);
        return str.substring(pos1 + 1, pos2);
    }

    /**
     * 获取这一行的值
     *
     * @param str
     * @param pos
     * @return
     */
    private static String getEnterRow(String str, int pos) {
        int pos1 = findStrLast(str, pos, "\n");
        if (pos1 == -1) return "";
        int pos2 = str.indexOf("\n", pos);
        return str.substring(pos1 + 2, pos2);
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    private static String sFindPath = "";

    public static void traverseFolder(String path, String dirName) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        if (dirName.equals(file2.getName())) {
                            sFindPath = file2.getPath();
                        }
                        if ("mobile".equals(file2.getName())) {
                            return;
                        }

                        if (!"build".equals(file2.getName())) {
                            traverseFolder(file2.getAbsolutePath(), dirName);
                        }
                    }
                }
            }
        }
        return;
    }

    public String readRem(String id, String key) {
        OpenDoDTO openDoDTO = getMappingStr(id);
        if (openDoDTO == null || StringUtils.isEmpty(openDoDTO.getPath())) {
            return null;
        }
        JSONObject jsonObject = getOpenDoDTO(openDoDTO);
        if (jsonObject == null) {
            return null;
        }

        Object o = jsonObject.get(key);
        if (o == null) {
            return null;
        }
        return o.toString();
    }
    public SavePostBO tidGetSavePostBo(String tid){
        OpenDoDTO openDoDTO = getMappingStr(tid);
        if (openDoDTO == null || StringUtils.isEmpty(openDoDTO.getPath())) {
            return null;
        }
        final SavePostBO savePostBO = openFile(openDoDTO.getPath());
        return savePostBO;
    }
    public Boolean writeRem(String id, String key, String content) {
        OpenDoDTO openDoDTO = getMappingStr(id);
        if (openDoDTO == null || StringUtils.isEmpty(openDoDTO.getPath())) {
            return null;
        }
        JSONObject jsonObject = getOpenDoDTO(openDoDTO);
        if (jsonObject == null) {
            return null;
        }
        jsonObject.put(key, content);
        String remFileName = getRemFileName(openDoDTO.getPath());
        FileUtils.strToFileUTF8(remFileName,jsonObject.toJSONString());
        return true;
    }

    private JSONObject getOpenDoDTO(OpenDoDTO openDoDTO) {
        String remFileName = getRemFileName(openDoDTO.getPath());
        String fileStr = FileUtils.FileUTF8ToStr(new File(remFileName));
        JSONObject remPostDTO;
        if (StringUtils.isEmpty(fileStr)) {
            remPostDTO = new JSONObject();
        } else {
            remPostDTO = JSONObject.parseObject(fileStr);
        }
        return remPostDTO;
    }

    private String getRemFileName(String path) {
        //逻辑就是  用全路径 进行md5 加密
        String md5 = MD5Utils.md5(path);
        String tmpStr = FileDirP + File.separator + "rem";
        File file = new File(tmpStr);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        return tmpStr + File.separator + md5 + ".txt";
    }
    private Boolean  openDoRem(String id,Model model){
        OpenDoDTO openDoDTO = getMappingStr(id);
        if (openDoDTO == null || StringUtils.isEmpty(openDoDTO.getPath())) {
            return null;
        }
        JSONObject jsonObject = getOpenDoDTO(openDoDTO);
        if(jsonObject != null ){
            model.addAttribute("rem",jsonObject);
        }
        return true;
    }
}
