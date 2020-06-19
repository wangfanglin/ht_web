package com.jeesite.modules.policy.entity;

import com.jeesite.modules.channel.entity.ChannelProInfo;
import com.jeesite.modules.channel.entity.ChannelProtype;
import com.jeesite.modules.channel.entity.ChannelSale;

/**
 *
 * @author tangchao
 */
public interface Policy {
    /**
     * 获取手机型号
     * @return  手机型号
     */
    String getStrphonemodel();

    /**
     * 获取手机品牌
     * @return  手机品牌
     */
    String getStrphonebrand();

    /**
     * 获取2.0对应的数据
     * @return
     */
    PolicyInfo convertNew();

    /**
     * 获取主键id
     * @return
     */
    String getId();

    /**
     * 获取产品id
     * @return
     */
    String getChannelProInfoId();

    /**
     * 获取渠道
     * @return
     */
    String getStrchannelname();



    String getNewModel();

    void setNewModel(String newModel);



    String getNewBrand();

    void setNewBrand(String newBrand);


    String getNewChannelProInfoId();

    void setNewChannelProInfoId(String newChannelProInfoId);



    String getNewChannelId();

    void setNewChannelId(String newChannelId);

    String getStrimeinum();

    ChannelSale getChannelSale();

    ChannelProtype getChannelProtype();
}
