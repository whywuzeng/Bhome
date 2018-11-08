package com.system.bhouse.bhouse.phone.activity.bean;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity.bean
 */

public class ScanBean {

    public ScanBean(String name, String text, String projectname, String time, String imageUrl,String groupString) {
        this.name = name;
        this.groupString = groupString;
        this.text = text;
        this.projectname = projectname;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public String name;
    public String text;
    public String projectname;
    public String time;
    public String imageUrl;
    public String groupString;
}
