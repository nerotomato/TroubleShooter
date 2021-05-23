package com.nerotomato.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Entity: 实体类, 必须
 * @Table: 对应数据库中的表, 必须, name=表名,
 * Indexes是声明表里的索引, columnList是索引的列, 同时声明此索引列是否唯一, 默认false
 * Created by nero on 2021/4/27.
 */
@ApiModel
@Data
@Entity
@Table(name = "ums_member", indexes = {@Index(columnList = "id", unique = true),
        @Index(columnList = "username", unique = true), @Index(columnList = "telephone", unique = true)})
public class UmsMember {
    @Id // @Id: 指明id列, 必须 // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String nickname;
    // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    @Column(name = "telephone", unique = true)
    private String telephone;
    private int status;
    private int gender;
    //@JsonFormat注解会将数据库中数据按照如下格式返回给前端
    @ApiModelProperty(value = "生日", example = "2021-5-12")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    private String city;
    private String job;
    //@JsonFormat注解会将数据库中数据按照如下格式返回给前端
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date create_time;
    //@JsonFormat注解会将数据库中数据按照如下格式返回给前端

    @ApiModelProperty(value = "修改时间", example = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date update_time;
}
