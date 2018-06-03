package com.system.bhouse.bhouse.Factory.SimpleFactory;

import com.system.bhouse.bhouse.Factory.PizzaBehavior;
import com.system.bhouse.bhouse.Factory.pizza.CheesePizzaBehavior;
import com.system.bhouse.bhouse.Factory.pizza.ClamPizaa;
import com.system.bhouse.bhouse.Factory.pizza.PepperoniPiazz;
import com.system.bhouse.bhouse.Factory.pizza.VegglePizaa;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.SimpleFactory
 */

public class SimpleFactory {

    private PizzaBehavior pizzaBehavior;

    public PizzaBehavior CreatePizza(String type) {

        if (type.equals("Cheese")) {
            pizzaBehavior = new CheesePizzaBehavior();
        }
        else if (type.equals("Clam")) {
            pizzaBehavior = new ClamPizaa();
        }
        else if (type.equals("Pepperoni")) {
            pizzaBehavior = new PepperoniPiazz();
        }
        else if (type.equals("Veggle")) {
            pizzaBehavior = new VegglePizaa();
        }
        return pizzaBehavior;
    }
}
