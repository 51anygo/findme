package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.net.formattransfer;
import com.net.mypackage;

public class getaddrbygpspackage extends mypackageimpl {
	private double lat;
	private	double lng;
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
	public mypackage parse(byte []pd)
	{ 		   
		int npos=PACKAGEHEADERLEN;
		if(pd.length<2*DOUBLELEN+npos)
	    {
		  return null;
		}
		byte[] tempbytes = new byte[DOUBLELEN];
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.lat=formattransfer.lBytesToDouble(tempbytes); 
		npos+=DOUBLELEN;
		System.arraycopy(pd, npos, tempbytes, 0, DOUBLELEN);
		this.lng=formattransfer.lBytesToDouble(tempbytes); 
		return this;
	}
	
	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {

		if(this.lat>=0 || this.lng>=0)
		{
		    try 
		    {
				 String straddr=userbiz.getAddressbygoogle(this.lat, this.lng);
				 if(straddr!=null && straddr.length()>0)
				 {
			    	 response.setCharacterEncoding("utf-8");       
			    	 response.setContentType("text/html; charset=utf-8");       
			         PrintWriter out = response.getWriter();
			         out.write(straddr); //stand for the report success
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
