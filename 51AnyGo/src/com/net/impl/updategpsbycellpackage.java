package com.net.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

import android.widget.Toast;

import com.anygo.Setting;
import com.common.MySecurity;
import com.google.android.maps.GeoPoint;
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
		tempbytes = new byte[usernamelen];
		System.arraycopy(pd, npos, tempbytes, 0, usernamelen);
		this.strusername=formattransfer.bytesToString(tempbytes);
		npos=npos+usernamelen;
		tempbytes = new byte[passwordlen];
		System.arraycopy(pd, npos, tempbytes, 0, passwordlen);
		this.strpassword=formattransfer.bytesToString(tempbytes);
		return this;
	}
	
	public String getInput(String httpurl,int mnc,int mcc,int lac,int lcd){ 
		String nowret="";
    	GeoPoint retpoint=new GeoPoint((int) (22.6948331* 1000000),
                (int) ( 113.8068808 * 1000000));
        try
        {
            setMnc(mnc);
        	setMcc(mcc);
        	setLac(lac);
        	setLcd(lcd);
	        byte[] tempbytes=getpackage();	    
	        URL url = new URL(httpurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setUseCaches(false);   
	        conn.setConnectTimeout(10000);
	        conn.setRequestMethod("POST");
	        OutputStream outStrm = conn.getOutputStream(); 
	        DataOutputStream objOutput = new DataOutputStream(outStrm);
	        //objOutput.writeObject(new String("this is a string..."));
            objOutput.write(tempbytes);
	        objOutput.flush();
	        objOutput.close();	      
	        //conn.connect();

	        int resCode = conn.getResponseCode();
	        if(resCode == HttpURLConnection.HTTP_OK)
            {  
	           // 取得Response内容 
	        	 InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "utf-8");
	        	 int i;  
               // read  
               while ((i = isr.read()) != -1) {  
            	   nowret = nowret + (char) i;  
               }  
               isr.close();	
               return nowret;
     		}
	        //Toast.makeText(Setting.this, R.string.setting_logintesetok, Toast.LENGTH_SHORT).show();		        
	        conn.disconnect();
        }
        catch(Exception ee)
        {
        	return null;
        }
        return nowret;
    }
	
	@Override
	public byte[] getpackage() {
		String tmpusername=MySecurity.encrypt(this.strusername);
		String tmppassword=MySecurity.encrypt(this.strpassword);
		int ntype=TYPEUPDATEGPSBYCELL;
		int npos=0;
		int nlen=0;
		int usernamelen=tmpusername.length();
		int passwordlen=tmppassword.length();
    	//int addrlen=strusername.length();
    	//tmpaddrbytes=formattransfer.stringToBytes(addr);
    	//tmpaddrbytes=addr.getBytes("UTF-8");
    	//int addrbyteslen=tmpaddrbytes.length;
		nlen+=4;  //package id
		nlen+=4;  //package len
		nlen+=4;  //mcc len
		nlen+=4;  //mnc len
		nlen+=4;  //lac len
		nlen+=4;  //lcd len
		nlen+=4;  //bshortcid len
		nlen+=4;  //needreply len
		nlen+=4;  //usernamelen
		nlen+=4;  //passwordlen
		nlen+=usernamelen;  //usernamelen
		nlen+=passwordlen;  //passwordlen
		byte[] tempbytes = new byte[nlen];
		System.arraycopy(formattransfer.toLH(ntype), 0, tempbytes, npos, INTLEN);
		npos+=4;
		//包长＋用户名长＋密码长=nlen
		System.arraycopy(formattransfer.toLH(nlen), 0, tempbytes, npos, INTLEN);
		npos+=4;
		System.arraycopy(formattransfer.toLH(mcc), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(mnc), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(lac), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(lcd), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(bshortcid), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(needreply), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(usernamelen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(passwordlen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.stringToBytes(tmpusername), 0, tempbytes, npos, usernamelen);		
		npos=npos+usernamelen;
		System.arraycopy(formattransfer.stringToBytes(tmppassword), 0, tempbytes, npos, passwordlen);		
		return tempbytes;
	}
/*	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
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
	 }*/

	public int getBshortcid() {
		return bshortcid;
	}

	public void setBshortcid(int bshortcid) {
		this.bshortcid = bshortcid;
	}

}
