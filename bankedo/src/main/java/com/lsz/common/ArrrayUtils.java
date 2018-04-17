package com.lsz.common;

/*
 * Created by ex-lingsuzhi on 2018/4/16.
 */
public class ArrrayUtils {
    public static boolean exists(Object[] arr, Object obj) {
        for (Object o : arr) {
            if(obj.equals(o)){
                return true;
            }
        }
        return false;
    }

}
