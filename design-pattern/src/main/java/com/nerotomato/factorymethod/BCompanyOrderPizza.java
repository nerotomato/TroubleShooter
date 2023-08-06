package com.nerotomato.factorymethod;

/**
 * 实现工厂方法的抽象类
 */
public class BCompanyOrderPizza extends OrderPizza{

    @Override
    Pizza createPizza() {
        System.out.println("producing BCompany pizza");
        return new BCompanyPizza();
    }
}
