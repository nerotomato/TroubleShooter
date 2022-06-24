package com.nerotomato.enumeration;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    @Test
    public void testTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2022-01-05 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime = instant.atZone(zoneId)
                .toLocalDateTime();
        System.out.println(localDateTime);
    }
}