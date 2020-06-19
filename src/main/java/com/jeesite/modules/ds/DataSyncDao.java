package com.jeesite.modules.ds;

import com.jeesite.common.mybatis.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DataSyncDao {
    /**
     * 查询1.0机型
     * @return
     */
    List<Map<String,Object>> findOldPhoneModel();

    /**
     * 查询1.0品牌
     * @return
     */
    List<String> findOldbrand();

    /**
     * 查询1.0 采购渠道
     * @return
     */
    List<Map<String,Object>> findOldPurchasing();

    /**
     * 查询1.0快递渠道
     * @return
     */
    List<Map<String,Object>> findOldExpressage();

    /**
     * 查询1.0 销售渠道
     * @return
     */
    List<Map<String,Object>> findOldSale();

    /**
     * 查询1.0中介服务商
     * @return
     */
    List<Map<String,Object>> findOldAgent();

    /**
     * 查询1.0保险服务商
     * @return
     */
    List<Map<String,Object>> findOldSupplier();

    /**
     * 查询 1.0 渠道商品
     * @return
     */
    List<Map<String,Object>> findOldProduct();

    /**
     * 查新1.0维修网点
     * @return
     */
    List<Map<String,Object>> findOldMaintenancePoint();
}
