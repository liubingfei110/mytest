package com.liubingfei.mytest.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: LiuBingFei
 * @Date: 2020-04-30 10:53
 * @Description:
 */
@Data
public class ReportUseOrgan {
    /**
     * 关联其他表的主键ID
     */
    private String relateId;

    /**
     * 申请单id
     */
    private String dataReportOrderId;

    /**
     * 单位名称
     */
    private String reportOrganName;

    /**
     * 单位类别
     */
    private String reportOrganType;

    /**
     * 市级正职
     */
    private Integer reportCityStand;

    /**
     * 市级副职
     */
    private Integer reportCityDeputy;

    /**
     * 正局(处)级
     */
    private Integer reportStandBureauRank;

    /**
     * 副局(处)级
     */
    private Integer reportDeputyBureauRank;

    /**
     * 局(处)级以下
     */
    private Integer reportBureauRankDown;

    /**
     * 县级正职
     */
    private Integer reportCountyStand;

    /**
     * 县级副职
     */
    private Integer reportCountyDeputy;

    /**
     * 正科级
     */
    private Integer reportStandAdministrative;

    /**
     * 副科级
     */
    private Integer reportDeputyAdministrative;

    /**
     * 科级以下
     */
    private Integer reportAdministrativeDown;

    /**
     * 乡级正职
     */
    private Integer reportVillageStand;

    /**
     * 乡级副职
     */
    private Integer reportVillageDeputy;

    /**
     * 乡级以下
     */
    private Integer reportVillageDown;

    /**
     * 排序
     */
    private Integer orderNo;
}
