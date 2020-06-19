package com.jeesite.modules.product.entity;

import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * @author:ZHF
 * @Time: 2020/2/20 15:44
 */
@Table(name="ht_product_child_middle", alias="a", columns={
        @Column(name="id", attrName="id", label="id", isPK=true),
        @Column(name="group_product_id", attrName="groupProductId", label="产品名称"),
        @Column(name="group_product_child_id", attrName="groupProductChildId", label="产品编码")
}
)
public class ProductChildMiddle {
    private int id;
    private String groupProductId;//组合产品的ID
    private String groupProductChildId; //组合产品的子表ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupProductId() {
        return groupProductId;
    }

    public void setGroupProductId(String groupProductId) {
        this.groupProductId = groupProductId;
    }

    public String getGroupProductChildId() {
        return groupProductChildId;
    }

    public void setGroupProductChildId(String groupProductChildId) {
        this.groupProductChildId = groupProductChildId;
    }
}
