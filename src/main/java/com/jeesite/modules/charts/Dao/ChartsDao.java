package com.jeesite.modules.charts.Dao;


import com.jeesite.common.mybatis.annotation.MyBatisDao;

@MyBatisDao
public interface ChartsDao {
    int dayAll();
    int staffAll();
    int userAll();
    int channelAll();
}
