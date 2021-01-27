package com.liubingfei.mytest.test;

import com.liubingfei.mytest.model.ExcessiveOrganExport;
import com.liubingfei.mytest.model.OverallUseStatisticsResponse;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: LiuBingFei
 * @Date: 2020-06-18 16:49
 * @Description: Stream操作
 */
public class SteamTest {

    /**
     * List Map 遍历
     */
    public void test1(){

        //List遍历
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        list.stream().forEach(overallUseStatisticsResponse -> System.out.println(overallUseStatisticsResponse.getStaffNumber()));

        //Map遍历
        Map<String, List<OverallUseStatisticsResponse>> map = new HashMap<>();
        map.forEach((key, value) -> value.stream().forEach(overallUseStatisticsResponse -> System.out.println()));


    }

    /**
     * 分组、分组统计
     */
    public void test2(){
        //List根据organId分组
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        Map<String, List<OverallUseStatisticsResponse>> map1 = list.stream().collect(Collectors.groupingBy(OverallUseStatisticsResponse::getOrganId));
        //List根据organId分组，并统计int BigDecimal类型
        Map<String, Integer> map2 = list.stream().collect(Collectors.groupingBy(OverallUseStatisticsResponse::getOrganId, Collectors.summingInt(OverallUseStatisticsResponse::getStaffNumber)));
        Map<String, BigDecimal> map3 = list.stream().collect(Collectors.groupingBy(OverallUseStatisticsResponse::getOrganId, Collectors.reducing(BigDecimal.ZERO, OverallUseStatisticsResponse::getEmptyArea, BigDecimal::add)));
    }

    /**
     * 合计
     */
    public void test3(){
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        Integer actualNumber = list.stream().mapToInt(OverallUseStatisticsResponse::getActualNumber).sum();
        BigDecimal emptyArea = list.stream().map(OverallUseStatisticsResponse::getEmptyArea).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * List合并记录，并累加。
     */
    public void test4(){
        //把每一条记录相加，第一条记录为总和。
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        list.stream().reduce((a, b) ->{
            a.setStaffNumber(a.getStaffNumber() + b.getStaffNumber());
            a.setActualNumber(a.getActualNumber() + b.getActualNumber());
            a.setEmptyArea(a.getEmptyArea().add(b.getEmptyArea()));
            return a;
        });
        OverallUseStatisticsResponse sum = list.get(0);
    }

    /**
     * 合计List
     */
    public void test4_2(){
        //带合计的数据集合
        List<ExcessiveOrganExport> excessiveOrganExportList = new ArrayList<>();
        //添加合计行
        ExcessiveOrganExport excessiveOrganExport = excessiveOrganExportList.stream().reduce((o1, o2) -> new ExcessiveOrganExport("合计",
                o1.getOfficeCheckArea().add(o2.getOfficeCheckArea()), o1.getOfficeRealUseArea().add(o2.getOfficeRealUseArea()), o1.getOfficeExcessiveArea().add(o2.getOfficeExcessiveArea()),
                o1.getServiceCheckArea().add(o2.getServiceCheckArea()), o1.getServiceRealUseArea().add(o2.getServiceRealUseArea()), o1.getServiceExcessiveArea().add(o2.getServiceExcessiveArea()),
                o1.getEquipCheckArea().add(o2.getEquipCheckArea()), o1.getEquipRealUseArea().add(o2.getEquipRealUseArea()), o1.getEquipExcessiveArea().add(o2.getEquipExcessiveArea())))
                .orElse(new ExcessiveOrganExport("合计", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        excessiveOrganExportList.add(excessiveOrganExport);
    }

    /**
     * List转Map
     */
    public void test5(){
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        Map<String, String> map1 = list.stream().collect(Collectors.toMap(OverallUseStatisticsResponse::getOrganId, OverallUseStatisticsResponse::getOrganName, (k1,k2) ->k2));
        Map<String, OverallUseStatisticsResponse> map2 = list.stream().collect(Collectors.toMap(OverallUseStatisticsResponse::getOrganId, a -> a, (o1,o2) -> o2));
        Map<String, List<OverallUseStatisticsResponse>> map3 = list.stream().collect(Collectors.groupingBy(OverallUseStatisticsResponse::getOrganId));
    }

    /**
     * 排序、过滤、去重
     */
    public void test6(){
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        List<OverallUseStatisticsResponse> list2 = (List<OverallUseStatisticsResponse>) list.stream().sorted(Comparator.comparing(OverallUseStatisticsResponse::getAccessoryArea).reversed().thenComparing(OverallUseStatisticsResponse::getAreaLevel));
        list2.stream().filter(overallUseStatisticsResponse -> overallUseStatisticsResponse.getActualNumber() > 2).distinct().collect(Collectors.toList());
        list.sort(Comparator.comparing(OverallUseStatisticsResponse::getActualNumber));
    }

    /**
     * 排序
     */
    @Test
    public void test7(){
        String str = "X100110021002,X100110021001,X100110021000,X10021002,X10021001,X10021000,X10011002,X10011001,X10011000,X1002,X1001,X1000,X";
        String[] array = str.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(array));
        list.sort(String::compareTo);
        list.stream().forEach(a -> System.out.println("["+a+"]"));
    }

    /**
     * 截取集合
     */
    @Test
    public void test8(){
        List<String> abc = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        List<String> result1 = abc.subList(1,3);
        List<String> result2 = abc.stream().skip(3).collect(Collectors.toList());
        List<String> result3 = abc.stream().limit(1).collect(Collectors.toList());
        System.out.println(result1.toString());
        System.out.println(result2.toString());
        System.out.println(result3.toString());
    }

    @Test
    public void test9(){
        String abc = "abc";
        System.out.println(Arrays.asList(abc.split(",")));
    }

    @Test
    public void test10(){
        String abc = "abc";
        String def = abc +'%';
        String name = abc + "%";
        System.out.println(def + "," +name);
    }





}
