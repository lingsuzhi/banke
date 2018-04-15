package com.lsz.model.bo.face;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class PostMan {
    private String id;
    private String name;
    private List<String> order;
    private Long timestamp;
    private List<Requests> requests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Requests> getRequests() {
        return requests;
    }

    public void setRequests(List<Requests> requests) {
        this.requests = requests;
    }

    public PostMan( ) {
        this.id = "9a3415fe-c471-99d4-f535-615721e96fdd";
        this.name = "face";
        this.order = new ArrayList<>();
        order.add("ea41b20e-c50c-a0e6-f062-420cc9f5f369");
        this.timestamp = new Date().getTime();
        this.requests = new ArrayList<>();



    }
}
