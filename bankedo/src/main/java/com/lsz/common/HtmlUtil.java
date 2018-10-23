package com.lsz.common;

import org.springframework.util.StringUtils;

/**
 * Created by ex-lingsuzhi on 2018/10/22.
 */
public class HtmlUtil {
    public static String  strToHtml(String s)
    {
        if   (!StringUtils.isEmpty(s)){
            s = s.replace("\"","&quot;")
                    .replace("<","&lt;")
                    .replace(">","&gt;");
        }

        return   s;
    }
}
