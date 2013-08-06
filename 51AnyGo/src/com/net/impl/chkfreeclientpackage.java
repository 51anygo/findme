package com.net.impl;

import java.io.IOException;
import java.io.OutputStream;
import com.net.formattransfer;
import com.net.mypackage;

public class chkfreeclientpackage  extends mypackageimpl {
	private String strclientversion;
	private int    nclienttype;

	public String getStrclientversion() {
		return strclientversion;
	}

	public void setStrclientversion(String strclientversion) {
		this.strclientversion = strclientversion;
	}

	public int getNclienttype() {
		return nclienttype;
	}

	public void setNclienttype(int nclienttype) {
		this.nclienttype = nclienttype;
	}
	
	public mypackage parse(byte []pd)
	{ 		   
		int npos=PACKAGEHEADERLEN;
		if(pd.length<1*INTLEN+npos)
	    {
		  return null;
		}
		int clientversionlen=0;
		byte[] tempbytes = new byte[INTLEN];
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.nclienttype=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		tempbytes = new byte[INTLEN];
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		clientversionlen=formattransfer.lBytesToInt(tempbytes); 
		npos+=INTLEN;
		tempbytes = new byte[clientversionlen];
		System.arraycopy(pd, npos, tempbytes, 0, clientversionlen);
		this.strclientversion=formattransfer.bytesToString(tempbytes); 
		return this;
	}

	@Override
	public byte[] getpackage() {
		// TODO Auto-generated method stub
		return null;
	}


	/*public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {
		try
		{
			byte[] clientbytes=userbiz.getclientlastver(this.strclientversion);
			if(clientbytes!=null && clientbytes.length>0)
			{
				response.setCharacterEncoding("utf-8");       
			    response.setContentType("application/binary"); 
			    response.setContentLength(clientbytes.length);
			    OutputStream out = response.getOutputStream();
			    out.write(clientbytes, 0, clientbytes.length);
			    out.flush();
			    out.close();
			}	
		}
		catch (IOException e)
		{
		   e.printStackTrace();
		}	
	}*/
}
