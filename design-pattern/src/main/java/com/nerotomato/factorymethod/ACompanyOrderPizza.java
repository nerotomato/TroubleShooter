package com.nerotomato.factorymethod;

/**
 * 实现-工厂方法抽象类
 */
public class ACompanyOrderPizza extends OrderPizza {

    @Override
    Pizza createPizza() {
        System.out.println("producing ACompany pizza.");
        return new ACompanyPizza();
    }
}
