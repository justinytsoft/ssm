package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Orders;

public interface IOrderService {

	List<Orders> queryOrdersByParam(Map<String, Object> param);

	int queryOrdersByParamTotal(Map<String, Object> param);

	void updateOrderStatus(Map<String, Object> param);

}
