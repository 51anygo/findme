package com.anygo.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anygo.entity.MyCellPos;
import com.j256.ormlite.dao.Dao;


public class MyCellPosService implements IMyCellPosService {
	private Dao<MyCellPos, Integer> MyCellPosDao = null;

	public MyCellPosService(Dao<MyCellPos, Integer> dao) {
		MyCellPosDao = dao;
	}

	@Override
	public int save(MyCellPos myCellPos) {
		// TODO Auto-generated method stub
		boolean result = false;

		try {
			//MyCellPosDao.delete(MyCellPos);
			MyCellPosDao.update(myCellPos);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public MyCellPos load() {
		MyCellPos MyCellPos = null;

		Map<String, Object> MyCellPosMap = new HashMap<String, Object>();

		try {
			List<MyCellPos> list = MyCellPosDao.queryForFieldValues(MyCellPosMap);
			MyCellPos = list != null && list.size() == 1 ? list.get(0) : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return MyCellPos;
	}

}
