package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.entity.TBGPSDATA;
import com.net.formattransfer;
import com.net.mypackage;

public class getaddrbycellpackage extends mypackageimpl{
	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
	private	int bshortcid;	
	private String addr;
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
		//System.arraycopy(pd, nbeginpos+16, tempbytes, 0, INTLEN);
		this.bshortcid=0;//formattransfer.lBytesToInt(tempbytes);		
		return this;
	}

	@Override
	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
		
		if(this.mcc>=0 || this.mnc>=0)
		{
				try 
				{
					TBGPSDATA gpsdata=userbiz.getgpsbycellfromcache(this.mcc,this.mnc,this.lac,this.lcd,this.bshortcid);
				     if(gpsdata!=null)
				     {
				    	int nbytelen=0;
				    	byte[] retbytes=null;
				    	byte[] tmpbytes=null;
				    	byte[] tmpaddrbytes=null;
				    	int lat=(int)(gpsdata.getLat()*1000000.);
				    	int lng=(int)(gpsdata.getLng()*1000000.);
				    	String addr=gpsdata.getMemo();
				    	int addrlen=addr.length();
				    	//tmpaddrbytes=formattransfer.stringToBytes(addr);
				    	tmpaddrbytes=addr.getBytes("UTF-8");
				    	int addrbyteslen=tmpaddrbytes.length;
				    	retbytes=new byte[8+4+addrbyteslen];
				        tmpbytes=formattransfer.toHH(lat);
						System.arraycopy(tmpbytes, 0, retbytes, nbytelen, INTLEN);
						nbytelen+=INTLEN;
				        tmpbytes=formattransfer.toHH(lng);
						System.arraycopy(tmpbytes, 0, retbytes, nbytelen, INTLEN);
						nbytelen+=INTLEN;
				        tmpbytes=formattransfer.toHH(addrbyteslen);
						System.arraycopy(tmpbytes, 0, retbytes, nbytelen, INTLEN);
						nbytelen+=INTLEN;
						System.arraycopy(tmpaddrbytes, 0, retbytes, nbytelen, addrbyteslen);
						nbytelen+=addrbyteslen;
						//byte[] mytempaddrbytes = new byte[addrbyteslen];
	       				//System.arraycopy(tmpaddrbytes, 0, mytempaddrbytes, 0, addrbyteslen);
	     				//String nowret=formattransfer.bytesToString(mytempaddrbytes);
	       				//String nowret=new String(mytempaddrbytes,"utf-8");
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
