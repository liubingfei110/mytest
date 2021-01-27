package com.liubingfei.mytest.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: LiuBingFei
 * @Date: 2020-06-16 15:22
 * @Description:
 */
@Data
public class OverallUseStatisticsResponse implements Serializable {

    private static final long serialVersionUID = -3359786945266594698L;

    /**
     * 单位ID
     */
    private String organId;

    /**
     * 单位名称
     */
    private String organName;

    /**
     * 编制人数
     */
    private Integer staffNumber;

    /**
     * 实际人数
     */
    private Integer actualNumber;

    /**
     * 空置使用面积
     */
    private BigDecimal emptyArea;

    /**
     * 办公室使用面积
     */
    private BigDecimal officeArea;

    /**
     * 服务用房使用面积
     */
    private BigDecimal serviceArea;

    /**
     * 设备用房使用面积
     */
    private BigDecimal equipmentArea;

    /**
     * 基本办公用房总使用面积
     */
    private BigDecimal officeSumArea;

    /**
     * 附属用房建筑面积
     */
    private BigDecimal accessoryArea;

    /**
     * 业务技术用房建筑面积
     */
    private BigDecimal techArea;

    /**
     * 排序值
     */
    private BigDecimal sortNo;

    /**
     * 行政区划代码
     */
    private String areaId;

    /**
     * 行政区划名称
     */
    private String areaName;

    /**
     * 行政区划节点是否可以向下钻，1不可以；0可以
     */
    private String codeType;

    /**
     * 行政区划级别：1、2、3、4
     */
    private Integer areaLevel;

    /**
     * 上级行政区划代码
     */
    private String areaParentId;
}
