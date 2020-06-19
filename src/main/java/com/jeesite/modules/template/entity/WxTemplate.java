package com.jeesite.modules.template.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 微信模板表
 *
 * @author wangfanglin
 * @date 2020/04/08
 */
@Data
@ApiModel
public class WxTemplate {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "模板标题")
    private String templateTitle;

    @ApiModelProperty(value = "一级行业")
    private String primaryIndustry;

    @ApiModelProperty(value = "二级行业")
    private String deputyIndustry;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板示例")
    private String example;

    @ApiModelProperty(value = "需要发送的字符串")
    private String sendContent;

    @ApiModelProperty(value = "模板使用时候区别")
    private String templateType;

    @ApiModelProperty(value = "头内容")
    private String headText;

    @ApiModelProperty(value = "尾内容")
    private String endText;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateDate;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "删除状态")
    private String delFlag;

    @ApiModelProperty(value = "原因")
    private String remarks;

}