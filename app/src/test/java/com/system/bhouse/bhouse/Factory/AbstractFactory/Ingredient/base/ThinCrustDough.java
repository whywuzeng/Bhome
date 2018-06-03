package com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base
 */

public class ThinCrustDough extends Dough {

    private static final String TAG = "ThinCrustDough";

    public ThinCrustDough(){
        CreateDough();
    }

    @Override
    public void CreateDough() {
        System.out.println(TAG+"CreateDough");
    }
}
