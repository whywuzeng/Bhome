package com.system.bhouse.bhouse.Annotation;

/**
 * Created by Administrator on 2018-07-05.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Annotation
 */

public class AnnotationTest {
    @GET(value = "http://bip.bhome.com.cn")
    public String getIpMsg(){
        return "";
    }

    @GET(value = "http://ip.taobao.com.cn")
    public String getIp(){
        return "";
    }
}
