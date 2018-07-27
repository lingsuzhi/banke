package com.lsz.model.bo.face;

import lombok.Data;

import java.io.Serializable;

@Data
public class SavePostBO implements Serializable {
    private String url;
    private String method;
    private String parameter;
    private String head;
    private String returnStr;
    private String name;
}
