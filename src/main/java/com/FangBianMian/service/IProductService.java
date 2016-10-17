package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductComment;


public interface IProductService {

	/**
	 * 通过商品ID查询商品
	 * @param id
	 * @return
	 */
	Product queryProductById(Integer id);

	/**
	 * 查询所有商品
	 * @param param
	 * @return
	 */
	List<Product> queryProductsByParam(Map<String, Object> param);

	/**
	 * 查询所有商品总数
	 * @param param
	 * @return
	 */
	int queryProductsByParamTotal(Map<String, Object> param);

	/**
	 * 保存商品
	 * @param p
	 * @param productImgs
	 */
	void saveProduct(Product p, String[] productImgs);

	/**
	 * 删除商品
	 * @param id
	 */
	void deleteProductById(Integer id);

	/**
	 * 查询商品评论
	 * @param param
	 * @return
	 */
	List<ProductComment> queryProductComments(Map<String, Object> param);

	/**
	 * 查询商品评论总数
	 * @param param
	 * @return
	 */
	int queryProductCommentsTotal(Map<String, Object> param);

}
