package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Bulletin;

public interface IBulletinDao {

	Bulletin queryBulletinById(@Param("id") Integer id);

	void updateBulletin(Bulletin b);

	void insertBulletin(Bulletin b);

	List<Bulletin> queryBulletins(@Param("param") Map<String, Object> param);

	int queryBulletinsTotal(@Param("param") Map<String, Object> param);

	void deleteBulletinById(@Param("id") Integer id);

}
