package com.anygo.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anygo.entity.MyCellPos;


public interface IMyCellPosService {

	public int save(MyCellPos myCellPos);
	public MyCellPos load();
}
