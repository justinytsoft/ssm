package com.FangBianMian.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IOrderDao;
import com.FangBianMian.domain.Orders;
import com.FangBianMian.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Override
	public List<Orders> queryOrdersByParam(Map<String, Object> param) {
		return orderDao.queryOrdersByParam(param);
	}

	@Override
	public int queryOrdersByParamTotal(Map<String, Object> param) {
		return orderDao.queryOrdersByParamTotal(param);
	}

	@Override
	public void updateOrderStatus(Map<String, Object> param) {
		orderDao.updateOrderStatus(param);
	}
	
}
