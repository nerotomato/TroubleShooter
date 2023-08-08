package com.nerotomato.abstractfactory;

import com.nerotomato.domain.ACompanyPizza;
import com.nerotomato.domain.Pizza;

/**
 * 实现抽象工厂类
 */
public class APizzaCompanyFactory implements PizzaFactory {

    @Override
    public Pizza createPizza() {
        System.out.println("producing APizzaCompanyFactory pizza.");
        return new ACompanyPizza();
    }
}
