package com.system.bhouse.bhouse.phone.activity.bean;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity.bean
 */

public class ScanBean {
    public String name;

    public ScanBean(String name, String text, String projectname, String time, String imageUrl) {
        this.name = name;
        this.text = text;
        this.projectname = projectname;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public String text;
    public String projectname;
    public String time;
    public String imageUrl;
}
