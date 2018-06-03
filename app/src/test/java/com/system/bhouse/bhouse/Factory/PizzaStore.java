package com.system.bhouse.bhouse.Factory;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory
 */

public abstract class PizzaStore {


//    private SimpleFactory mSimpleFactory;

//    public void CreatePizza(SimpleFactory simpleFactory) {
//        this.mSimpleFactory=simpleFactory;
//    }

    public PizzaBehavior OrderPizza(String type) {

        PizzaBehavior pizzaBehavior;

        pizzaBehavior =CreatePizza(type);

        pizzaBehavior.prepare();
        pizzaBehavior.bake();
        pizzaBehavior.cut();
        pizzaBehavior.box();

        return pizzaBehavior;
    }

    protected abstract PizzaBehavior CreatePizza(String type);

}
