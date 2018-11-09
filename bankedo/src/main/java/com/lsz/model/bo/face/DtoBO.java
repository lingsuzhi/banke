package com.lsz.model.bo.face;

import com.lsz.common.UuidUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ex-lingsuzhi on 2018/10/22.
 */
@Data
public class DtoBO {
    private String id;
    private List<DtoAttrBO> attrList = new ArrayList<>();
    private String projectName;
    private String describe;
    private String name;

    public DtoBO() {
        id = UuidUtil.shortUuid();
    }
}
