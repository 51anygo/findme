package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.entity.TBGPSDATA;
import com.net.formattransfer;
import com.net.mypackage;

public class getgpsbycellfromlocalpackage extends mypackageimpl {
	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
	private	int bshortcid;


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
	
	@Override
	public mypackage parse(byte[] pd) {
		int npos=PACKAGEHEADERLEN;
		if(pd.length<4*4+npos)
	    {
		  return null;
		}
		byte[] tempbytes = new byte[DOUBLELEN];
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
		return this; 
	}

	@Override
	public void response(HttpServletRequest request,
			HttpServletResponse response, UserBiz userbiz) {
		if(this.lac>=0 || this.lcd>=0)
		{
			try 
			{
				TBGPSDATA gpsdata=userbiz.getgpsbycellfromcache(this.mcc,this.mnc,this.lac,this.lcd,this.bshortcid);
			     if(gpsdata!=null)
			     {
			    	int nbytelen=0;
			    	byte[] retbytes=new byte[8];
			    	byte[] tmpbytes=null;
			    	int lat=(int)(gpsdata.getLat()*1000000.);
			    	int lng=(int)(gpsdata.getLng()*1000000.);
			        tmpbytes=formattransfer.toHH(lat);
					System.arraycopy(tmpbytes, 0, retbytes, nbytelen, INTLEN);
					nbytelen+=INTLEN;
			        tmpbytes=formattransfer.toHH(lng);
					System.arraycopy(tmpbytes, 0, retbytes, nbytelen, INTLEN);
					nbytelen+=INTLEN;
				    response.setCharacterEncoding("utf-8");       
				    response.setContentType("application/binary"); 
				    response.setContentLength(retbytes.length);
				    OutputStream out = response.getOutputStream();
				    out.write(retbytes, 0, retbytes.length);
				    out.flush();
				    out.close();
			     }
			} 
			catch (IOException e)
			{
			   e.printStackTrace();
			}		
		}
	}

}
