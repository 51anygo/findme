package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.common.DateUtils;
import com.common.MySecurity;
import com.entity.TBCELL2GPS;
import com.entity.TBGPSDATA;
import com.entity.TBUSER;
import com.hook.Reflector;
import com.net.formattransfer;
import com.net.mypackage;

public class updategpsbycellpackage extends mypackageimpl {
	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
	private	int bshortcid;
	private	int needreply;
	private String strusername;
	private String strpassword;
	
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
	
	public mypackage parse(byte []pd)
	{ 		   
		int npos=PACKAGEHEADERLEN;
		if(pd.length<4*4+npos)
	    {
		  return null;
		}
		int usernamelen=0;
		int passwordlen=0;
		byte[] tempbytes = new byte[INTLEN];
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mcc=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mnc=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lac=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lcd=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.bshortcid=formattransfer.lBytesToInt(tempbytes);
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.needreply=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		usernamelen=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		passwordlen=formattransfer.lBytesToInt(tempbytes);
		npos+=INTLEN;
		tempbytes = new byte[usernamelen];
		System.arraycopy(pd, npos, tempbytes, 0, usernamelen);
		this.strusername=formattransfer.bytesToString(tempbytes);
		npos=npos+usernamelen;
		tempbytes = new byte[passwordlen];
		System.arraycopy(pd, npos, tempbytes, 0, passwordlen);
		this.strpassword=formattransfer.bytesToString(tempbytes);
		return this;
	}

	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
		String strreply;
		if(this.strusername != null && !this.strusername.equals("") 
				&& this.strpassword!=null && !this.strpassword.equals(""))
		{		
			this.strusername=MySecurity.decrypt(this.strusername);
			this.strpassword=MySecurity.decrypt(this.strpassword);
			TBUSER u = new TBUSER();
			u.setUsername(this.strusername);
			u.setPassword(this.strpassword);
			u = userbiz.login(u);
			if(u != null)
			{
				TBGPSDATA gpsdata=userbiz.getgpsbycellfromcache(this.mcc, this.mnc, this.lac,
						                                        this.lcd, this.bshortcid);
				if(gpsdata==null)
				{					
					return;
				}
				String gpstime;
				Date nowdate=new Date();
				gpstime=DateUtils.dateToStr(nowdate);;
			    gpsdata.setLat(gpsdata.getLat());
			    gpsdata.setLng(gpsdata.getLng());
			    gpsdata.setAlt(Double.valueOf(0));
			    gpsdata.setAngle("0");
			    gpsdata.setSpeed(Double.valueOf(88.));
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

	public int getBshortcid() {
		return bshortcid;
	}

	public void setBshortcid(int bshortcid) {
		this.bshortcid = bshortcid;
	}
}
