package com.FangBianMian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.BulletinType;
import com.FangBianMian.domain.City;
import com.FangBianMian.domain.PaymentType;
import com.FangBianMian.domain.Position;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.Province;

public interface IBaseDao {
	
	/**
	 * 查询商品一级分类
	 * @return
	 */
	List<ProductCategory> queryProductCategory();
	
	/**
	 * 查询省份
	 * @return
	 */
	List<Province> queryProvince();

	/**
	 * 查询城市
	 * @return
	 */
	List<City> queryCityByPid(@Param("pid") Integer pid);

	/**
	 * 查询区县
	 * @param cid
	 * @return
	 */
	List<Position> queryPositionByCid(@Param("cid") Integer cid);

	/**
	 * 获取支付方式
	 * @return
	 */
	List<PaymentType> queryPaymentType();

	/**
	 * 查询省份的名称
	 * @param province
	 * @return
	 */
	String queryProvinceNameById(@Param("id") Integer province);

	/**
	 * 查询区县的名称
	 * @param province
	 * @return
	 */
	String queryPositionNameById(@Param("id") Integer position);

	/**
	 * 查询城市的名称
	 * @param province
	 * @return
	 */
	String queryCityNameById(@Param("id") Integer city);

	/**
	 * 获取公告类型
	 * @return
	 */
	List<BulletinType> queryBulletinType();
}
