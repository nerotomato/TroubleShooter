package com.nerotomato.factorymethod;

import com.nerotomato.domain.ACompanyPizza;
import com.nerotomato.domain.Pizza;

/**
 * 实现-工厂方法抽象类
 */
public class ACompanyOrderPizza extends OrderPizza {

    @Override
    public Pizza createPizza() {
        System.out.println("producing ACompany pizza.");
        return new ACompanyPizza();
    }
}
