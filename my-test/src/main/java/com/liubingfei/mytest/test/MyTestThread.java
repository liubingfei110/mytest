package com.liubingfei.mytest.test;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: LiuBingFei
 * @Date: 2021-05-12 11:19
 * @Description:
 */
public class MyTestThread {

    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();
        String abc = String.join(",", arr);
        System.out.println("a" + abc + "a");
        if (Objects.isNull(abc)) {
            System.out.println(123);
        }
        if (StringUtils.isBlank(abc)) {
            System.out.println(456);
        }

    }

}
