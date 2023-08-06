package com.nerotomato.factorymethod;

public class PizzaStore {
    public static void main(String[] args) {
        ACompanyOrderPizza aCompanyOrderPizza = new ACompanyOrderPizza();
        BCompanyOrderPizza bCompanyOrderPizza = new BCompanyOrderPizza();

        Pizza pizzA = aCompanyOrderPizza.createPizza();

        Pizza pizzaB = bCompanyOrderPizza.createPizza();

    }
}
