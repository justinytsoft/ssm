package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Bulletin;

public interface IBulletinService {

	/**
	 * 通过ID查询公告
	 * @param id
	 * @return
	 */
	Bulletin queryBulletinById(Integer id);

	/**
	 * 保存或更新公告
	 * @param b
	 */
	void saveBulletin(Bulletin b);

	/**
	 * 查询所有公告
	 * @param param
	 * @return
	 */
	List<Bulletin> queryBulletins(Map<String, Object> param);

	/**
	 * 查询所有公告总数
	 * @param param
	 * @return
	 */
	int queryBulletinsTotal(Map<String, Object> param);

	/**
	 * 通过ID删除公告
	 * @param id
	 */
	void deleteBulletinById(Integer id);

}
