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
               String utfrc=nowret;
        	   int baddrinex=utfrc.lastIndexOf(',');
			   utfrc=utfrc.substring(baddrinex+1,utfrc.length());
			   utfrc=utfrc.replace("\"", "");
			   nowret = utfrc;
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
		int ntype=TYPEGETADDRBYGPS;
		int npos=0;
		int nlen=0;
    	//int addrlen=strusername.length();
    	//tmpaddrbytes=formattransfer.stringToBytes(addr);
    	//tmpaddrbytes=addr.getBytes("UTF-8");
    	//int addrbyteslen=tmpaddrbytes.length;
		nlen+=INTLEN;  //package id
		nlen+=INTLEN;  //package len
		nlen+=DOUBLELEN;  //lat len
		nlen+=DOUBLELEN;  //lng len
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
		return tempbytes;
	}
	
	
	/*public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz) {

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
	}*/
}
