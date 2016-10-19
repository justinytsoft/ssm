package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.ProductComment;


public interface IProductService {

	/**
	 * 通过商品ID查询商品
	 * @param id 商品ID
	 * @param status 是否上架 true 上架, flase 下架, null 不添加该条件
	 * @return
	 */
	Product queryProductById(Integer id, Boolean status);

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

	/**
	 * 通过分类ID获取分类
	 * @param category_id
	 * @return
	 */
	ProductCategory queryProductCategoryById(Integer category_id);

	/**
	 * 保存商品评论
	 * @param pid
	 * @param comment
	 */
	void insertProductComment(ProductComment pc);

	/**
	 * 更新商品
	 * @param p
	 */
	void updateProduct(Product p);

}
