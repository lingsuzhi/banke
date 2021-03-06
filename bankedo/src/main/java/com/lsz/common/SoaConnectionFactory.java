package com.lsz.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
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

    public static Map strToMap(String data) {
        Map maps = null;
        if (data != null && !data.isEmpty()) {
            try {
                maps = (Map) JSON.parse(data);
            } catch (JSONException exception) {
                //如果他不是json 尝试
                String[] sArrRow = data.split("\n");
                maps = new HashMap<String, String>();
                if (sArrRow != null && sArrRow.length > 0) {
                    for (String str : sArrRow) {
                        String[] sArrCol = str.split("=");
                        if (sArrCol != null && sArrCol.length > 1) {
                            maps.put(sArrCol[0], sArrCol[1]);
                        }
                    }
                }
            }


        }
        return maps;
    }

    private static Map strTabToMap(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        Map maps = null;
        if (data != null && !data.isEmpty()) {
            try {
                maps = (Map) JSONObject.parse(data);
            } catch (Exception exception) {
                //如果他不是json 尝试
                String[] sArrRow = data.split("\t:\t");
                maps = new HashMap<String, String>();
                if (sArrRow != null && sArrRow.length > 0) {
                    for (String str : sArrRow) {
                        String[] sArrCol = str.split("\t=\t");
                        if (sArrCol != null && sArrCol.length > 1) {
                            maps.put(sArrCol[0], sArrCol[1]);
                        }
                    }
                }
            }


        }
        return maps;
    }

    public static ResponseEntity<String> goFase(String url, String post, String data, String head, Object... objArr) {
        log.info("url:{} ######### Method:{}", url, post);
        if (data != null) {
            log.info("data:{}", data);
        }
        if (head != null) {
            log.info("head:{}", head);
        }
        Map dataMap = strToMap(data);
        Map<String, String> headMap = strTabToMap(head);
        if (StringUtils.isEmpty(post)) {
            post = "GET";
        }
        post = post.toUpperCase();
        HttpMethod httpMethod = HttpMethod.GET;
        if ("GET".equals(post)) {
            httpMethod = HttpMethod.GET;
            url = getUrl(url, dataMap);
        } else if ("POST".equals(post)) {
            httpMethod = HttpMethod.POST;
            String tmpS = headMap == null ? null : headMap.get("Content-Type");
            if (tmpS == null || !tmpS.contains("application/json")) {
                url = getUrl(url, dataMap);
            }
        } else if ("PUT".equals(post)) {
            httpMethod = HttpMethod.PUT;
        } else if ("DELETE".equals(post)) {
            httpMethod = HttpMethod.DELETE;
        }
        HttpHeaders httpHeaders = getHead();
        if (headMap != null && headMap.size() > 0) {
            for (String s1 : headMap.keySet()) {
                httpHeaders.set(s1, headMap.get(s1));

            }

        }
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dataMap, httpHeaders);
        HttpEntity<String> json = restTemplate.exchange(url, httpMethod, httpEntity, String.class, objArr);
        log.info("returnData:{}", json);
        return (ResponseEntity<String>) json;
    }
}
