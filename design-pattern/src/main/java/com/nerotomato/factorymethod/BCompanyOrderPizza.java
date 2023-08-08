package com.nerotomato.factorymethod;

import com.nerotomato.domain.BCompanyPizza;
import com.nerotomato.domain.Pizza;

/**
 * 实现工厂方法的抽象类
 */
public class BCompanyOrderPizza extends OrderPizza{

    @Override
    public Pizza createPizza() {
        System.out.println("producing BCompany pizza");
        return new BCompanyPizza();
    }
}
