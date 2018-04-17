package com.lsz.model.bo;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
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
