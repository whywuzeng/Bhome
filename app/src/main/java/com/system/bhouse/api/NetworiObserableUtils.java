package com.system.bhouse.api;

/**
 * Created by Administrator on 2016-3-28.
 */
public class NetworiObserableUtils {
    private static NetworiObserableUtils networiObserableUtils;
    private NetworiObserableUtils(){

    }

    public static NetworiObserableUtils getInstence(){
        if (networiObserableUtils==null){
            networiObserableUtils=new NetworiObserableUtils();
        }
        return networiObserableUtils;
    }

//    public void
}
