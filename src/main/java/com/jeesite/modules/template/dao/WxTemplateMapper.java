package com.jeesite.modules.template.dao;


import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.template.entity.WxTemplate;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface WxTemplateMapper  {

    int insert(WxTemplate record);

    int insertSelective(WxTemplate record);

    List<WxTemplate> findByTemplateType(WxTemplate wxTemplate);

    List<WxTemplate> select (Map params);
}