package com.lsz.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Enumeration;

/*
 * Created by ex-lingsuzhi on 2018/4/26.
 */
@Controller
public class IpController {
    @RequestMapping("/testip")
    @ResponseBody
    public Object facetest2(HttpServletRequest request) {

        Enumeration<String> head = request.getHeaderNames();
        String s = request.getRemoteHost();

        ResponseEntity entity = ResponseEntity.ok(s);
        return entity;
    }
    public static void main(String[] args) throws Exception{
        //获取本机InetAddress的实例：
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("本机名：" + address.getHostName());
        System.out.println("IP地址：" + address.getHostAddress());
        byte[] bytes = address.getAddress();
        System.out.println("字节数组形式的IP地址：" + Arrays.toString(bytes));
        System.out.println("直接输出InetAddress对象：" + address);
    }
}
