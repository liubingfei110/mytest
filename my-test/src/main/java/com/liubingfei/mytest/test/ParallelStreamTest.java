package com.liubingfei.mytest.test;

import org.junit.Test;

import java.nio.file.OpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: LiuBingFei
 * @Date: 2020-07-24 13:42
 * @Description: 测试并行流
 */
public class ParallelStreamTest {

    @Test
    public void test1(){
        Integer [] arrays = {1,2,3,4,5,6,7,8,9};
        List<Integer> list = new ArrayList<>(Arrays.asList(arrays));
        Integer sum1 = list.stream().reduce(0, (a,b) -> a+b);
        System.out.println("sum1: "+sum1);
        Optional<Integer> sum2 = list.stream().reduce((a, b) -> a+b);
        if(sum2.isPresent()){
            System.out.println("sum2: "+sum2.get());
        }
        Optional<Integer> sum3 = list.parallelStream().reduce((a,b) -> a+b);
        System.out.println("sum3: " + sum3.get());
        Random random = new Random();
        Integer number = random.ints(0, 10001).findFirst().getAsInt();
        System.out.println(number);


    }
}
