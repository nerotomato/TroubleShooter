package com.nerotomato.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程实体类
 *
 * @Created by nerotomato on 2021/8/17.
 */
@Getter
@Setter
@AllArgsConstructor
public class Courses implements Cloneable{
    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 深拷贝 对象的引用类型属性，也应该和 Student 类一样实现clone方法
        return super.clone();
    }

    @Override
    public String toString() {
        return "Courses{" + this.hashCode() + ",name='" + name + '\'' + '}';
    }
}
