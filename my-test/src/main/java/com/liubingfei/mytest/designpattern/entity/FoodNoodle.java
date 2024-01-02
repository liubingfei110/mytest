package com.liubingfei.mytest.designpattern.entity;

import lombok.Data;

/**
 * @Author: LiuBingFei
 * @Date: 2021-06-29 9:30
 * @Description: 面条
 */
@Data
public class FoodNoodle extends Food {


    /**
     * 辛辣程度
     */
    private String spicy;

    public FoodNoodle(String spicy) {
        this.spicy = spicy;
    }

    public String getSpicy() {
        return spicy;
    }

    public void setSpicy(String spicy) {
        this.spicy = spicy;
    }

    /**
     * 添加辛辣
     *
     * @param spicy
     */
    private void addSpicy(String spicy) {
        this.spicy = spicy;
    }

}
