package com.lsz.model.bo.face;

import com.lsz.common.UuidUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class SavePostBO implements Serializable {
    private String id;
    private String url;
    private String method;
    private String parameter;
    private String head;
    private String returnStr;
    private String returnTypeStr;
    private String name;
    private String projectName;//项目名
    /**
     * 描述
     */
    private String describe;
    private String parameterRem;
    public SavePostBO(){
        id = UuidUtil.shortUuid();
    }
}
