package com.nerotomato.factorymethod;

import com.nerotomato.domain.Pizza;

/**
 * 定义工厂方法模式 - 抽象类
 */
public abstract class OrderPizza {
    abstract Pizza createPizza();
}
