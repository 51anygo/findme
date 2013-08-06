package com.net.impl;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.common.MySecurity;
import com.google.android.maps.GeoPoint;
import com.net.formattransfer;
import com.net.mypackage;

public class updateiamcallingpackage extends mypackageimpl {

	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
	private	int bshortcid;
	
	private	double  lat; 
	private	double  lng;  
	private	double  speed;
	private	double  angle;
	private	long    nowtime;
	private	int     needreply;
	private String strusername;
	private String strpassword;
	private String strmycalnum;
	private String strdestcalnum;
	public String getStrmycalnum() {
		return strmycalnum;
	}
	public void setStrmycalnum(String strmycalnum) {
		this.strmycalnum = strmycalnum;
	}

	
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
		int destcalnumlen=0;
		byte[] tempbytes = new byte[DOUBLELEN];
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mcc=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.mnc=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lac=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		this.lcd=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
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
		System.arraycopy(pd, npos, tempbytes, 0, INTLEN);
		destcalnumlen=formattransfer.lBytesToInt(tempbytes);
		npos=npos+INTLEN;
		System.arraycopy(pd, npos, tempbytes, 0, LONGLEN);
		this.nowtime=formattransfer.lBytesToLong(tempbytes); 
		npos+=LONGLEN;
		//reserved
		npos+=LONGLEN;
		tempbytes = new byte[DOUBLELEN];
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
		tempbytes = new byte[usernamelen];
		System.arraycopy(pd, npos, tempbytes, 0, usernamelen);
		this.strusername=formattransfer.bytesToString(tempbytes);
		npos=npos+usernamelen; 
		tempbytes = new byte[passwordlen];
		System.arraycopy(pd, npos, tempbytes, 0, passwordlen);
		this.strpassword=formattransfer.bytesToString(tempbytes);
		npos=npos+passwordlen; 
		tempbytes = new byte[destcalnumlen];
		System.arraycopy(pd, npos, tempbytes, 0, destcalnumlen);
		this.strdestcalnum=formattransfer.bytesToString(tempbytes);
		npos=npos+destcalnumlen; 
		return this;
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
	public int getBshortcid() {
		return bshortcid;
	}
	public void setBshortcid(int bshortcid) {
		this.bshortcid = bshortcid;
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
		int ntype=TYPEUPDATEIAMCALLING;
		int npos=0;
		int nlen=0;
		int usernamelen=tmpusername.length();
		int passwordlen=tmppassword.length();
		int mycalnumlen=this.strmycalnum.length();
		int destcalnumlen=this.strdestcalnum.length();
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
		nlen+=4;  //mycalnumlen
		nlen+=4;  //destcalnumlen
		nlen+=4;  //nowtime
		nlen+=DOUBLELEN;  //needreply len
		nlen+=DOUBLELEN;  //usernamelen
		nlen+=DOUBLELEN;  //passwordlen
		nlen+=DOUBLELEN;  //destcalnumlen
		nlen+=usernamelen;  //usernamelen
		nlen+=passwordlen;  //passwordlen
		nlen+=mycalnumlen;
		nlen+=destcalnumlen;
		byte[] tempbytes = new byte[nlen];
		System.arraycopy(formattransfer.toLH(ntype), 0, tempbytes, npos, INTLEN);
		npos=npos+INTLEN;
		//包长＋用户名长＋密码长=nlen
		System.arraycopy(formattransfer.toLH(nlen), 0, tempbytes, npos, INTLEN);
		npos=npos+INTLEN;
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
		System.arraycopy(formattransfer.toLH(mycalnumlen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(destcalnumlen), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.toLH(nowtime), 0, tempbytes, npos, INTLEN);		
		npos=npos+INTLEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.lat), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.lng), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;	
		System.arraycopy(formattransfer.DoubleTolBytes(this.speed), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;
		System.arraycopy(formattransfer.DoubleTolBytes(this.angle), 0, tempbytes, npos, DOUBLELEN);		
		npos=npos+DOUBLELEN;	
		System.arraycopy(formattransfer.stringToBytes(tmpusername), 0, tempbytes, npos, usernamelen);		
		npos=npos+usernamelen;
		System.arraycopy(formattransfer.stringToBytes(tmppassword), 0, tempbytes, npos, passwordlen);	
		npos=npos+passwordlen;
		System.arraycopy(formattransfer.stringToBytes(strmycalnum), 0, tempbytes, npos, mycalnumlen);	
		npos=npos+mycalnumlen;
		System.arraycopy(formattransfer.stringToBytes(strdestcalnum), 0, tempbytes, npos, destcalnumlen);	
		return tempbytes;
	}
	public String getStrdestcalnum() {
		return strdestcalnum;
	}
	public void setStrdestcalnum(String strdestcalnum) {
		this.strdestcalnum = strdestcalnum;
	}

}
