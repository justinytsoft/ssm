package com.FangBianMian.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IProductDao;
import com.FangBianMian.domain.Product;
import com.FangBianMian.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;

	@Override
	public Product queryProductById(Integer id) {
		return productDao.queryProductById(id);
	}

	@Override
	public List<Product> queryProductsByParam(Map<String, Object> param) {
		return productDao.queryProductsByParam(param);
	}

	@Override
	public int queryProductsByParamTotal(Map<String, Object> param) {
		return productDao.queryProductsByParamTotal(param);
	}
}
