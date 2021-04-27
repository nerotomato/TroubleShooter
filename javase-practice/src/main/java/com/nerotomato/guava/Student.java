package com.nerotomato.guava;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nero on 2021/4/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private int age;
    private String name;

}
