package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Product;

public interface IProductDao {

	Product queryProductById(@Param("id") Integer id);

	List<Product> queryProductsByParam(@Param("param") Map<String, Object> param);

	int queryProductsByParamTotal(@Param("param") Map<String, Object> param);

}
