package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.net.formattransfer;
import com.net.mypackage;

public class getgpsbycellpackage extends mypackageimpl{
	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
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
		int nbeginpos=PACKAGEHEADERLEN;
		if(pd.length<4*4+nbeginpos)
	    {
		  return null;
		}
		byte[] tempbytes = new byte[INTLEN];
		System.arraycopy(pd, nbeginpos, tempbytes, 0, INTLEN);
		this.mcc=formattransfer.lBytesToInt(tempbytes); 
		System.arraycopy(pd, nbeginpos+4, tempbytes, 0, INTLEN);
		this.mnc=formattransfer.lBytesToInt(tempbytes); 
		System.arraycopy(pd, nbeginpos+8, tempbytes, 0, INTLEN);
		this.lac=formattransfer.lBytesToInt(tempbytes); 
		System.arraycopy(pd, nbeginpos+12, tempbytes, 0, INTLEN);
		this.lcd=formattransfer.lBytesToInt(tempbytes); 
		return this;
	}

	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
		
		if(this.lac>=0 || this.lcd>=0)
		{
			try 
			{
			     byte [] pd=userbiz.getgpsbycell(mcc,mnc,lac,lcd);
			     if(pd!=null && pd.length>0)
			     {
				    response.setCharacterEncoding("utf-8");       
				    response.setContentType("application/binary"); 
				    response.setContentLength(pd.length);
				    OutputStream out = response.getOutputStream();
				    out.write(pd, 0, pd.length);
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
