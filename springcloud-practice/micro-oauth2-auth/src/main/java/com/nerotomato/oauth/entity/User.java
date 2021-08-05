package com.nerotomato.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author nerotomato
 * @create 2021/7/17 下午3:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {
    private long id;
    private String username;
    private String password;
    private int status;
    private List<String> roles;
}
