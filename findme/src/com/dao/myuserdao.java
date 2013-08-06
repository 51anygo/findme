package com.dao;

import com.entity.TBCELL2GPS;
import com.entity.TBGPSDATA;
import com.entity.TBGPSOFFSET;
import com.entity.TBGPSSHARE;
import com.entity.TBIPDATA;
import com.entity.TBSYSTEMSET;
import com.entity.TBUSER;

public interface  myuserdao {
	public boolean addUser(TBUSER u);
	public boolean updateUser(TBUSER u);
	public TBUSER getUser(Integer uid);
	public boolean deleteUser(TBUSER u);
	public TBUSER login(TBUSER u);
	public TBUSER getUser(TBUSER u);
	public boolean updategps(TBUSER u,TBGPSDATA gpsdata);
	public boolean updatecell2gps(TBCELL2GPS cell2gpsdata);
	public TBGPSDATA lastestgpsdata(boolean bonlygps,TBUSER u);
	public TBGPSDATA getgpsdatabytime(boolean bonlygps,TBUSER u,String strquerytime);
	public boolean isexists(String strusername);
	public TBUSER isemailexists(String stremail);
	public TBIPDATA getaddressbyip(String strip);
	public TBGPSOFFSET getgpsoffset(final TBGPSDATA gpsdata);
	public TBGPSSHARE getGpsshare(Double lat,Double lng);
	public TBGPSDATA getGpssharebytime(int shareid, String strquerytime);
	public TBSYSTEMSET getsystemset(String strname);
	public TBCELL2GPS getcell2gps(final int MCC,final int MNC,final int LAC,
			                      final int CID,final int BSHORTCID);
}
