package com.system.bhouse.bhouse.Factory.AbstractFactory;

import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Cheese;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Clams;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Dough;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Pepperoni;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Sauce;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Veggies;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.AbstractFactory
 */

public interface PizzaIngredientFactory {
    public Dough CreateDough();
    public Sauce CreateSauce();
    public Cheese CreateCheese();
    public Veggies[] CreateVeggies();
    public Pepperoni CreatePepperoni();
    public Clams CreateClams();

}
