package com.anygo.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anygo.entity.MyConfig;
import com.j256.ormlite.dao.Dao;


public class MyConfigService implements IMyConfigService {
	private Dao<MyConfig, Integer> myconfigDao = null;

	public MyConfigService(Dao<MyConfig, Integer> dao) {
		myconfigDao = dao;
	}

	@Override
	public int save(MyConfig myconfig) {
		// TODO Auto-generated method stub
		boolean result = false;

		try {
			//myconfigDao.delete(myconfig);
			myconfigDao.update(myconfig);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public MyConfig load() {
		MyConfig myconfig = null;

		Map<String, Object> myconfigMap = new HashMap<String, Object>();

		try {
			List<MyConfig> list = myconfigDao.queryForFieldValues(myconfigMap);
			myconfig = list != null && list.size() == 1 ? list.get(0) : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return myconfig;
	}
	
	public boolean addMyConfig(MyConfig myconfig) {
		boolean result = false;

		try {
			myconfigDao.create(myconfig);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public MyConfig findMyconfigByID(int myconfigID) {
		MyConfig myconfig = null;

		Map<String, Object> accountMap = new HashMap<String, Object>();
		accountMap.put("aId", myconfigID);
		try {
			List<MyConfig> list = myconfigDao.queryForFieldValues(accountMap);
			myconfig = list != null && list.size() == 1 ? list.get(0) : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return myconfig;
	}

}
