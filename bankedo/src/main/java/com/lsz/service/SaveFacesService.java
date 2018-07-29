package com.lsz.service;

import com.alibaba.fastjson.JSONObject;
import com.lsz.common.FileUtils;
import com.lsz.common.MD5Utils;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.model.bo.face.SavePostBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
@Service
@Slf4j
public class SaveFacesService {
    public static final String FileDirP = "d:\\post";
    public static final String FilePath = FileDirP + File.separator + "defautl";

    public String saveDo(SavePostBO savePostBO) {
        checkFilePath(FilePath);
        String json = JSONObject.toJSONString(savePostBO);
        String name = savePostBO.getName();
        String fileName = FilePath + File.separator + name + ".json";
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
        final BASE64Decoder decoder = new BASE64Decoder();
        try {
            fileStr = new String(decoder.decodeBuffer(fileStr), "UTF-8");
        } catch (IOException e) {
            log.info("错误: {}", e);
        }
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
    public SavePostBO getFileJsonPostBO(String name){
        String str = getFileJson(name);
        if(StringUtils.isEmpty(str)){
            return null;
        }
        SavePostBO savePostBO = JSONObject.parseObject(str,SavePostBO.class);
        return  savePostBO;
    }
    public List<LayuiNavbarBO> getNavbar() {
        File dir = new File(FileDirP);
        List<LayuiNavbarBO> list = new LinkedList<>();
        File[] files = dir.listFiles();
        if (files == null) return null;
        int id = 0;
        boolean dirB1 = true;
        for (File file : files) {
            id++;
            LayuiNavbarBO layuiNavbarBO = new LayuiNavbarBO();
            if (file.isDirectory()) {
                layuiNavbarBO.setSpread(true);
                layuiNavbarBO.setTitle(file.getName());
                layuiNavbarBO.setIcon("fa-cubes");
                layuiNavbarBO.setId(String.valueOf(id));
                if (dirB1) {
                    layuiNavbarBO.setSpread(true);
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


                        layuiNavbarBO2.setUrl("face/opendo?fileStr=" + MD5Utils.base64(fileStr));
                        layuiNavbarBO2.setSpread(true);
                        layuiNavbarBO2.setTitleEx(file2.getName());
                        layuiNavbarBO2.setIcon("fa-stop-circle");
                        layuiNavbarBO2.setId(String.valueOf(id));
                        layuiNavbarBO.getChildren().add(layuiNavbarBO2);
                    }

                }

            } else {
                layuiNavbarBO.setUrl("face/opendo?fileStr=" + MD5Utils.base64(file.getName()));
                layuiNavbarBO.setSpread(true);
                layuiNavbarBO.setTitleEx(file.getName());
                layuiNavbarBO.setIcon("fa-stop-circle");
                layuiNavbarBO.setId(String.valueOf(id));
                //layuiNavbarBO.setChildren(null);
            }

            list.add(layuiNavbarBO);
        }
        return list;
    }
}
