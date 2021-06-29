package com.liubingfei.mytest.designpattern.entity;

import lombok.Data;

/**
 * @Author: LiuBingFei
 * @Date: 2021-06-29 9:26
 * @Description: 食物类
 */
@Data
public class Food {
    /**
     * 名称
     */
    private String name;

    /**
     * 种类
     */
    private String type;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 颜色
     */
    private String color;

    /**
     * 味道
     */
    private String taste;


}
