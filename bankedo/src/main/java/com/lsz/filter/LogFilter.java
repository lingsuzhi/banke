package com.lsz.filter;

import com.alibaba.fastjson.JSONObject;
import com.lsz.common.ArrrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class LogFilter extends OncePerRequestFilter {
    protected static Logger log = LoggerFactory.getLogger(LogFilter.class);

    public static final String[] STATIC = {
            "htm", "html", "js", "map", "css", "jpg", "png", "gif", "ico", "bmp",
            "otf", "eot", "svg", "ttf", "woff", "woff2", "swf", "wav", "ogg", "mp3"
    };
    private static final String[] SKIPS = {"/health"};
    protected boolean includeParam = true;
    private int maxParamLength = 0;

    public static boolean isStatic(String uri) {
        int pointPos = uri.lastIndexOf(".");
        if (pointPos >= 0) {
            String zStr = uri.substring(pointPos + 1);
            return ArrrayUtils.exists(STATIC, zStr);
        }
        return false;
    }

    protected static boolean needProcess(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !isStatic(uri) && !ArrrayUtils.exists(SKIPS, uri);
    }

    protected static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public void setMaxParamLength(int maxParamLength) {
        this.maxParamLength = maxParamLength;
    }

    protected void process(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        long beg = System.currentTimeMillis();
        if (includeParam) {
            String params = JSONObject.toJSONString(request.getParameterMap());
            // 处理PostBody请求

            log.info("##### Begin Request[{}] Param[{}]", uri, params);
        } else {
            log.info("##### Begin Request[{}]", uri);
        }
        String ip = getIpAddress(request);
        try {
            filterChain.doFilter(request, response);
        } finally {
            long end = System.currentTimeMillis();
            String responseStr = response.getContentType();
            if (maxParamLength > 0 && responseStr.length() > maxParamLength) {
                responseStr = responseStr.substring(0, maxParamLength);
            }
            log.info("##### Finish Request[{}] Cost[{}ms] Response[{}]", uri, end - beg, responseStr);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (needProcess(request)) {
            process(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
