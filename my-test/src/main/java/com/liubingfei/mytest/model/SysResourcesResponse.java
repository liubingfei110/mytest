package com.liubingfei.mytest.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ysyang
 * @Date: 2020-04-05 11:17
 * @Description: 资源response
 */
@Data
@Accessors(chain = true)
public class SysResourcesResponse implements Serializable {

    private static final long serialVersionUID = -3395332349789844801L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单链接
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 项目编码,当为项目时存在该值
     */
    private String projectCode;

    /**
     * 当录入项目时为0,后续为123菜单级别
     */
    private Integer level;

    /**
     * 标识是top菜单还是left菜单
     */
    private String type;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否可用YES/NO
     */
    private String enable;
    /**
     * 父节点集合
     */
    private List<String> parentIdList;

    /**
     * 子集资源
     */
    private List<SysResourcesResponse> children;

    /**
     * 值  前端所需结构
     */
    private String value;

    /**
     * 名称 前端所需结构
     */
    private String label;

    /**
     * 前端所需结构
     */
    private String typeFlag;

    /**
     * 角色资源关系表主键id
     */
    private String relationId;

    /**
     * 角色id
     */
    private String roleId;

//    /**
//     * 前端在配置按钮时，需要将菜单全部禁用
//     */
//    private Boolean disabled;

}