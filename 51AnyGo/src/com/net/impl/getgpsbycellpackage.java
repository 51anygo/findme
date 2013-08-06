package com.net.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.Toast;

import com.anygo.ShowTrack;
import com.google.android.maps.GeoPoint;
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
	
	public GeoPoint getInput(String httpurl,int mnc,int mcc,int lac,int lcd){ 
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
		        //byte[] buffer = new byte[1024];  
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
     			//byte[] prepbuf=recv_len;
     			if(buffer.length>0 && recv_len>2)
     			{
     				//AfxMessageBox(strHeader);
     				//prepbuf+=strlen("\r\n\r\n");//去掉两个回车
     				byte[] mytempbytes = new byte[4];
     				double lat=formattransfer.hBytesToInt(buffer)/1000000.;
     				System.arraycopy(buffer, 4, mytempbytes, 0, 4);
     				double lng=formattransfer.hBytesToInt(mytempbytes)/1000000.;
     				return new GeoPoint((int) (lat* 1000000),
     		                (int) ( lng * 1000000));
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
        return retpoint;
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
		int ntype=TYPEGETGPSBYCELLFROMLOCAL;
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
