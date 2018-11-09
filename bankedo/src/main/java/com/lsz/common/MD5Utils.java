package com.lsz.common;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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


    public static String encodeUtf8(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }
    public static String decodeUtf8(String str){

        try {
            return URLDecoder.decode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }
}
