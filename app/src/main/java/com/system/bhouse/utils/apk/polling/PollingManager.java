package com.system.bhouse.utils.apk;

/**
 * Created by Administrator on 2018-10-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.apk
 */

public class PollingManager {

//    private HashMap<String,>

    private final static class HODLER{
        private final static PollingManager MANAGER=new PollingManager();
    }

    public static PollingManager getInstance(){
        return HODLER.MANAGER;
    }


}
