package com.nerotomato.enumeration;

import lombok.Data;

/**
 * 枚举
 * Created by nero on 2021/2/24.
 */
@Data
public class TestEnum {

    private HamburgerStatus status;

    //枚举类型中可以定义属性,方法和构造函数
    public enum HamburgerStatus {
        ORDERED(5) {
            public boolean isOrdered() {
                return true;
            }
        },
        READY(2) {
            public boolean isReady() {
                return true;
            }
        },
        DELIVERED(0) {
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isOrdered() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public boolean isDelivered() {
            return false;
        }

        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        HamburgerStatus(int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }
    }

    public boolean isDeliverable() {
        return this.status.isReady();
    }
    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " +
                this.getStatus().getTimeToDelivery());
    }
}
