package com.lsz.dto;

import lombok.Data;

@Data
public class OpenDoDTO {

    private String id;
    private String path;
    private String type;
    private String name;
    private String projectName;

    public OpenDoDTO() {

    }

    public OpenDoDTO(String id, String name, String path, String type, String projectName) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.type = type;
        this.projectName = projectName;
    }
}
