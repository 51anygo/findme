package com.net.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import com.net.formattransfer;
import com.net.mypackage;

public class updatecellgpspackage extends updategpspospackage {

	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
	private	int bshortcid;
	
	private	double  lat; 
	private	double  lng;  
	private	double  speed;
	private	double  angle;
	private	long    nowtime;
	private	int     needreply;
	private String strusername;
	private String strpassword;
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public long getNowtime() {
		return nowtime;
	}
	public void setNowtime(long nowtime) {
		this.nowtime = nowtime;
	}
	public int getNeedreply() {
		return needreply;
	}
	public void setNeedreply(int needreply) {
		this.needreply = needreply;
	}
	
	public String getStrusername() {
		return strusername;
	}
	public void setStrusername(String strusername) {
		this.strusername = strusername;
	}
	public String getStrpassword() {
		return strpassword;
	}
	public void setStrpassword(String strpassword) {
		this.strpassword = strpassword;
	}
	
	public mypackage parse(byte []pd)
	{ 		   
		int npos=PACKAGEHEADERLEN;
		if(pd.length<4*4+npos)
	    {
		  return null;
		}
		int usernamelen=0;
		int passwordlen=0;
		byte[] tempbytes = new byte[DOUBLELEN];
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mcc=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mnc=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lac=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lcd=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.bshortcid=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.needreply=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		usernamelen=formattransfer.lBytesToInt(tempbytes); 
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		passwordlen=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, LONGLEN);
		this.nowtime=formattransfer.lBytesToLong(tempbytes); 
		npos+=LONGLEN;
		//reserved
		npos+=LONGLEN;
		tempbytes = new byte[DOUBLELEN];
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.lat=formattransfer.lBytesToDouble(tempbytes); 
		npos+=DOUBLELEN;
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.lng=formattransfer.lBytesToDouble(tempbytes); 
		npos+=DOUBLELEN;
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.speed=formattransfer.lBytesToDouble(tempbytes); 
		npos+=DOUBLELEN;
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.angle=formattransfer.lBytesToDouble(tempbytes); 
		npos+=DOUBLELEN;
		tempbytes = new byte[usernamelen];
		System.arraycopy(pd, npos, tempbytes, 0, usernamelen);
		this.strusername=formattransfer.bytesToString(tempbytes);
		npos=npos+usernamelen; 
		tempbytes = new byte[passwordlen];
		System.arraycopy(pd, npos, tempbytes, 0, passwordlen);
		this.strpassword=formattransfer.bytesToString(tempbytes);
		return this;
	}
	
/*	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {
		if(this.strusername != null && !this.strusername.equals("") 
				&& this.strpassword!=null && !this.strpassword.equals(""))
		{		
			this.strusername=MySecurity.decrypt(this.strusername);
			this.strpassword=MySecurity.decrypt(this.strpassword);
			TBUSER u = new TBUSER();
			u.setUsername(this.strusername);
			u.setPassword(this.strpassword);
			u = userbiz.login(u);
			String strreply;
			if(u != null)
			{
				if(this.lat==0 || this.lng==0)
				{
					return;
				}
				if(this.lac>0 && this.lcd>0)
				{
					TBCELL2GPS cell2gps = new TBCELL2GPS();
					cell2gps.setMcc(this.mcc);
					cell2gps.setMnc(this.mnc);
					cell2gps.setLac(this.lac);
					cell2gps.setCid(this.lcd);
					cell2gps.setBshortcid(this.bshortcid);
					cell2gps.setLat(this.lat);
					cell2gps.setLng(this.lng);
					cell2gps.setUserid(u.getId());
					cell2gps.setUpdatetime(DateUtils.dateToStr(new Date()));
					userbiz.updatecell2gps(cell2gps);
				}
				String gpstime;
				String strangle=String.valueOf(this.angle);
				Date nowdate=new Date();
				nowdate.setTime(this.nowtime*1000);
				gpstime=DateUtils.dateToStr(nowdate);;
				TBGPSDATA gpsdata = new TBGPSDATA();
			    gpsdata.setLat(this.lat);
			    gpsdata.setLng(this.lng);
			    gpsdata.setAlt(Double.valueOf(0));
			    gpsdata.setAngle(strangle);
			    gpsdata.setSpeed(this.speed);
			    gpsdata.setGpstime(gpstime);
			    gpsdata.setUpdatetime(DateUtils.dateToStr(new Date()));
				userbiz.updategps(u, gpsdata);
				strreply="0";
			}
			else
			{
				strreply="1";
			}
			if(this.needreply==1)
			{
			  try 
			  {
	            PrintWriter out = response.getWriter();
	            out.write(strreply); //stand for the report success
	            out.close();
		      } 
			  catch (IOException e)
		      {
		        e.printStackTrace();
		      }
			}
			else
			{
			    //如果不需要回复则关闭socket再继续，以节约流量
				try
				{
					  Object myrequest = Reflector.getAccessibleField(request, "request");
			          Object coyoteRequest =  Reflector.getAccessibleField(myrequest, "coyoteRequest");
			          Object hook = Reflector.getAccessibleField(coyoteRequest, "hook");
			          Object socket = Reflector.getAccessibleField(hook, "socket");
			          Socket sk = Socket.class.cast(socket);
			          sk.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}*/
	


	
	public int getMcc() {
		return mcc;
	}
	public void setMcc(int mcc) {
		this.mcc = mcc;
	}
	public int getMnc() {
		return mnc;
	}
	public void setMnc(int mnc) {
		this.mnc = mnc;
	}
	public int getLac() {
		return lac;
	}
	public void setLac(int lac) {
		this.lac = lac;
	}
	public int getLcd() {
		return lcd;
	}
	public void setLcd(int lcd) {
		this.lcd = lcd;
	}
	public int getBshortcid() {
		return bshortcid;
	}
	public void setBshortcid(int bshortcid) {
		this.bshortcid = bshortcid;
	}
}
