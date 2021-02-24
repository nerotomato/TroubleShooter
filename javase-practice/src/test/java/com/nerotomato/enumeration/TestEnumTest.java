package com.nerotomato.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 枚举类型中可以定义属性,方法和构造函数
 * */
class TestEnumTest {
    @Test
    public void givenHamburgerOrder(){
        TestEnum te = new TestEnum();
        te.setStatus(TestEnum.HamburgerStatus.READY);
        assertTrue(te.isDeliverable());
        System.out.println(te.isDeliverable());
    }
}