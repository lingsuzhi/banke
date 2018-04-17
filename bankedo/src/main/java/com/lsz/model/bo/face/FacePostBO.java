package com.lsz.model.bo.face;


/*
 * Created by Administrator on 2018/4/15 0015.
 */

import com.alibaba.fastjson.JSONObject;

public class FacePostBO {
    private String url;
    private String post;
    private String data;
    private String head;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
