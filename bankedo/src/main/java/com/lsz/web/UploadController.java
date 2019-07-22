package com.lsz.web;

import com.alibaba.fastjson.JSONObject;
import com.lsz.common.soa.ResponseInfo;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/files")
public class UploadController {

    @RequestMapping("/uploadInfo")
    public ResponseInfo freemarker(HttpServletRequest request) {
        String id = request.getSession().getId();
        String filename = request.getParameter("filename");
        //使用sessionid + 文件名生成文件号，与上传的文件保持一致
        id = id + filename;
        Object size = ProgressSingleton.get(id + "Size");
        size = size == null ? 100 : size;
        Object progress = ProgressSingleton.get(id + "Progress");
        progress = progress == null ? 0 : progress;
        JSONObject json = new JSONObject();
        json.put("size", size);
        json.put("progress", progress);
        return ResponseInfo.success(json.toString());
    }


    @RequestMapping("/upload")
    public ResponseInfo<String> uploadFile(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);

        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> fileItems = null;

        try {
            fileItems = upload.parseRequest(new ServletRequestContext(request));
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //获取文件域
        FileItem fileItem = fileItems.get(0);
        //使用sessionid + 文件名生成文件号
        String id = request.getSession().getId() + fileItem.getName();
        //向单例哈希表写入文件长度和初始进度
        ProgressSingleton.put(id + "Size", fileItem.getSize());
        //文件进度长度
        long progress = 0;
        //用流的方式读取文件，以便可以实时的获取进度
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = fileItem.getInputStream();
            File file = new File("D:/test");
            file.createNewFile();
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int readNumber = 0;
            while ((readNumber = in.read(buffer)) != -1) {
                //每读取一次，更新一次进度大小
                progress = progress + readNumber;
                //向单例哈希表写入进度
                ProgressSingleton.put(id + "Progress", progress);
                out.write(buffer);
            }
            //当文件上传完成之后，从单例中移除此次上传的状态信息
            ProgressSingleton.remove(id + "Size");
            ProgressSingleton.remove(id + "Progress");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseInfo.success("完成");
    }

    static class ProgressSingleton {
        //为了防止多用户并发，使用线程安全的Hashtable
        private static Hashtable<Object, Object> table = new Hashtable<>();

        public static void put(Object key, Object value) {
            table.put(key, value);
        }

        public static Object get(Object key) {
            return table.get(key);
        }

        public static Object remove(Object key) {
            return table.remove(key);
        }
    }
}
