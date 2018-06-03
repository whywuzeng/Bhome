package com.system.bhouse.bhouse.Command;

/**
 * Created by Administrator on 2018-05-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Command
 */

public class CeilingFan {
    private static final String TAG = "CeilingFan";
    String location="";

    public int getLevel() {
        return level;
    }

    int level;
    public static final int HIGH=1;
    public static final int MEDIUM=2;
    public static final int LOW=0;
    private boolean isOn=false;



    public CeilingFan(String location){
        this.location=location;
    }

    public void high(){
        if (isOn) {
            level = HIGH;
            System.out.println(TAG + "this level is:" + level);
        }
    }

    public void medium(){
        if (isOn) {
            level = MEDIUM;
            System.out.println(TAG + "this level is :" + level);
        }
    }

    public void low(){
        if (isOn) {
            level = LOW;
            System.out.println(TAG + "this level is :" + level);
        }
    }

    public void off(){
        isOn=false;
        System.out.println(TAG+"is off");
    }

}
