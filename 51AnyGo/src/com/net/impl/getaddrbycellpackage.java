package com.net.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.maps.GeoPoint;
import com.net.formattransfer;
import com.net.mypackage;

public class getaddrbycellpackage extends mypackageimpl{
	private int mcc;
	private	int mnc; 
	private	int lac;
	private	int lcd;
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
	           String strresult;
	           int recv_len = conn.getContentLength();//.getContentLength()此连接
	           int readBytes=0 ;
	           byte[] buffer = new byte[recv_len];
	           InputStream inputStream = conn.getInputStream();           
	           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	           int read = 0;
	           System.out.println("length=" + recv_len);
	           long startTime2=System.currentTimeMillis();
	           while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) 
	           {
	                 byteArrayOutputStream.write(buffer, 0, read);                
	                 readBytes += read;
	           }
     			if(buffer.length>0 && recv_len>2)
     			{
     				byte[] mytempbytes = new byte[4];
     				double lat=formattransfer.hBytesToInt(buffer)/1000000.;
     				System.arraycopy(buffer, 4, mytempbytes, 0, 4);
     				double lng=formattransfer.hBytesToInt(mytempbytes)/1000000.;
       				System.arraycopy(buffer, 8, mytempbytes, 0, 4);
     				int addrlen=formattransfer.hBytesToInt(mytempbytes);
     				byte[] mytempaddrbytes = new byte[addrlen];
       				System.arraycopy(buffer, 12, mytempaddrbytes, 0, addrlen);
     				//nowret=formattransfer.bytesToString(mytempaddrbytes);
       				String utfrc=new String(mytempaddrbytes,"utf-8");     				
     				int baddrinex=utfrc.lastIndexOf(',');
     				utfrc=utfrc.substring(baddrinex+1,utfrc.length());
     				utfrc=utfrc.replace("\"", "");
     				nowret = utfrc;
     				return nowret;
     			}
     			else 
     			{
     				return null;
     			}
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
	@Override
	public byte[] getpackage() {
		int ntype=TYPEGETADDRBYCELL;
		int npos=0;
		int nlen=0;
		nlen+=4;  //package id
		nlen+=4;  //package len
		nlen+=4;  //mcc len
		nlen+=4;  //mnc len
		nlen+=4;  //lac len
		nlen+=4;  //lcd len
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
		return tempbytes;
	}

/*	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz){
		
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
	 }*/
}
