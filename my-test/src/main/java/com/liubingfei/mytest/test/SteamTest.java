package com.liubingfei.mytest.test;

import com.liubingfei.mytest.model.ExcessiveOrganExport;
import com.liubingfei.mytest.model.OverallUseStatisticsResponse;
import com.liubingfei.mytest.model.SysResourcesResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
     * 合计List：reduce
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
        //升序
        list.sort(String::compareTo);
        list.stream().forEach(a -> System.out.println("["+a+"]"));

        //降序
        List<Integer> numberList = new ArrayList<>(Arrays.asList(9, 2, 3, 4, 5));
        //注意：o1 代表后面一个数 o2代表前面一个数
        numberList.sort((o1, o2) -> {
            if(o1.compareTo(o2) >= 0){
                return -1;//-1表示交换两个数的位置 所以这里实现的是降序排列
            }
            return 1;
        });
        numberList.stream().forEach(a -> System.out.println("["+a+"]"));
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

    @Test
    public void test11(){
        String a = "a";
        String b = "b";
        List<String> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        String c = null;
        if(list.contains(c)){
            System.out.println("xxxxxxxxxxxxx");
        }else{
            System.out.println("yyyyyyyyyyyyy");
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(100.100);
        bigDecimal.toString();
        System.out.println(bigDecimal.toString());
        System.out.println(String.valueOf(bigDecimal));

    }

    /**
     * 拼接字符串测试
     */
    @Test
    public void test12(){
        String a = "a";
        String b = "b";
        String c = null;
        String d = "";
        String e = "e";
        String result1 = StringUtils.join(a,b);
        String result2 = StringUtils.join(a,b,c);
        String result3 = StringUtils.join(a,b,c,d);
        String result4 = StringUtils.join(a,b,c,d,e);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }

    @Test
    public void test13(){
        // redis 宕机时采用时间戳加随机数
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Random random = new Random();
        //14位时间戳到 + 6位随机数
        timeStamp +=(random.nextInt(10)+"") + (random.nextInt(10)+"") + (random.nextInt(10)+"");
        System.out.println(timeStamp);
        timeStamp +=(random.nextInt(10)+"") + (random.nextInt(10)+"") + (random.nextInt(10)+"");
        System.out.println(timeStamp);
    }

    /**
     * 排序
     */
    @Test
    public void test14(){
        //降序
        List<Integer> numberList = new ArrayList<>(Arrays.asList(9, 2, 3, 4, 5));
        //注意：o1 代表后面一个数 o2代表前面一个数
        numberList.sort((o1, o2) -> {
            if(o1.compareTo(o2) >= 0){
                return -1;//-1表示交换两个数的位置 所以这里实现的是降序排列
            }
            return 1;
        });
        numberList.stream().forEach(a -> System.out.println("["+a+"]"));
    }

    /**
     * Set
     */
    @Test
    public void test15(){
        Set<String> set = new HashSet<>();
        set.add("9");
        set.add("2");
        set.add("9");
        set.add("6");
        set.add("5");
        set.add("5");
        System.out.println(set.toString() + set.size());
    }

    @Test
    public void test16(){
        SysResourcesResponse sysResourcesResponse = new SysResourcesResponse();
        sysResourcesResponse.setId("1a");
        sysResourcesResponse.setChildren(new ArrayList<>(Arrays.asList(new SysResourcesResponse().setId("2a"), new SysResourcesResponse().setId("2b"))));
        sysResourcesResponse.getChildren().get(0).setChildren(new ArrayList<>(Arrays.asList(new SysResourcesResponse().setId("3a"), new SysResourcesResponse().setId("3b"))));
        System.out.println("1:" + sysResourcesResponse.toString());
        removeNotFirstResources(sysResourcesResponse);
        System.out.println("``````````````````````````````````````````````````");
        System.out.println("2:" + sysResourcesResponse.toString());
    }

    public void removeNotFirstResources(SysResourcesResponse leftResource) {
        List<SysResourcesResponse> childrenList = leftResource.getChildren();
        if (Objects.isNull(childrenList) || childrenList.isEmpty() || childrenList.size() == 1) {
            return;
        }
        //删除非第一个元素
        for (int i = 0; i < childrenList.size(); i++) {
            if (i != 0) {
                childrenList.remove(i);
                i--;
            }
        }
        SysResourcesResponse first =  childrenList.get(0);
        removeNotFirstResources(first);
    }

    @Test
    public void test17(){
        if("".contains("")){
            System.out.println("空包含空");
        }
        if("123".contains("")){
            System.out.println("字符串包含空");
        }
    }

    @Test
    public void test18(){
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(0,9,8));
        list1.addAll(3,list2);
        System.out.println(list1.toString());

    }

    /**
     * 查询日期间隔
     *
     * @return
     */
    @Test
    public void dateDuration() {
        LocalDate start = LocalDate.parse("2021-03-03");
        LocalDate end = LocalDate.parse("2021-03-04");
        Long result = 0L;
        result = start.until(end, ChronoUnit.DAYS);
        System.out.println(result);

        LocalDate endDate = start.plusMonths(1);
        System.out.println(endDate);
    }
}
