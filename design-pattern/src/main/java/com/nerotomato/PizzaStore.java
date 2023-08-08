package com.nerotomato;

import com.nerotomato.abstractfactory.APizzaCompanyFactory;
import com.nerotomato.abstractfactory.BPizzaCompanyFactory;
import com.nerotomato.domain.Pizza;
import com.nerotomato.factorymethod.ACompanyOrderPizza;
import com.nerotomato.factorymethod.BCompanyOrderPizza;

public class PizzaStore {
    public static void main(String[] args) {
        ACompanyOrderPizza aCompanyOrderPizza = new ACompanyOrderPizza();
        BCompanyOrderPizza bCompanyOrderPizza = new BCompanyOrderPizza();
        Pizza pizzA = aCompanyOrderPizza.createPizza();
        Pizza pizzaB = bCompanyOrderPizza.createPizza();

        Pizza AFactoryPizza = new APizzaCompanyFactory().createPizza();
        Pizza BFactoryPizza = new BPizzaCompanyFactory().createPizza();

    }
}
