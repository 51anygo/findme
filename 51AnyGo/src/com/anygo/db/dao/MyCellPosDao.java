package com.anygo.db.dao;

import java.sql.SQLException; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 
 
import com.anygo.entity.MyCellPos;
import com.j256.ormlite.dao.Dao; 

public class MyCellPosDao {
	  public List<MyCellPos> findCellPos(Dao<MyCellPos, Integer> mycellposdao,int mnc,int mcc,int lac,int lcd) throws SQLException 
	    { 
	        Dao<MyCellPos, Integer> mycellposDao = mycellposdao; 
	        Map<String, Object> userMap=new HashMap<String, Object>(); 
	        userMap.put("mnc", mnc); 
	        userMap.put("mcc", mcc); 
	        userMap.put("lac", lac); 
	        userMap.put("lcd", lcd); 
	        List<MyCellPos> userlistEntities=mycellposDao.queryForFieldValues(userMap); 
	        return userlistEntities==null?null:userlistEntities; 
	    } 

	  public void addCellPos(Dao<MyCellPos, Integer> userdao,int mnc,int mcc,int lac,int lcd,String addr) throws SQLException 
	    { 
	        Dao<MyCellPos, Integer> userDao = userdao; 
	        MyCellPos MyCellPos=new MyCellPos(); 
	        MyCellPos.setMnc(mnc); 
	        MyCellPos.setMcc(mcc); 
	        MyCellPos.setLac(lac); 
	        MyCellPos.setLcd(lcd); 
	        MyCellPos.setAddr(addr);
	        userDao.create(MyCellPos); 
	    } 
}
