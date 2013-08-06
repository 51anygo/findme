package com.biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.dao.myuserdao;
import com.entity.TBCELL2GPS;
import com.entity.TBGPSDATA;
import com.entity.TBGPSSHARE;
import com.entity.TBUSER;

public interface UserBiz {
	public void setUserDao(myuserdao userDao);

	public String checkUser(String strusername);
	
	public String regUser(TBUSER u);
	
	public String checkEmail(String stremail);	
	
	public TBGPSDATA getGps(boolean bonlygps,HttpSession session);
	
	public TBGPSDATA getGpsbytime(boolean bonlygps,String strquerytime,HttpSession session);
	
	public TBGPSDATA getGpssharebytime(int shareid,String strquerytime);
	
	public TBUSER isLogin(HttpSession session);
	
	public String checkCRCNum(String num,HttpSession session);
	
	public TBUSER login(TBUSER u);
	
	public void logout(TBUSER u);
	
	public boolean save(TBUSER u);
	
	public boolean add(TBUSER u);
	
	public boolean updategps(TBUSER u,TBGPSDATA gpsdata);
	
	public boolean updatecell2gps(TBCELL2GPS cell2gpsdata);
	
	public boolean updateiamcalling(TBUSER u,TBCELL2GPS cell2gpsdata);
	
	public String[] getAddress(HttpServletRequest request);
	
	public String[] getAddressfromip(String strip);
	
	public String[] getAddressbyip(String strip,String num,HttpSession session);
	
	public String[] getAddressbylike(String strkey,String num,HttpSession session);
	
	public String[] getAddressbycell(String strcell,String num,HttpSession session); 
	
	public String getAddressbygoogle(Double lat,Double lng);
	
	public String getOppAddress(String mydstcalnum);
	
	public byte[] getgpsbycell(int MCC, int MNC, int LAC, int CID);
	
	public String getAddrbycellPrue(int MCC, int MNC, int LAC, int CID);
	
	public TBGPSSHARE getGpsshare(Double lat,Double lng);
	
	public byte[] getclientbytes(String strclientversion);
	
	public byte[] getclientlastver(String strclientversion);
	
	public TBGPSDATA getgpsbycellfromcache(int MCC, int MNC, int LAC, int CID,int BSHORTCID);
}
