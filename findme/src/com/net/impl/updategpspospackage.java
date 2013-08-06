package com.net.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.common.DateUtils;
import com.common.MySecurity;
import com.entity.TBGPSDATA;
import com.entity.TBUSER;
import com.hook.Reflector;
import com.net.formattransfer;
import com.net.mypackage;

public class updategpspospackage extends mypackageimpl {
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
		System.arraycopy(pd, npos, tempbytes, 0, LONGLEN);
		this.nowtime=formattransfer.lBytesToLong(tempbytes); 
		npos+=LONGLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.needreply=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		usernamelen=formattransfer.lBytesToInt(tempbytes); 
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		passwordlen=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		tempbytes = new byte[usernamelen];
		System.arraycopy(pd, npos, tempbytes, 0, usernamelen);
		this.strusername=formattransfer.bytesToString(tempbytes);
		npos=npos+usernamelen;
		tempbytes = new byte[passwordlen];
		System.arraycopy(pd, npos, tempbytes, 0, passwordlen);
		this.strpassword=formattransfer.bytesToString(tempbytes);
		return this;
	}
	
	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {
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
				String gpstime;
				String strangle=String.valueOf(this.angle);
				Date nowdate=new Date();
				nowdate.setTime(this.nowtime*1000);
				gpstime=DateUtils.dateToStr(nowdate);;
				TBGPSDATA gpsdata = new TBGPSDATA();
			    gpsdata.setLat(this.lat);
			    gpsdata.setLng(this.lng);
			    Double dalt=Double.valueOf(0);
			    gpsdata.setAlt(dalt);
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
	}
}

