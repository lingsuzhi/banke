package com.lsz.service;

import com.alibaba.fastjson.JSONObject;
import com.lsz.common.FileUtils;
import com.lsz.common.MD5Utils;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.model.bo.face.SavePostBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public static final String FileDirP = "d:\\post";
    public static final String FilePath = FileDirP + File.separator;

    public String getHeadMenu(){
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
        if(!file.exists()){
            return returnStr;
        }
        File[] files = file.listFiles();
        if(files == null){
            return returnStr;
        }
        List<String> list = new ArrayList();
        for (File f:files             ) {
            if("Default".equals(f.getName())){
                continue;
            }
            list.add(f.getName());
        }
        for(int i = 0;i<10;i++){
            if(i>=list.size()){
                break;
            }
            String name = list.get(i);
            returnStr += "<li class=\"layui-nav-item\"><a href=\"javascript:layui.app.funSetMenu('" + name + "')" +  "\">" + name + "</a></li>\r\n";
        }
        if(list.size()>10){
            returnStr += "<li class=\"layui-nav-item\">";
            returnStr += "<a href=\"javascript:;\">其它系统</a>";
            returnStr += "<dl class=\"layui-nav-child\">";
            for(int i = 10;i<500;i++) {
                if(i>=list.size()){
                    break;
                }
                String name = list.get(i);
                returnStr += "<dd><a href=\"javascript:layui.app.funSetMenu('" + name + "')" +  "\">" + name + "</a></dd>\r\n";
            }
            returnStr += "</dl>";
            returnStr += "</li>";
        }
        return returnStr;
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
        String path = FilePath + dirName;
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

    public SavePostBO openFile(String fileStr) {
        fileStr = MD5Utils.decodeUtf8(fileStr);
        File file = new File(FileDirP + File.separator + fileStr);
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
        File file = new File(FilePath + File.separator + name + ".json");
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public String getFileJson(String name) {
        String str = "";
        File file = new File(FilePath + File.separator + name + ".json");
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

    public List<LayuiNavbarBO> getNavbar(String dirName) {
        File dir = new File(FileDirP + File.separator + dirName);
        if(!dir.exists()){
            return new LinkedList<>();
        }
        List<LayuiNavbarBO> list = new LinkedList<>();
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return new LinkedList<>();
        }
        int id = 0;
        boolean dirB1 = true;
        for (File file : files) {
            id++;
            LayuiNavbarBO layuiNavbarBO = new LayuiNavbarBO();
            if (file.isDirectory()) {
                layuiNavbarBO.setSpread(false);
                layuiNavbarBO.setTitle(file.getName());
                layuiNavbarBO.setIcon("fa-cubes");
                layuiNavbarBO.setId(String.valueOf(id));
                if (dirB1) {
                    layuiNavbarBO.setSpread(false);
                    dirB1 = !dirB1;
                }
                File[] files2 = file.listFiles();
                for (File file2 : files2) {
                    id++;
                    if (file2.isDirectory()) {
//文件夹不处理，只支持2级菜单
                    } else {
                        /////
                        LayuiNavbarBO layuiNavbarBO2 = new LayuiNavbarBO();
                        String fileStr = file.getName() + File.separator + file2.getName();


                        layuiNavbarBO2.setUrl("face/opendo?fileStr=" + MD5Utils.encodeUtf8(fileStr));
                        layuiNavbarBO2.setSpread(true);
                        layuiNavbarBO2.setTitleEx(file2.getName());
                        layuiNavbarBO2.setIcon("fa-stop-circle");
                        layuiNavbarBO2.setId(String.valueOf(id));
                        layuiNavbarBO.getChildren().add(layuiNavbarBO2);
                    }

                }

            } else {
                layuiNavbarBO.setUrl("face/opendo?fileStr=" + MD5Utils.encodeUtf8(file.getName()));
                layuiNavbarBO.setSpread(true);
                layuiNavbarBO.setTitleEx(file.getName());
                layuiNavbarBO.setIcon("fa-stop-circle");
                layuiNavbarBO.setId(String.valueOf(id));
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

        return list;
    }

    public void batchGenerate(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) return;
        //对文件进行循环遍历
        for (File file : files) {
            doJavaFile(file);
        }
    }

    private void doJavaFile(File file) {
        String className = file.getName();
        className = className.replace(".java", "");
        String tmpPath = FileDirP + File.separator + className;
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
            SavePostBO savePostBO = doJavaFileEx(fileStr, tmpMapPos,mapPos);
            mapPos = tmpMapPos + keyVal.length();
            if (savePostBO != null) {
                savePostBO.setUrl("http://pxy-disp-sit2.banketech" + classPathValue + savePostBO.getUrl());
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
     *  @param leftPos 不能超过的位置
     * @return
     */
    private SavePostBO doJavaFileEx(String fileStr, int pos,int leftPos) {
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
        if (pos1 != -1 && pos2 != -1 && pos1>leftPos) {
            String zhujieStr = fileStr.substring(pos1, pos2);
            String[] sArr = zhujieStr.split("\r\n");
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
                            paramS += "参数名:" + paramName.trim() + "        说明：" + paramRem.trim() + "\r\n";
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
                savePostBO.setParameterRem(paramS);
                savePostBO.setReturnStr(returnS);
                savePostBO.setName(nameS);
            }
        }
        return savePostBO;
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
        int pos1 = findStrLast(str, pos, "\r\n");
        if (pos1 == -1) return "";
        int pos2 = str.indexOf("\r\n", pos);
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

    public static void main(String[] args) {
        String tmp1 ="";
      traverseFolder2("D:\\source\\ADT-jpush");
        System.out.println(tmps);
    }
    private  static  String tmps="";
    public static void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.out.println("文件夹是空的!");
                return ;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        if("api".equals(file2.getName())){
                            tmps = file2.getPath();
                        }
                        if("build".equals(file2.getName())){
                            return;
                        }
                        traverseFolder2(file2.getAbsolutePath());
                    }
                }
            }
        }
        return  ;
    }
}
