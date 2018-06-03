package com.system.bhouse.bhouse.Factory.Store;

import com.system.bhouse.bhouse.Factory.PizzaBehavior;
import com.system.bhouse.bhouse.Factory.PizzaStore;
import com.system.bhouse.bhouse.Factory.pizza.CheesePizzaBehavior;
import com.system.bhouse.bhouse.Factory.pizza.ClamPizaa;
import com.system.bhouse.bhouse.Factory.pizza.PepperoniPiazz;
import com.system.bhouse.bhouse.Factory.pizza.VegglePizaa;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.Store
 */

public class ChicagoStylePizaaStore extends PizzaStore
{

    private PizzaBehavior mPizzaBehavior;


    @Override
    protected PizzaBehavior CreatePizza(String type) {

        if (type.equals("Cheese")) {
            this.mPizzaBehavior = new CheesePizzaBehavior();
        }
        else if (type.equals("Clam")) {
            this.mPizzaBehavior = new ClamPizaa();
        }
        else if (type.equals("Pepperoni")) {
            this.mPizzaBehavior = new PepperoniPiazz();
        }
        else if (type.equals("Veggle")) {
            this.mPizzaBehavior = new VegglePizaa();
        }

        return this.mPizzaBehavior;
    }
}
