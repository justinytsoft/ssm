package com.FangBianMian.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IBulletinDao;
import com.FangBianMian.domain.Bulletin;
import com.FangBianMian.service.IBulletinService;

@Service
public class BulletinServiceImpl implements IBulletinService {

	@Autowired
	private IBulletinDao bulletinDao;

	@Override
	public Bulletin queryBulletinById(Integer id) {
		return bulletinDao.queryBulletinById(id);
	}

	@Override
	public void saveBulletin(Bulletin b) {
		if(b.getId()!=null){
			bulletinDao.updateBulletin(b);
		}else{
			bulletinDao.insertBulletin(b);
		}
	}

	@Override
	public List<Bulletin> queryBulletins(Map<String, Object> param) {
		return bulletinDao.queryBulletins(param);
	}

	@Override
	public int queryBulletinsTotal(Map<String, Object> param) {
		return bulletinDao.queryBulletinsTotal(param);
	}

	@Override
	public void deleteBulletinById(Integer id) {
		bulletinDao.deleteBulletinById(id);
	}
}
