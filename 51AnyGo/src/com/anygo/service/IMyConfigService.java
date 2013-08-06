package com.anygo.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anygo.entity.MyConfig;


public interface IMyConfigService {

	public int save(MyConfig myconfig);
	public MyConfig load();
	public boolean addMyConfig(MyConfig myconfig);
	public MyConfig findMyconfigByID(int myconfigID);
}
