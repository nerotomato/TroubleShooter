package com.nerotomato.bytecode;

/**
 * Created by nero on 2021/3/16.
 */
public class ByteCodeObject {
    private int count = 0;
    private double sum = 0.0d;

    public void add(double num) {
        this.count++;
        sum += num;
    }

    public double getMult() {
        return sum * count;
    }

    public double getAvg() {
        if (0 == this.count) {
            return sum;
        }
        return sum / count;
    }
}
