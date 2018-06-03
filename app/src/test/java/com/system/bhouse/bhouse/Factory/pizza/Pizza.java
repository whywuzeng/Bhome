package com.system.bhouse.bhouse.Factory.pizza;

import com.system.bhouse.bhouse.Factory.PizzaBehavior;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.pizza
 */

public abstract class Pizza implements PizzaBehavior {
    public String getName() {
        return name;
    }

    String name;
    String dough;
    String sauce;

    ArrayList<String> topping = new ArrayList<>();

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Tossing dough"); //搅拌面包
        System.out.println("Adding sauce"); //加酱料
        System.out.println("Adding Tossings");

        for (int i = 0; i < topping.size(); i++) {
            System.out.println("  " + topping.get(i));
        }
    }

    @Override
    public void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the pizza into diagonal silices");
    }

    @Override
    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }


}
