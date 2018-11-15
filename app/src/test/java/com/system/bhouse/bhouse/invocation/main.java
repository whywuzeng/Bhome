package com.system.bhouse.bhouse.invocation;

import org.junit.Test;

/**
 * Created by wz on 2018-11-14.
 */

public class main {

    @Test
    public void mainRun(){
        Man man = new Man();
        Proxy proxy = new Proxy(man);
        java.lang.reflect.Proxy.newProxyInstance(man.getClass().getClassLoader(),man.getClass().getInterfaces(),proxy);
    }
}
