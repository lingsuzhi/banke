package com.lsz.model.bo.face;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParameBO implements Serializable {
    private String parameName;  //名字
    private String parameRequired;//必填
    private String parameType;//类型
    private String parameRem; //说明

    public ParameBO(String parameName, String parameRequired, String parameType, String parameRem) {
        this.parameName = parameName;
        this.parameRequired = parameRequired;
        this.parameType = parameType;
        this.parameRem = parameRem;
    }
    public ParameBO(){

    }
}
