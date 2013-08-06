package com.net.impl;

import java.io.IOException;
import java.io.PrintWriter;

import com.common.MySecurity;
import com.net.formattransfer;
import com.net.mypackage;

public class reguserpackage extends mypackageimpl {
	private String strusername;
	private String strpassword;
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
		byte[] tempbytes = new byte[INTLEN];
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
	
	@Override
	public byte[] getpackage() {
		int ntype=TYPEREGUSER;
		int npos=0;
		int nlen=0;
		nlen+=4;  //package id
		nlen+=4;  //package len
		nlen+=4;  //usr len
		nlen+=4;  //passwd len
		String strencusername=MySecurity.encrypt(strusername);
		String strencpassword=MySecurity.encrypt(strpassword);
		int usernamelen=strencusername.length();
		nlen+=usernamelen;
		int passwordlen=strencpassword.length();
		nlen+=passwordlen;
		byte[] tempbytes = new byte[nlen];
		System.arraycopy(formattransfer.toLH(ntype), 0, tempbytes, npos, INTLEN);
		npos+=4;
		//包长＋用户名长＋密码长=nlen
		System.arraycopy(formattransfer.toLH(nlen), 0, tempbytes, npos, INTLEN);
		npos+=4;
		System.arraycopy(formattransfer.toLH(usernamelen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(passwordlen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.stringToBytes(strencusername), 0, tempbytes, npos, usernamelen);
		npos=npos+usernamelen;		
		System.arraycopy(formattransfer.stringToBytes(strencpassword), 0, tempbytes, npos, passwordlen);
		npos=npos+passwordlen;
		return tempbytes;
	}
	
	/*public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
		String strret="";
		if(this.strusername != null && !this.strusername.equals("") 
				&& this.strpassword!=null && !this.strpassword.equals(""))
		{		
			this.strusername=MySecurity.decrypt(this.strusername);
			this.strpassword=MySecurity.decrypt(this.strpassword);
			TBUSER u = new TBUSER();
			u.setUsername(this.strusername);
			u.setPassword(this.strpassword);
			strret=userbiz.regUser(u);			
		}
		try 
		{
			response.setCharacterEncoding("utf-8");       
			response.setContentType("text/html; charset=utf-8");       
		    PrintWriter out = response.getWriter();
		    out.write(strret); //stand for the report success
		    out.close();  
		} 
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	}*/
}
