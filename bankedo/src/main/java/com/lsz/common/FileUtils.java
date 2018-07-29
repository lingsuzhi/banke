package com.lsz.common;

import com.google.common.base.MoreObjects;

import javax.annotation.Resources;
import java.io.*;
import java.net.URL;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
public class FileUtils {
    public static String FileUTF8ToStr(File file){

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
              fileInputStream = new FileInputStream(file);
            inputStreamReader= new InputStreamReader(fileInputStream,"utf-8");
          //  BufferedReader br = new BufferedReader(inputStreamReader);

            StringBuffer strBuf=new StringBuffer();
            while(inputStreamReader.ready())
            {
                strBuf.append((char)inputStreamReader.read());
            }
            return strBuf.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void strToFileUTF8(String fileName, String strBuff) {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        File file = new File(fileName);
        if (file.exists()){
            file.delete();
        }
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out, "utf-8");
            osw.write(strBuff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) osw.close();
                if (out != null) out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static URL getResource(String resourceName) {
        ClassLoader loader = MoreObjects.firstNonNull(
                Thread.currentThread().getContextClassLoader(),
                Resources.class.getClassLoader());
        URL url = loader.getResource(resourceName);
        checkArgument(url != null, "resource %s not found.", resourceName);
        return url;
    }
}
