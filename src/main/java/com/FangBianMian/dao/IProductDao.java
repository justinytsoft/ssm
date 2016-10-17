package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductComment;
import com.FangBianMian.domain.ProductImg;

public interface IProductDao {

	Product queryProductById(@Param("id") Integer id);

	List<Product> queryProductsByParam(@Param("param") Map<String, Object> param);

	int queryProductsByParamTotal(@Param("param") Map<String, Object> param);

	void updateProduct(Product p);

	void insertProduct(Product p);

	void deleteProductImgs(@Param("pid") Integer id);

	void insertProductImgs(@Param("pid") Integer id, @Param("imgs") String[] productImgs);

	List<ProductImg> selectProductImgByPid(@Param("id") Integer id);

	void deleteProductById(@Param("id") Integer id);

	List<ProductComment> queryProductComments(@Param("param") Map<String, Object> param);

	int queryProductCommentsTotal(@Param("param") Map<String, Object> param);

}
