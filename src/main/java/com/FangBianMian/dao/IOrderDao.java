package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Orders;

public interface IOrderDao {

	List<Orders> queryOrdersByParam(@Param("param") Map<String, Object> param);

	int queryOrdersByParamTotal(@Param("param") Map<String, Object> param);

	void updateOrderStatus(@Param("param") Map<String, Object> param);

}
