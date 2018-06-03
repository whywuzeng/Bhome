package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class GarageDoor {

    private static final String TAG = "GarageDoor";

    public GarageDoor() {

    }

    public void Up() {
        System.out.println(TAG + "---->Up");
    }

    public void down() {
        System.out.println(TAG + "---->down");
    }

    public void stop() {
        System.out.println(TAG + "---->stop");
    }

    public void lightOn() {
        System.out.println(TAG + "---->lightOn");
    }

    public void lightOff() {
        System.out.println(TAG + "---->lightOff");
    }
}
