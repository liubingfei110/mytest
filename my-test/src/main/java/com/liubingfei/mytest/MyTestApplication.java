package com.liubingfei.mytest;

import com.alibaba.fastjson.JSONObject;
import com.liubingfei.mytest.model.OverallUseStatisticsResponse;
import com.liubingfei.mytest.model.ReportUseOrgan;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MyTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(MyTestApplication.class, args);
    }

    @Test
    public void test1(){
        boolean flagA = true;
        boolean flagB = false;
        boolean flagC = false;

        if(flagA || flagB & flagC){
            System.out.println(1);
        }else{
            System.out.println(2);
        }

        if((flagA || flagB) & flagC ){
            System.out.println(11);
        }else{
            System.out.println(22);
        }

        if(flagA || (flagB & flagC) ){
            System.out.println(111);
        }else{
            System.out.println(222);
        }

    }

    @Test
    public void test2(){
        AtomicInteger atomicInteger = new AtomicInteger();
        for(int i = 0; i<10; i++){
            System.out.println(atomicInteger.getAndIncrement());
        }
    }

    @Test
    public void test3(){
        String useOrganStrings = "[{\"reportOrganName\":\"123\",\"reportOrganType\":\"THREE\",\"reportCityStand\":\"1\",\"reportCityDeputy\":\"2\",\"reportStandBureauRank\":\"3\",\"reportDeputyBureauRank\":\"4\",\"reportBureauRankDown\":\"5\",\"reportCountyStand\":\"\",\"reportCountyDeputy\":\"\",\"reportStandAdministrative\":\"\",\"reportDeputyAdministrative\":\"\",\"reportAdministrativeDown\":\"\",\"reportVillageStand\":\"\",\"reportVillageDeputy\":\"\",\"reportVillageDown\":\"\"}]";
        List<ReportUseOrgan> reportUseOrganList = JSONObject.parseArray(useOrganStrings, ReportUseOrgan.class);
        if (Objects.nonNull(reportUseOrganList) && reportUseOrganList.size() > 0) {
            AtomicInteger atomicInteger = new AtomicInteger();
            reportUseOrganList.stream().forEach(organ -> {
                organ.setRelateId("relateId");
                organ.setOrderNo(atomicInteger.getAndIncrement());
            });
        }
        System.out.println(reportUseOrganList.toString());
    }

    @Test
    public void test4(){
        Integer upFloorNum = 10;
        Integer downFloorNum = 2;

        List<String> upList = Stream.iterate(1, item -> item + 1).limit(upFloorNum).sorted(Comparator.reverseOrder()).map(upNum -> upNum + "层").collect(Collectors.toList());
        List<String> downList = Stream.iterate(1, item -> item + 1).limit(downFloorNum).map(downNum -> "负" + downNum + "层").collect(Collectors.toList());
        upList.addAll(downList);
        System.out.println(upList.toString());

    }

    @Test
    public void test5(){
        ReportUseOrgan reportUseOrgan = new ReportUseOrgan();
        reportUseOrgan.setDataReportOrderId("1");
        reportUseOrgan.setOrderNo(1);
        ReportUseOrgan reportUseOrgan2 = new ReportUseOrgan();
        reportUseOrgan2.setDataReportOrderId("2");
        reportUseOrgan2.setOrderNo(2);
        ReportUseOrgan reportUseOrgan3 = new ReportUseOrgan();
        reportUseOrgan3.setDataReportOrderId("2");
        reportUseOrgan3.setOrderNo(3);
        List<ReportUseOrgan> list = new ArrayList<>();
        list.add(reportUseOrgan);
        list.add(reportUseOrgan2);
        list.add(reportUseOrgan3);
        Map<String,Integer> map = list.stream().collect(Collectors.groupingBy(ReportUseOrgan::getDataReportOrderId, Collectors.summingInt(ReportUseOrgan::getOrderNo)));
        map.forEach((k,v) -> System.out.println(k + "  " + v));

    }

    @Test
    public void test6(){
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        System.out.println(date);
        System.out.println(year);
    }

    @Test
    public void test7() {
        ArrayList<String> list = new ArrayList<String>();
            list.add("a");
            list.add("b");
            list.add("c");
            list.add("d");
            System.out.println(list.subList(3, 4));
    }


    @Test
    public void test8(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        System.out.println(date);
    }

    /**
     * 合并集合--reduce
     */
    @Test
    public void test9() {
        OverallUseStatisticsResponse overallUseStatisticsResponse1 = new OverallUseStatisticsResponse();
        overallUseStatisticsResponse1.setAreaId("a");
        overallUseStatisticsResponse1.setStaffNumber(1);
        overallUseStatisticsResponse1.setActualNumber(2);
        OverallUseStatisticsResponse overallUseStatisticsResponse2 = new OverallUseStatisticsResponse();
        overallUseStatisticsResponse2.setAreaId("a");
        overallUseStatisticsResponse2.setStaffNumber(3);
        overallUseStatisticsResponse2.setActualNumber(4);
        OverallUseStatisticsResponse overallUseStatisticsResponse3 = new OverallUseStatisticsResponse();
        overallUseStatisticsResponse3.setAreaId("a");
        overallUseStatisticsResponse3.setStaffNumber(5);
        overallUseStatisticsResponse3.setActualNumber(6);
        List<OverallUseStatisticsResponse> list = new ArrayList<>();
        list.add(overallUseStatisticsResponse1);
        list.add(overallUseStatisticsResponse2);
        list.add(overallUseStatisticsResponse3);
        System.out.println(list.toString());
        list.stream().reduce((a,b) ->{
            a.setStaffNumber(a.getStaffNumber()+b.getStaffNumber());
            a.setActualNumber(a.getActualNumber() + b.getActualNumber());
            return a;
        });
        System.out.println(list.toString());
    }

    @Test
    public void test10(){
        String str = "INSTITUTIONS_SAME_LEVEL,VERTICAL_MANAGEMENT_INSTITUTIONS,AGENCY,GOVERNMENT_AFFILIATED_INSTITUTIONS,OTHER";
        System.out.println(">>>>>>>>>>>>>>>>>>>"+str.length());
    }

}



