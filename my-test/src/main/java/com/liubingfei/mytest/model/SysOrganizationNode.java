package com.liubingfei.mytest.model;

import lombok.Data;

/**
 * @Author: LiuBingFei
 * @Date: 2020-07-16 9:26
 * @Description:
 */
@Data
public class SysOrganizationNode {

    private String id;

    /**
     * 组织关系树ID
     */
    private String treeId;

    /**
     * 节点类型：文件夹节点：directory；普通节点：node
     */
    private String nodeType;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点子节点数量
     */
    private Integer childrenCount;

    /**
     * 该节点下被删除的子节点编码，逗号分隔。如 X1001,X1002,X1003...
     */
    private String childrenRemoveCode;

    /**
     * 节点层级：1、2、3...
     */
    private Integer level;

    /**
     * 是否是叶子节点 0不是，1是
     */
    private Integer leaf;

    /**
     * 查询范围：SELF本级；NEXT下级；ALL本级+下级
     */
    private String queryScope;

    /**
     * 是否有操作权限：0没有，1有。
     */
    private Integer operatePermission;

    /**
     * 父节点
     */
    private String parentNodeId;

    /**
     * 节点绑定数据ID（单位/区域ID）
     */
    private String nodeDataId;

    /**
     * 节点绑定数据名称（单位/区域名称）
     */
    private String nodeDataName;
}
