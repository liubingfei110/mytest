package com.liubingfei.mytest.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: LiuBingFei
 * @Date: 2021-01-27 9:48
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcessiveOrganExport {
    @Excel(name = "单位名称", orderNum = "1", width = 25)
    private String organName;

    @Excel(name = "办公室核定使用面积上限(㎡)", orderNum = "2", width = 25)
    private BigDecimal officeCheckArea;

    @Excel(name = "办公室实际使用面积(㎡)", orderNum = "3", width = 25)
    private BigDecimal officeRealUseArea;

    @Excel(name = "办公室超标面积(㎡)", orderNum = "4", width = 25)
    private BigDecimal officeExcessiveArea;

    @Excel(name = "服务用房核定使用面积上限(㎡)", orderNum = "5", width = 25)
    private BigDecimal serviceCheckArea;

    @Excel(name = "服务用房实际使用面积(㎡)", orderNum = "6", width = 25)
    private BigDecimal serviceRealUseArea;

    @Excel(name = "服务用房超标面积(㎡)", orderNum = "7", width = 25)
    private BigDecimal serviceExcessiveArea;

    @Excel(name = "设备用房核定使用面积上限(㎡)", orderNum = "8", width = 25)
    private BigDecimal equipCheckArea;

    @Excel(name = "设备用房实际使用面积(㎡)", orderNum = "9", width = 25)
    private BigDecimal equipRealUseArea;

    @Excel(name = "设备用房超标面积(㎡)", orderNum = "10", width = 25)
    private BigDecimal equipExcessiveArea;
}
