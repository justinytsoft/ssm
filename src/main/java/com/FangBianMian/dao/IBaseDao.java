package com.FangBianMian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.City;
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
}
