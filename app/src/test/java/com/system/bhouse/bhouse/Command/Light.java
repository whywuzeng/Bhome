package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class Light {

    private static final String TAG = "Light";

    private Boolean isOn=false;

    public void On() {
        if (!isOn)
        {
            isOn=!isOn;
            System.out.println(TAG + "On");
        }
    }

    public void Off() {
        if (isOn)
        {
            isOn=!isOn;
            System.out.println(TAG + "Off");
        }
    }
}
