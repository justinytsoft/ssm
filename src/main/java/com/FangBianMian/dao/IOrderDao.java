package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Orders;
import com.FangBianMian.domain.OrdersItem;
import com.FangBianMian.domain.OrdersLog;

public interface IOrderDao {

	List<Orders> queryOrdersByParam(@Param("param") Map<String, Object> param);

	int queryOrdersByParamTotal(@Param("param") Map<String, Object> param);

	void updateOrderStatus(@Param("param") Map<String, Object> param);

	Orders queryOrdersByOid(@Param("id") Integer id);

	void insertOrder(Orders o);

	void insertOrderItem(OrdersItem oi);

	void insertOrderLog(OrdersLog ol);

	void deleteOrderById(@Param("oid") Integer oid);

}
