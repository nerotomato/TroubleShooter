package com.nerotomato.dynamic.service;

import com.nerotomato.dynamic.entity.OmsOrder;

/**
 * Created by nero on 2021/5/12.
 */
public interface OmsOrderService extends BaseService<OmsOrder> {
    Object saveMultiOrders(int nums);

    Object batchSaveOrders(int nums);
}
