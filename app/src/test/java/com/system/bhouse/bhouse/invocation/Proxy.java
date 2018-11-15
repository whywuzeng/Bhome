package com.system.bhouse.bhouse.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wz on 2018-11-14.
 */

public class Proxy implements InvocationHandler{

    private Object man;

    public Proxy(Subject man) {
        this.man = man;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("proxy:"+proxy.getClass().getName());
        System.out.println("before");
        method.invoke(man,args);
        System.out.println("after ..调用完 目标类");
        return null;
    }
}
