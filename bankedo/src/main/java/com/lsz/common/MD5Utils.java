package com.lsz.common;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
public class MD5Utils {
    public static String base64(String str){
        final BASE64Encoder encoder = new BASE64Encoder();
        final byte[] textByte;
        try {
            textByte = str.getBytes("UTF-8");
            return encoder.encode(textByte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
