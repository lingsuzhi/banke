package com.lsz.common;

import java.io.*;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
public class FileUtils {
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
}
