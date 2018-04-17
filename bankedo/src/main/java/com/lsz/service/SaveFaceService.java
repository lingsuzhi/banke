package com.lsz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsz.common.FileUtils;
import com.lsz.common.MD5Utils;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.model.bo.face.FacePostBO;
import com.lsz.model.bo.face.PostMan;
import com.lsz.model.bo.face.Requests;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
@Service
public class SaveFaceService {
    /**
     * 保存为一个json对象，跟postMan兼容
     *
     * @param facePostBO
     * @return PostMan
     */
    public PostMan saveDo(FacePostBO facePostBO) {
        PostMan postMan = new PostMan();
        Requests requests = new Requests();
        requests.setUrl(facePostBO.getUrl());
        requests.setMethod(facePostBO.getPost());
        requests.setRawModeData(facePostBO.getData());

        String head = facePostBO.getHead();
        if (StringUtils.isEmpty(head)) {
            head = "Content-Type: application/json\n";
        } else {
            if (head.indexOf("application/json") == -1) {
                head = head + "\n" + "Content-Type: application/json\n";
            }
        }
        requests.setHeaders(head);

        postMan.getRequests().add(requests);
        String url = facePostBO.getUrl();
        int xgPos = url.lastIndexOf("/");
        if (xgPos >= 0) {
            String name1 = url.substring(xgPos + 1);
            postMan.setName(name1);
        }

        int whPos = postMan.getName().indexOf("?");
        if (whPos >= 0) {
            String name1 = postMan.getName().substring(0,whPos );
            postMan.setName(name1);
        }
        return postMan;
    }

    public static final String FileDirP = "d:\\post";
    public FacePostBO openFile(String fileStr){
        final BASE64Decoder decoder = new BASE64Decoder();

        try {
            fileStr   =  new String(decoder.decodeBuffer(fileStr), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(FileDirP + File.separator + fileStr);
        if (file.exists()) {
            String fileS = FileUtils.FileUTF8ToStr(file);
            if(!StringUtils.isEmpty(fileS)){
                PostMan postMan  =   JSON.parseObject(fileS,PostMan.class);

                FacePostBO facePostBO = new  FacePostBO();
                List<Requests> list =  postMan.getRequests();
                if(list != null && list.size() > 0){
                    Requests requests = list.get(0);
                    facePostBO.setUrl(requests.getUrl());
                    facePostBO.setHead(requests.getHeaders());
                    facePostBO.setData(requests.getRawModeData());
                    facePostBO.setPost(requests.getMethod());
                }
                return facePostBO;

            }
        }
        return null;
    }
    public void saveFile(PostMan postMan) {
        String fileDir = FileDirP + File.separator + "defautl";
        File file = new File(fileDir);
        if (file.exists() && file.isDirectory()) {
            //文件夹存在
        } else {
            file.mkdirs();
        }
        String name = postMan.getName();
        String fileName = fileDir + File.separator + name + ".json";
        String strBuff = JSONObject.toJSONString(postMan);
        FileUtils.strToFileUTF8(fileName, strBuff);
    }

//    public static void main(String[] strings) {
//        SaveFaceService saveFaceService = new SaveFaceService();
//        saveFaceService.getNavbar();
//
//    }

    public List<LayuiNavbarBO> getNavbar() {
        File dir = new File(FileDirP);
        List<LayuiNavbarBO> list = new LinkedList<>();
        File[] files = dir.listFiles();
        if(files == null) return null;
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



                        layuiNavbarBO2.setUrl("face/opendo?fileStr=" +MD5Utils.base64(fileStr));
                        layuiNavbarBO2.setSpread(false);
                        layuiNavbarBO2.setTitleEx(file2.getName());
                        layuiNavbarBO2.setIcon("fa-stop-circle");
                        layuiNavbarBO2.setId(String.valueOf(id));
                        layuiNavbarBO.getChildren().add(layuiNavbarBO2);
                    }

                }

            } else {
                layuiNavbarBO.setUrl("face/opendo?fileStr=" + MD5Utils.base64(file.getName()));
                layuiNavbarBO.setSpread(false);
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
