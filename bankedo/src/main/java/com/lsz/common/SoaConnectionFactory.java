package com.lsz.common;

import com.alibaba.fastjson.JSON;
import com.lsz.common.soa.BaseResponse;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Set;


public class SoaConnectionFactory {
    protected static Logger log = LoggerFactory.getLogger(SoaConnectionFactory.class);

    private static RestTemplate restTemplate;
    private Object parameters;
    public final static ObjectMapper mapper = new ObjectMapper();

    static {
        int readTimeout = 30000;
        int connectTimeout = 10000;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectTimeout);
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);

    }

    private static HttpHeaders getHead() {
        HttpHeaders headers = new HttpHeaders();

        return headers;
    }

    private static HttpEntity<String> exchange(String uri, HttpMethod httpMethod, HttpEntity<Object> httpEntity, Class<String> _class, Object... objArr) {
        log.info("uri:" + uri);
        return restTemplate.exchange(uri, httpMethod, httpEntity, _class, objArr);
    }

    public static String getUrl(String uri, Object obj) {
        if (obj == null) return uri;
        StringBuffer returnUrl = new StringBuffer();
        returnUrl.append(uri + "?");
        Map map;
        try {
            if (obj instanceof Map) {
                map = (Map) obj;
            } else {
                map = LszUtil.beanToMap(obj);
            }
            Set<String> keys = map.keySet();
            if (keys != null) {
                for (String key : keys) {
                    String val = (String) map.get(key);
                    returnUrl.append("&" + key + "=" + val);
                }
            }


        } catch (Exception e) {
            String msg = "组装" + "请求参数异常!";
            log.error(msg, e);

        }

        return returnUrl.toString();
    }

    private static <T extends BaseResponse> T todo(HttpMethod httpMethod, String uri, Object obj, Class<T> _class, Object... objArr) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(obj, getHead());
        HttpEntity<String> json = null;
        try {
            String uriTmp = "";
            if (httpMethod == HttpMethod.GET) {
                uriTmp = getUrl(uri, obj);
            } else {
                uriTmp = uri;
            }
            json = exchange(uriTmp, httpMethod, httpEntity, String.class, objArr);
            log.info("json:{}", json);
            //	ResponseEntity<String> rs = (ResponseEntity<String>)json;
        } catch (Exception e) {
            log.error("SoaFactory  exchange 异常", e);
        }
        try {
            T res = jsonToObj(json.getBody(), _class);
            return res;
        } catch (Exception e) {
            log.error("SoaFactory.parseObject()  soa返回json格式异常", e);
            e.printStackTrace();
            return (T) new BaseResponse("-999", "SOA返回数据格式异常，请联系管理员");
        }
    }

    public static <T> T jsonToObj(String json, Class<T> cls) {

        try {
            return mapper.readValue(json, cls);
        } catch (JsonParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }


    public static ResponseEntity<String> goFase(String url, String post, String data, Object... objArr) {
        log.info("url:{}-----------------Method:{}", url, post);
        if(data != null ){
            log.info("data:{}", data);
        }

        Map maps = null;
        if (data != null && !data.isEmpty()) {
            maps = (Map) JSON.parse(data);
        }
        post = post.toUpperCase();
        HttpMethod httpMethod = HttpMethod.GET;
        if ("GET".equals(post)) {
            httpMethod = HttpMethod.GET;
            url = getUrl(url,maps);
        } else if ("POST".equals(post)) {
            httpMethod = HttpMethod.POST;
        } else if ("PUT".equals(post)) {
            httpMethod = HttpMethod.PUT;
        } else if ("DELETE".equals(post)) {
            httpMethod = HttpMethod.DELETE;
        }
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(maps, getHead());
        HttpEntity<String> json = restTemplate.exchange(url, httpMethod, httpEntity, String.class, objArr);
        log.info("returnData:{}", json);
        return (ResponseEntity<String>) json;
    }
}
