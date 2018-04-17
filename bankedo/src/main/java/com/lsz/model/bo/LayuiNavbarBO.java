package com.lsz.model.bo;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by ex-lingsuzhi on 2018/4/17.
 */
public class LayuiNavbarBO {
    private String id;
    private String title;
    private String icon;
    private Boolean spread;   //是否父节点
    private List<LayuiNavbarBO> children = new ArrayList<>();
    private String url;
//    public void setUrlEx(String url)   {
//        final BASE64Encoder encoder = new BASE64Encoder();
//        final byte[] textByte;
//        try {
//            textByte = url.getBytes("UTF-8");
//            this.url = encoder.encode(textByte);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public String getUrlEx()  {
//        final BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            return  new String(decoder.decodeBuffer(this.url), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public void setTitleEx(String title) {
        if (title != null) {
            if (".json".equals(title.substring(title.length() - 5))) {
                title = title.substring(0, title.length() - 5);
            }
        }

        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<LayuiNavbarBO> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiNavbarBO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
