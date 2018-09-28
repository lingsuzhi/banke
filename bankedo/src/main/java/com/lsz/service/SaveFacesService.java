package com.lsz.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsz.common.FileUtils;
import com.lsz.common.MD5Utils;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.model.bo.face.SavePostBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
@Service
@Slf4j
public class SaveFacesService {

    public static String FileDirP = "post";
    private static String FileDirStr = "";

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
            try{
                jsonArray = JSONObject.parseArray(json);

            }catch (Exception e){
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

    public String saveDo(SavePostBO savePostBO, String dirName) {
        if (StringUtils.isEmpty(savePostBO.getName())) {
            log.info("saveDo异常 name不能为空");
            return "";
        }
        savePostBO.setName(savePostBO.getName().replace("*", "")
                .replace("/", "")
                .replace("\\", "")
                .replace("\"", "")
                .replace(":", "")
                .replace("?", "")
                .replace("|", "")
                .replace("<", "")
                .replace(">", ""));
        String path = FileDirStr + File.separator + dirName;
        checkFilePath(path);
        String json = JSONObject.toJSONString(savePostBO);
        String name = savePostBO.getName();
        String fileName = path + File.separator + name + ".json";
        FileUtils.strToFileUTF8(fileName, json);
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

    public SavePostBO openFileEx(String fileStr, String dirName) {
        final String finalStr = "fileStr=";
        int pos = fileStr.indexOf(finalStr);
        if (pos == -1) return null;
        int pos2 = fileStr.indexOf("&", pos);
        if (pos2 < pos) {
            pos2 = fileStr.length();
        }
        return openFile(fileStr.substring(pos + finalStr.length(), pos2), dirName);
    }

    public SavePostBO openFile(String fileStr, String dirName) {
        fileStr = MD5Utils.decodeUtf8(fileStr);
        File file = new File(FileDirP + File.separator + dirName + File.separator + fileStr);
        if (file.exists()) {
            String fileS = FileUtils.FileUTF8ToStr(file);
            if (!StringUtils.isEmpty(fileS)) {
                SavePostBO savePostBO = JSONObject.parseObject(fileS, SavePostBO.class);
                return savePostBO;
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
        List<LayuiNavbarBO> list = getNavbar(projectName);
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
                    SavePostBO savePostBO = openFileEx(layuiNavbarBO.getUrl(), projectName);
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

    public List<LayuiNavbarBO> getNavbar(String dirName) {
        File dir = new File(FileDirP + File.separator + dirName);
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
                layuiNavbarBO.setId(dirName + String.valueOf(id));
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


                        layuiNavbarBO2.setUrl("face/opendo?fileStr=" + MD5Utils.encodeUtf8(fileStr) + "&dirName=" + dirName);
                        layuiNavbarBO2.setSpread(true);
                        layuiNavbarBO2.setTitleEx(file2.getName());
                        layuiNavbarBO2.setIcon("fa-stop-circle");
                        layuiNavbarBO2.setId(dirName + String.valueOf(id));
                        layuiNavbarBO.getChildren().add(layuiNavbarBO2);
                    }

                }

            } else {
                layuiNavbarBO.setUrl("face/opendo?fileStr=" + MD5Utils.encodeUtf8(file.getName()));
                layuiNavbarBO.setSpread(true);
                layuiNavbarBO.setTitleEx(file.getName());
                layuiNavbarBO.setIcon("fa-stop-circle");
                layuiNavbarBO.setId(dirName + String.valueOf(id));
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
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        //对文件进行循环遍历
        for (File file : files) {
            doJavaFileDo(file);
        }
        log.info("执行完毕。。。");
    }

    public void doJavaFileDo(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        String apiPath = findApiPath(file.getAbsolutePath());
        if (StringUtils.isEmpty(apiPath)) {
            log.info("路径:{} 找不到api目录", file.getAbsolutePath());
            return;
        }
        log.info("正在处理项目{}", file.getName());
        FileDirStr = FileDirP + File.separator + file.getName();
        File dir = new File(FileDirStr);
        if (dir.exists()) {
            deleteDir(dir);
        }
        dir.mkdir();//创建文件夹
        batchGenerate(apiPath, file.getName());

    }

    public String findApiPath(String path) {
        sFindPath = "";
        traverseFolder(path);
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

    private void doJavaFile(File file, String projectName) {
        String className = file.getName();
        className = className.replace(".java", "");
        String tmpPath = FileDirP + File.separator + projectName + File.separator + className;
        //如果目录存在 则删除
        File dir = new File(tmpPath);
        if (dir.exists()) {
            deleteDir(dir);
        }
        dir.mkdir();//创建文件夹
        String fileStr = FileUtils.FileUTF8ToStr(file);
        if (StringUtils.isEmpty(fileStr)) return;
        final String keyVal = "Mapping(";
        int mappingPos1 = fileStr.indexOf(keyVal);
        if (mappingPos1 == -1) return;
        //原理：第一个 mapping 后面第一个引号的内容 就是控制器路径
        String classPathValue = getYinhao(fileStr, mappingPos1);

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
                String tmpUrl = classPathValue + savePostBO.getUrl();
                tmpUrl = tmpUrl.replace("//", "/");//有个小Bug

                savePostBO.setUrl(tmpUrl);
                savePostBO.setProjectName(projectName);

                savePostBO.setDescribe(savePostBO.getName());
                if (savePostBO.getName().length() > 10) {
                    savePostBO.setName(savePostBO.getName().substring(0, 10));
                }

                saveDo(savePostBO, className);
            }
        }

    }

    private static int findStrLast(String str, int pos, String findStr) {
        return str.substring(0, pos).lastIndexOf(findStr);
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
                savePostBO.setParameterRem(paramStr);
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
            if ("(".equals(tmpS)) {
                leftK++;
            } else if (")".equals(tmpS)) {
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

    public static void traverseFolder(String path) {
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
                        if ("api".equals(file2.getName())) {
                            sFindPath = file2.getPath();
                        }
                        if ("mobile".equals(file2.getName())) {
                            return;
                        }

                        if (!"build".equals(file2.getName())) {
                            traverseFolder(file2.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return;
    }
}
