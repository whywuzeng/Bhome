package com.system.bhouse.bhouse.Factory.AbstractFactory;

import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Cheese;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Clams;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Dough;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Pepperoni;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Sauce;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.ThinCrustDough;
import com.system.bhouse.bhouse.Factory.AbstractFactory.Ingredient.base.Veggies;

/**
 * Created by Administrator on 2018-05-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Factory.AbstractFactory
 */

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough CreateDough() {

        return new ThinCrustDough();
    }

    @Override
    public Sauce CreateSauce() {
        return null;
    }

    @Override
    public Cheese CreateCheese() {
        return null;
    }

    @Override
    public Veggies[] CreateVeggies() {
        return new Veggies[0];
    }

    @Override
    public Pepperoni CreatePepperoni() {
        return null;
    }

    @Override
    public Clams CreateClams() {
        return null;
    }
}
