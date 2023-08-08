package com.nerotomato.abstractfactory;

import com.nerotomato.domain.BCompanyPizza;
import com.nerotomato.domain.Pizza;

/**
 * 实现抽象工厂类
 */
public class BPizzaCompanyFactory implements PizzaFactory{
    @Override
    public Pizza createPizza() {
        System.out.println("producing BPizzaCompanyFactory pizza.");
        return new BCompanyPizza();
    }
}
