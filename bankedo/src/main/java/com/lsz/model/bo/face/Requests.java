package com.lsz.model.bo.face;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class Requests {
    private String id;
    private String headers;
    private String url;
    private String pathVariables;
    private String preRequestScript;
    private String method;
    private String collectionId;
    private List data;
    private String dataMode;
    private String name;
    private String description;
    private String descriptionFormat;
    private Long time;
    private Integer version;
    private List responses;
    private String tests;
    private String currentHelper;
    private String helperAttributes;
    private String rawModeData;

    public Requests(){
        this.id = "ea41b20e-c50c-a0e6-f062-420cc9f5f369";
       // this.headers = headers;
      //  this.url = url;
        this.pathVariables = null;
        this.preRequestScript = null;
        //this.method = method;
        this.collectionId = "9a3415fe-c471-99d4-f535-615721e96fdd";
        this.data = new ArrayList();
        this.dataMode = "raw";
        this.name = "lszFace";
        this.description = "1";
        this.descriptionFormat = "html";
        this.time = new Date().getTime();
        this.version = 2;
        this.responses = new ArrayList();;
        this.tests = null;
        this.currentHelper = "normal";
        this.helperAttributes = null;
       // this.rawModeData = rawModeData;
    }

    //"id": "ea41b20e-c50c-a0e6-f062-420cc9f5f369",
//"headers": "head: 1112\nContent-Type: application/json\n",
//"url": "http://127.0.0.1:8080/face/facetest",
//"pathVariables": {},
//"preRequestScript": null,
//"method": "POST",
//"collectionId": "9a3415fe-c471-99d4-f535-615721e96fdd",
//"data": [],
//"dataMode": "raw",
//"name": "http://127.0.0.1:8080/face/facetest",
//"description": "1",
//"descriptionFormat": "html",
//"time": 1523761814002,
//"version": 2,
//"responses": [],
//"tests": null,
//"currentHelper": "normal",
//"helperAttributes": {},
//"rawModeData": "{\"id\":1,\r\n\"name\":\"lsz\"}"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(String pathVariables) {
        this.pathVariables = pathVariables;
    }

    public String getPreRequestScript() {
        return preRequestScript;
    }

    public void setPreRequestScript(String preRequestScript) {
        this.preRequestScript = preRequestScript;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getDataMode() {
        return dataMode;
    }

    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFormat() {
        return descriptionFormat;
    }

    public void setDescriptionFormat(String descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List getResponses() {
        return responses;
    }

    public void setResponses(List responses) {
        this.responses = responses;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getCurrentHelper() {
        return currentHelper;
    }

    public void setCurrentHelper(String currentHelper) {
        this.currentHelper = currentHelper;
    }

    public String getHelperAttributes() {
        return helperAttributes;
    }

    public void setHelperAttributes(String helperAttributes) {
        this.helperAttributes = helperAttributes;
    }

    public String getRawModeData() {
        return rawModeData;
    }

    public void setRawModeData(String rawModeData) {
        this.rawModeData = rawModeData;
    }
}
