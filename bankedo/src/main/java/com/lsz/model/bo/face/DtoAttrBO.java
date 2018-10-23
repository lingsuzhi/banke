package com.lsz.model.bo.face;

import lombok.Data;

/**
 * Created by ex-lingsuzhi on 2018/10/22.
 */
@Data
public class DtoAttrBO {
    private String typeStr;
    private String nameStr;
    private String remStr;
    //是否必填  字符串 :true false
    private String parameRequired;
}
