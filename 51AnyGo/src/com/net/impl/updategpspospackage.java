package com.net.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

import com.common.MySecurity;
import com.google.android.maps.GeoPoint;
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
	
	public String getInput(String httpurl){ 
		String nowret="";
    	GeoPoint retpoint=new GeoPoint((int) (22.6948331* 1000000),
                (int) ( 113.8068808 * 1000000));
        try
        {
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
	@Override
	public byte[] getpackage() {
		String tmpusername=MySecurity.encrypt(this.strusername);
		String tmppassword=MySecurity.encrypt(this.strpassword);
		int ntype=TYPEUPDATEGPSPOS;
		int npos=0;
		int nlen=0;
		int usernamelen=tmpusername.length();
		int passwordlen=tmppassword.length();
    	//int addrlen=strusername.length();
    	//tmpaddrbytes=formattransfer.stringToBytes(addr);
    	//tmpaddrbytes=addr.getBytes("UTF-8");
    	//int addrbyteslen=tmpaddrbytes.length;
		nlen+=INTLEN;  //package id
		nlen+=INTLEN;  //package len
		nlen+=DOUBLELEN;  //lat len
		nlen+=DOUBLELEN;  //lng len
		nlen+=DOUBLELEN;  //speed len
		nlen+=DOUBLELEN;  //angle len
		nlen+=INTLEN;  //nowtime len
		nlen+=INTLEN;  //needreply len
		nlen+=INTLEN;  //usernamelen
		nlen+=INTLEN;  //passwordlen
		nlen+=usernamelen;  //usernamelen
		nlen+=passwordlen;  //passwordlen
		byte[] tempbytes = new byte[nlen];
		System.arraycopy(formattransfer.toLH(ntype), 0, tempbytes, npos, INTLEN);
		npos+=4;
		//包长＋用户名长＋密码长=nlen
		System.arraycopy(formattransfer.toLH(nlen), 0, tempbytes, npos, INTLEN);
		npos+=4;
		System.arraycopy(formattransfer.DoubleTolBytes(this.lat), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.lng), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.speed), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.angle), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		this.nowtime=0;
		System.arraycopy(formattransfer.toLH(this.nowtime), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(this.needreply), 0, tempbytes, npos, INTLEN);		
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
	
	/*public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {
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
	}*/
}

