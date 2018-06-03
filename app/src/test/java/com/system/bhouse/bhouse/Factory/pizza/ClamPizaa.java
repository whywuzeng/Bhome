package com.system.bhouse.bhouse.Factory.pizza;

import com.system.bhouse.bhouse.Factory.PizzaBehavior;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.pizza
 */

public class ClamPizaa implements PizzaBehavior {
    private static final String TAG = "ClamPizaa";
    @Override
    public void prepare() {
        System.out.println(TAG+"prepare");
    }

    @Override
    public void bake() {
        System.out.println(TAG+"bake");
    }

    @Override
    public void cut() {
        System.out.println(TAG+"cut");
    }

    @Override
    public void box() {
        System.out.println(TAG+"box");
    }
}
