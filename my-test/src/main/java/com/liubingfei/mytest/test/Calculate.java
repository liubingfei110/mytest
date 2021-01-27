package com.liubingfei.mytest.test;

import com.liubingfei.mytest.model.SysOrganizationNode;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: LiuBingFei
 * @Date: 2020-07-14 10:22
 * @Description:
 */
public class Calculate {
    /**
     * 超大型整数相加
     */
    @Test
    public void test1(){
        String a1 = "111111111111111111111111111111111111111111111111111111111111";
        String a2 = "999999999999999999999999999999999999999999999999999999999999";
        System.out.println("长度：a1:"+ a1.length() + ", a2:"+a2.length());
        LocalDateTime localDateTime1 = LocalDateTime.now();
        BigInteger int1 = new BigInteger("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        BigInteger int2 = new BigInteger("99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
        System.out.println(int1.add(int2));
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        System.out.println("耗时：" + duration.toMillis() + "毫秒，" + duration.toNanos() + "纳秒。");
    }

    @Test
    public void test2(){
        SysOrganizationNode sysOrganizationNode = new SysOrganizationNode();
        sysOrganizationNode.setNodeCode("1001,1002");
        sysOrganizationNode.setId("1");
        List<String> sysOrganizationNodeList = new ArrayList<>(Arrays.asList(sysOrganizationNode.getNodeCode().split(",")));
        List<SysOrganizationNode> sysOrganizationNodeList1 = new ArrayList<>();
        sysOrganizationNodeList.stream().forEach(nodeCode ->{
            SysOrganizationNode node = new SysOrganizationNode();
            node.setNodeCode(nodeCode);
            node.setId(sysOrganizationNode.getId());
            sysOrganizationNodeList1.add(node);
        });
        System.out.println(sysOrganizationNodeList1.toString());

    }

    @Test
    public void test3(){
        List<String> list = new ArrayList<>();
        list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");list.add("6");list.add("7");
        System.out.println(list.toString());
        System.out.println("0-3:"+list.subList(0,3));
        System.out.println("1-3:"+list.subList(1,3));
        System.out.println("3-3:"+list.subList(3,3));
        System.out.println("4-7:"+list.subList(4,7));


    }


}
