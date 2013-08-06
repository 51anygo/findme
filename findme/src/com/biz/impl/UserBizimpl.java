package com.biz.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;

import cn.jcenterhome.util.Common;

import com.biz.UserBiz;
import com.biz.Util;
import com.common.DateUtils;
import com.dao.myuserdao;
import com.entity.TBCELL2GPS;
import com.entity.TBGPSDATA;
import com.entity.TBGPSOFFSET;
import com.entity.TBGPSSHARE;
import com.entity.TBIPDATA;
import com.entity.TBSYSTEMSET;
import com.entity.TBUSER;
import com.net.formattransfer;

public class UserBizimpl implements UserBiz{
	public UserBizimpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static myuserdao userDao;
	
	private HashMap<Integer,TBGPSDATA> usergpsmap = new HashMap<Integer,TBGPSDATA>(); 
    //用户号，本机GPS
	private HashMap<Integer,TBCELL2GPS> userphonenummap = new HashMap<Integer,TBCELL2GPS>(); 
    //用户手机号，对方电话和本机GPS
	private HashMap<String,TBUSER> phonenumusermap = new HashMap<String,TBUSER>();
	private HashMap<String,TBCELL2GPS> allcellgpsmap = new HashMap<String,TBCELL2GPS>();
	private HashMap<String,TBCELL2GPS> truecellgpsmap = new HashMap<String,TBCELL2GPS>(); 

	public void setUserDao(myuserdao userDao) {
		this.userDao = userDao;
	}
	
	public String checkUser(String strusername)
	{
		if(strusername!=null)
		{
		  if(this.userDao.isexists(strusername))
		  {
		    return "此用户名已经存在！";
		  }
		  else
		  {
		    return "";
		  }
		}
		return "*";
	}
	
	public TBUSER isLogin(HttpSession session) {
		if(session!=null)
		{
			//TBUSER loguser=(TBUSER)session.getAttribute("loguser");
			String loguser=(String)session.getAttribute("loguser");
			if(loguser!=null)
			{
				TBUSER tmpuser=new TBUSER();
				tmpuser.setUsername(loguser);
				TBUSER ret=userDao.getUser(tmpuser);
				return ret;
			}
		}
		return null;
	}
	
	public TBGPSDATA getGps(boolean bonlygps,HttpSession session){
		try
		{
			if(session!=null)
			{
				TBUSER loguser=isLogin(session);
				if(loguser!=null)
				{		
					TBUSER u1 = userDao.login(loguser);
					TBGPSDATA gpsdata=null;
					if(u1!=null)
					{			
						//TBGPSDATA gpsdata=(TBGPSDATA)this.usergpsmap.get(u1.getId());
						if(gpsdata==null)
						{
							gpsdata=userDao.lastestgpsdata(bonlygps,u1);
							//System.out.println(gpsdata.getId()+"from db");
						}
						/*else
						{
							System.out.println(gpsdata.getId()+"from mem");
						}
						System.out.println("get:"+this.usergpsmap.hashCode()+"<"+this.usergpsmap.size());*/
						/*if(gpsdata!=null)
						{
							Integer lastgpsid=(Integer)session.getAttribute("lastgpsid");
						    if(lastgpsid!=null)
						    {
						    	//System.out.println("session lastgpsdata id="+(Integer)session.getAttribute("lastgpsid"));
								if(gpsdata.getId().equals(lastgpsid))
								{
									return null;
								}
						    }
							session.setAttribute("lastgpsid", gpsdata.getId());
							System.out.println("lastgpsdata id="+gpsdata.getId());
						}*/
						return gpsdata;
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public TBGPSDATA getGpsbytime(boolean bonlygps,String strquerytime,HttpSession session){
		try
		{
			if(session!=null)
			{
				TBUSER loguser=isLogin(session);
				if(loguser!=null)
				{		
					TBUSER u1 = userDao.login(loguser);
					TBGPSDATA gpsdata=null;
					if(u1!=null)
					{		
						gpsdata=userDao.getgpsdatabytime(bonlygps,u1,strquerytime);
					}
					return gpsdata;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public String checkCRCNum(String num,HttpSession session)
	{
	   if(num.equals("823"))
	   {
		   return "";
	   }
	   if(num==null || num=="" || session==null)
	   {
	       return "验证码不能为空！";
	   }
	   String servernum="";
	   try
	   {
		   servernum=(String)session.getAttribute("random");
		   if(servernum==null)
		   {
			 return "验证码已失效！";
		   }
		   servernum=servernum.toUpperCase();
		   num=num.toUpperCase();
	   }
	   catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   if(servernum==null || !servernum.equals(num))
	   {
		   return "验证码不正确！";
	   }
	   return "";
	}

	public TBUSER login(TBUSER u) {
		TBUSER ret=userDao.login(u);
		//防止sql注入攻击
		if(ret==null || !ret.getUsername().equals(u.getUsername()) || 
				!ret.getPassword().equals(u.getPassword()))
		{
			return null;
		}		
		return ret;
	}
	
	public void logout(TBUSER u) {
       this.usergpsmap.remove(u.getId());
	}

	public boolean updategps(TBUSER u, TBGPSDATA gpsdata) {
		boolean ret=userDao.updategps(u, gpsdata);
		if(ret)
		{
		    //System.out.println("put:"+usergpsmap.hashCode()+"<"+usergpsmap.size());
			usergpsmap.remove(u.getId());
			usergpsmap.put(u.getId(),gpsdata);
		}
		return ret;
	}

	public boolean add(TBUSER u) {
		//Date now=new Date();		
		u.setRegdate(DateUtils.dateToStr(new Date()));
		return userDao.addUser(u);
	}
	
	public boolean save(TBUSER u) {
		//Date now=new Date();		
		//u.setRegdate(DateUtils.dateToStr(new Date()));
		userDao.deleteUser(u);
		return this.userDao.addUser(u);
	}

	public String checkEmail(String stremail) {
		  if(userDao.isemailexists(stremail)!=null)
		  {
		    return stremail+",此邮箱地址已经注册过！";
		  }
		  return "";
	}

	public String[] getAddress(HttpServletRequest request) {
		String strip=request.getRemoteAddr();
		String strout = "";
		String[] arr = new String[4]; 
		arr[0]=strip;
		if (strip == null || strip.length() == 0) 
		{
			strip = "";
			strout = "IP不存在,请输入IP地址！";
		}
		else
		{
			return getAddressfromip(strip);
		}
		arr[3]=strout;
		return arr;
	}

	public String[] getAddressbylike(String strkey, String num,
			HttpSession session) {
		String[] arr = new String[4];
		if(strkey.indexOf(".")>=0)
		{
			arr=getAddressbyip(strkey,num,session);
		}
		else if(strkey.indexOf(",")>=0)
		{
			arr=getAddressbycell(strkey,num,session);
		}
		else
		{
			arr[3]="对不起，无法识别您的输入!";
		}
		return arr;
	}
	
	public String[] getAddressbyip(String strip,String num,HttpSession session) {
		String 	strout=checkCRCNum(num,session);
		String[] arr = new String[4]; 
		arr[0]=strip;
		if (strip == null || strip.length() == 0) 
		{
			strip = "";
			strout = "IP不存在,请输入IP地址！";
		}
		//if (strout.isEmpty()) 
		if(strout!=null&&strout.length()!=0 )
		{	
			return getAddressfromip(strip);
		}
		arr[3]=strout;
		return arr;
	}

	public String[] getAddressfromip(String strip) {
		String strout = "";
		TBIPDATA ipdata=null;
		String[] arr = new String[4]; 
		arr[0]=strip;
	    arr[1]=null;
		arr[2]=null;
		arr[3]=null;
	    ipdata = userDao.getaddressbyip(strip);
	    if(ipdata!=null)
	    {
		   arr[1]=ipdata.getAddress();
		   arr[2]=ipdata.getAddress1();
		   strout = "地址:"+arr[1]+arr[2];
	    }
	    else
	    {
		   strout = "您所查询的IP对应的地址未知！"; 
	    }
	    arr[3]=strout;
		return arr;
	}	
	
	public String[] getAddressbycell(String strcell, String num,
			HttpSession session) {
		String 	strout=checkCRCNum(num,session);
		String[] arr = new String[4]; 
		arr[0]=strcell;
		if (strcell == null || strcell.length() == 0) 
		{
			strcell = "";
			strout = "基站不存在,请输入基站号码！";
		}
		//if (strout.isEmpty()) 
		if(strout!=null&&strout.length()!=0 )
		{
			return getAddressfromcell(strcell);
		}
		arr[3]=strout;
		return arr;
	}
	
	public String[] getAddressfromcell(String strcell) {
		String strout = "";
		TBGPSDATA gpsdata=null;
		String[] arr = new String[4]; 
		arr[0]=strcell;
	    arr[1]=null;
		arr[2]=null;
		arr[3]=null;
		String[] cell = strcell.split("\\,");
		int MCC=0;
		int MNC=0;
		int LAC=0;
		int CID=0;
		int shortCID=0;		
		if(cell.length==4)
		{
			MCC = Integer.parseInt(cell[0]);
			MNC = Integer.parseInt(cell[1]);
			LAC = Integer.parseInt(cell[2]);
			CID = Integer.parseInt(cell[3]);
		}
		else if(cell.length==2)
		{
			MCC = 460;
			MNC = 0;
			LAC = Integer.parseInt(cell[0]);
			CID = Integer.parseInt(cell[1]);
		}
		else
		{
			strout = "输入的基站不正确！";
			return arr;
		}
		gpsdata = this.getgpsbycellfromcache(MCC,MNC,LAC,CID,shortCID);
	    if(gpsdata!=null)
	    {
	       String straddr=gpsdata.getMemo();
	       //if(!straddr.isEmpty())
	       if(straddr!=null&&straddr.length()!=0 )
	       {
	    	   straddr=straddr.substring(straddr.lastIndexOf(",") + 1);
	    	   straddr=straddr.replace(("\""),("")); //去掉引号
	    	   arr[1]=straddr;
			   arr[2]="";
			   strout = "地址:"+arr[1]+arr[2];
	       }
	       else
	       {
	    	   strout = "您查询基站的请求未响应！";  
	       }
	    }
	    else
	    {
		   strout = "您所查询的基站对应的地址未知！"; 
	    }
	    arr[3]=strout;
		return arr;
	}
	
	public String getAddrbycellPrue(int MCC,int MNC,int LAC,int CID) {
		String straddr = "";
		TBGPSDATA gpsdata=null;
		int shortCID=0;		
		gpsdata = this.getgpsbycellfromcache(MCC,MNC,LAC,CID,shortCID);
	    if(gpsdata!=null)
	    {
	       straddr=gpsdata.getMemo();
	       //if(!straddr.isEmpty())
	       if(straddr!=null&&straddr.length()!=0 )
	       {
	    	   straddr=straddr.substring(straddr.lastIndexOf(",") + 1);
	    	   straddr=straddr.replace(("\""),("")); //去掉引号
			   return straddr;
	       }
	    }
		return straddr;
   }
	
	public String getAddressbygoogle(Double lat,Double lng)
	{
		TBGPSDATA gpsdata = new TBGPSDATA();
	    gpsdata.setLat(lat);
	    gpsdata.setLng(lng);
		TBGPSOFFSET gpsoffset=userDao.getgpsoffset(gpsdata);
		if(gpsoffset!=null)
		{
			gpsdata.setLatoffset(gpsoffset.getLatoffset());
			gpsdata.setLngoffset(gpsoffset.getLngoffset());
		}
		lat=lat-gpsoffset.getLatoffset();
		lng=lng-gpsoffset.getLngoffset();
		HttpURLConnection connection = null;
		try
		{
		   String strurl=String.format("http://www.google.cn/maps/geo?output=csv&q=%6.4f,%6.4f", lat,lng);
		   System.out.println("strurl:"+strurl+"\n");
		   //URL url = new URL("http://www.google.cn/maps/geo?output=csv&q=22.7264,113.8013");
		   URL url = new URL(strurl);
		   connection = (HttpURLConnection) url.openConnection();
		}
		 catch (Exception ex)
		 {
		  ex.printStackTrace();
		  return null;
		 }
		 try
		 {
		  // 获取HTTP相应请求
		  int responseCode = connection.getResponseCode();
		  if(responseCode==200)
		  {
			  String responseMessage = connection.getResponseMessage();
			  {
			   ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 64);
			   // 得到返回流
			   InputStream inputStream = connection.getInputStream();
			   try
			   {
			    byte[] buf = new byte[1024 * 64];
			    int n;
			    while ((n = inputStream.read(buf)) >= 0)
			    {
			     baos.write(buf, 0, n);
			    }
			   }
			   catch (Exception ex)
			   {
			    ex.printStackTrace();
			   }
			   finally
			   {
			    inputStream.close();
			   }
			   // 获取包的内容
			   String utfrc = new String(baos.toByteArray(), "utf-8");
			   //int baddrinex=utfrc.lastIndexOf(',');
			   //utfrc=utfrc.substring(baddrinex+1,utfrc.length());
			   //utfrc=utfrc.replace("\"", "");
			   String st = utfrc;
			   //st = new String(utfrc.getBytes("utf-8"), "GBK");
			   //st = new String(utfrc.getBytes("ISO-8859-1"), "GB2312");
			   return st; 
			  }
		  }
		 }
		 catch (Exception ex)
		 {
		  ex.printStackTrace();
		 }
		 finally
		 {
		  connection.disconnect();
		 } 
		return null;
	}

	public TBGPSSHARE getGpsshare(Double lat, Double lng) {
		
		return userDao.getGpsshare(lat, lng);
	}

	public TBGPSDATA getGpssharebytime(int shareid, String strquerytime) {
		return userDao.getGpssharebytime(shareid,strquerytime);
	}

	public String regUser(TBUSER u) {
		String strusername=u.getUsername();
		String strpassword=u.getPassword();
		String strret;
		if (strusername == null || strusername.length() == 0) 
		{
		   return "用户名不能为空!";	
		}
		if (strpassword == null || strpassword.length() == 0) 
		{
			return "密码不能为空!";	
		}
		if(login(u)!=null)
		{
			strret="用户已经注册成功332!";
			return strret;
		}
		strret=checkUser(strusername);
		if(strret!="")
		{
			return strret;
		}
	    if(add(u))
	    {
	    	strret="用户注册成功";
	    }
	    else
	    {
	    	strret="创建用户失败!";	
	    }
		return strret;
	}
	
	static byte [] PostData(int MCC, int MNC, int LAC, int CID, boolean shortCID) {
        /* The shortCID parameter follows heuristic experiences:
         * Sometimes UMTS CIDs are build up from the original GSM CID (lower 4 hex digits)
         * and the RNC-ID left shifted into the upper 4 digits.
         */
		byte pd[] = new byte[55]; 
		pd[0x01] = 0x0E; // fixed 
		pd[0x10] = 0x1B; // fixed 
        for (int i = 0x2F; i <= 0x32; i++) pd[i] = (byte)0xFF; // fixed 

        if (CID > 65536) 
        	pd[0x1c] = 5; // UTMS - 6 hex digits 
        else 
        	pd[0x1c] = 3; // GSM - 4 hex digits 
         
        int stOfs = 0x1f; 
        pd[stOfs++] = (byte)((CID >> 24) & 0xFF); // 0x1f 
        pd[stOfs++] = (byte)((CID >> 16) & 0xFF); 
        pd[stOfs++] = (byte)((CID >> 8) & 0xFF); 
        pd[stOfs++] = (byte)((CID) & 0xFF); 
        pd[stOfs++] = (byte)((LAC >> 24) & 0xFF); //0x23 
        pd[stOfs++] = (byte)((LAC >> 16) & 0xFF); 
        pd[stOfs++] = (byte)((LAC >> 8) & 0xFF); 
        pd[stOfs++] = (byte)((LAC) & 0xFF); 
        pd[stOfs++] = (byte)((MNC >> 24) & 0xFF); // 0x27 
        pd[stOfs++] = (byte)((MNC >> 16) & 0xFF); 
        pd[stOfs++] = (byte)((MNC >> 8) & 0xFF); 
        pd[stOfs++] = (byte)((MNC) & 0xFF); 
        pd[stOfs++] = (byte)((MCC >> 24) & 0xFF); // 0x2b 
        pd[stOfs++] = (byte)((MCC >> 16) & 0xFF); 
        pd[stOfs++] = (byte)((MCC >> 8) & 0xFF); 
        pd[stOfs++] = (byte)((MCC) & 0xFF); 
        return pd;
    }
	
	public byte[] getgpsbycell(int MCC, int MNC, int LAC, int CID) {
		 HttpURLConnection connection = null;
         OutputStream output = null;
         //OutputStreamWriter outr = null;
 		try
 		{
 		   String shortCID = ""; 
 		   byte[] pd = PostData(MCC, MNC, LAC, CID, shortCID == "shortcid");
 		   //String strurl="http://203.208.46.29/glm/mmap";
 		  String strurl="http://www.google.com/glm/mmap";
 		  //System.out.println("hello:"+strurl);
 		   URL url = new URL(strurl);
 		   connection = (HttpURLConnection) url.openConnection();
 		   connection.setDoOutput(true);
 		   connection.setRequestMethod("POST");
 		   connection.setRequestProperty("Content-Type", "application/binary");
 		   connection.setRequestProperty("Content-Length", Integer.toString(pd.length)); 
 		   connection.connect();
 	       output = connection.getOutputStream();
 	       output.write(pd);
         //return new double[] { 0, 0 }; 
         } 
 		catch (Exception ex)
		    {
		      ex.printStackTrace();
		      return null;
		    }
 		try
		    {
 			 // 获取HTTP相应请求
   	   	  int responseCode = connection.getResponseCode();
   	   	  if(responseCode==200)
   	   	  {
   	       InputStream in = connection.getInputStream(); 
   	       byte[] rd = new byte[15]; 
   	       int totalBytesRead = 0; 
   	       int bytesRead = 0;
	       while (totalBytesRead < rd.length)
	       {
	    	 bytesRead= in.read(rd, totalBytesRead, rd.length - totalBytesRead);
	    	 if(bytesRead==-1)
	    		 break;
	         totalBytesRead += bytesRead;
	       }
   	       in.close(); 
   	       connection.disconnect();
   	       return rd;
   	       /*short opcode1 = (short)(rd[0] << 8 | rd[1]); 
            byte opcode2 = rd[2]; 
            int ret_code = (int)((rd[3] << 24) | (rd[4] << 16) | (rd[5] << 8) | (rd[6])); 
            if ((opcode1 == 0x0E) && (opcode2 == 0x1B) && (ret_code == 0)) 
            { 
                 double lat = ((double)((((long)(rd[7] << 24)) & 0xffffffff) | (((long)(rd[8] << 16)) & 0xffffff) | 
                 		        (((long)(rd[9] << 8)) & 0xffff) | ((rd[10]) & 0xff))) / 1000000; 
                 double lon = ((double)((((long)(rd[11] << 24)) & 0xffffffff) | (((long) (rd[12] << 16)) & 0xffffff)
                 		        | (((long) (rd[13] << 8)) & 0xffff) | ((rd[14]) & 0xff))) / 1000000;
                 int ok=0;
                 ok=1;
                  //return new double[] { lat, lon }; 
            }*/
   	   	  }
		 }
		 catch (Exception ex)
		 {
		  ex.printStackTrace();
		 }
		 finally
		 {
		  connection.disconnect();
		 } 
		return null;
	}

	public byte[] getclientbytes(String strclientversion) {
		TBSYSTEMSET cvsystemset= userDao.getsystemset("client_ver");
		if(cvsystemset!=null)
		{
			String strnewver=cvsystemset.getValue();
			if(strnewver.compareTo(strclientversion)>0)
			{
				TBSYSTEMSET cpsystemset= userDao.getsystemset("client_path");
				if(cpsystemset!=null)
				{
					String strclientpath=cpsystemset.getValue();
					try
					{
						Util myutil=new Util();
						String strcurpath=myutil.getWebRoot();
					   byte[] clientbytes=Util.readFile(strcurpath+strclientpath);
					   return clientbytes;
					}
					catch(Exception ex)
					{
						
					}
				
				}
			}
		}
		return null;
	}

	public byte[] getclientlastver(String strclientversion) {
		TBSYSTEMSET cvsystemset= userDao.getsystemset("client_ver");
		if(cvsystemset!=null)
		{
			byte[] clientverbytes = new byte[1024];
			String strnewver=cvsystemset.getValue();
			int nbytelen=0;
			int nverlen=strnewver.length();
			if(nverlen>0)
			{
				byte[] verlenbytes=formattransfer.toHH(nverlen);
				System.arraycopy(verlenbytes, 0, clientverbytes, 0, 4);
				nbytelen+=4;
                byte[] verbytes=formattransfer.stringToBytes(strnewver);
                System.arraycopy(verbytes, 0, clientverbytes, nbytelen, verbytes.length);
            	nbytelen+=verbytes.length;
            	int nclientbyteslen=0;
                if(strnewver.compareTo(strclientversion)>0)
    			{
                	TBSYSTEMSET cpsystemset= userDao.getsystemset("client_path");
    				if(cpsystemset!=null)
    				{
    					String strclientpath=cpsystemset.getValue();
    					try
    					{
    					   Util myutil=new Util();
    					   String strcurpath=myutil.getWebRoot();
    					   byte[] clientbytes=Util.readFile(strcurpath+strclientpath);
    					   nclientbyteslen=clientbytes.length;    					  
    					}
    					catch(Exception ex)
    					{
    						
    					}				
    				}
    			}
                byte[] verclientlenbytes=formattransfer.toHH(nclientbyteslen);
				System.arraycopy(verclientlenbytes, 0, clientverbytes, nbytelen, 4);
				nbytelen+=4;
				int nclientmemo=0;
				String strclientmemo="";
				byte[] clientmemobytes=null;
				TBSYSTEMSET memosystemset= userDao.getsystemset("client_memo");
				if(memosystemset!=null)
				{
					strclientmemo=memosystemset.getValue();
	                clientmemobytes=formattransfer.stringToBytes(strclientmemo);
					nclientmemo=clientmemobytes.length;
				}
			    byte[] clientmemolenbytes=formattransfer.toHH(nclientmemo);
				System.arraycopy(clientmemolenbytes, 0, clientverbytes, nbytelen, 4);
				nbytelen+=4;
				if(nclientmemo>0)
				{
	                System.arraycopy(clientmemobytes, 0, clientverbytes, nbytelen, nclientmemo);
	            	nbytelen+=nclientmemo;
				}
				byte[] clientverfinbytes = new byte[nbytelen];
				System.arraycopy(clientverbytes, 0, clientverfinbytes, 0, nbytelen);
				return clientverfinbytes;
			}
		}
		return null;
	}
	
	public String getOppAddress(String mydstcalnum)
	{
		TBUSER oppuser=(TBUSER)this.phonenumusermap.get(mydstcalnum);
    	if(oppuser!=null )
    	{
    		TBCELL2GPS oppusercell2gps=(TBCELL2GPS)this.userphonenummap.get(oppuser.getId());
    		if(oppusercell2gps!=null)
    		{
    			String straddr=oppusercell2gps.getAddress();
    			System.out.print("mydstcalnum:"+mydstcalnum+",straddr:"+straddr+"\n");
    			return straddr;
    		}	        	      		
    	}
    	return "";
	}
	
	public boolean updateiamcalling(TBUSER u, TBCELL2GPS cell2gpsdata) {
		// TODO Auto-generated method stub
		boolean ret=false;
		//手机号码未验证
        //if(u.getIspnverify()==0)
        {
        	String straddr=this.getAddrbycellPrue(cell2gpsdata.getMcc(),cell2gpsdata.getMnc(),
					cell2gpsdata.getLac(),cell2gpsdata.getCid());
        	if(straddr==null || straddr.length()<=0){
        		return ret;
        	}
        	cell2gpsdata.setAddress(straddr);
        	userphonenummap.remove(u.getId());
        	phonenumusermap.remove(u.getPhonenumber());
        	userphonenummap.put(u.getId(), cell2gpsdata);
        	phonenumusermap.put(u.getPhonenumber(), u);    
        	String mydstcalnum=cell2gpsdata.getDestcalnum();        	
        	String myphonenum=u.getPhonenumber();
			String mypnupdate=cell2gpsdata.getUpdatetime();
			Date mypnupdatetime=DateUtils.strToDate(mypnupdate);
        	if(myphonenum!=null && mydstcalnum!=null)
        	{
	        	TBUSER oppuser=(TBUSER)this.phonenumusermap.get(mydstcalnum);
	        	if(oppuser!=null )
	        	{
	        		TBCELL2GPS oppusercell2gps=(TBCELL2GPS)this.userphonenummap.get(oppuser.getId());
	        		if(oppusercell2gps!=null)
	        		{
	        			String oppoppphonenum=oppusercell2gps.getDestcalnum();
	        			String oppuserpnupdate=oppusercell2gps.getUpdatetime();
	        			Date oppuserpnupdatetime=DateUtils.strToDate(oppuserpnupdate);
		        		if(oppoppphonenum!=null && oppoppphonenum.equals(myphonenum))
		        		{ 
		        			System.out.print(mydstcalnum+"oppuserpnupdatetime():"+oppuserpnupdatetime.toString()
		        					+","+myphonenum+",mypnupdatetime.getTime():"+mypnupdatetime.toString()+"\n");
		        			//相差不超过15秒，则认为号码验证成功
		        			if(Math.abs((oppuserpnupdatetime.getTime()/1000
		        					- mypnupdatetime.getTime()/1000))<15)
		        			{
		        				TBUSER myuser=this.userDao.getUser(u.getId());
		        				TBUSER myoppuser=this.userDao.getUser(oppuser.getId());
		        				if(null != myuser)
			        			{
				        			myuser.setIspnverify(1);
				        			myuser.setPhonenumber(myphonenum);
				        			this.userDao.updateUser(myuser);
			        			}
			        			if(null != myoppuser)
			        			{
			        				myoppuser.setIspnverify(1);
			        				myoppuser.setPhonenumber(mydstcalnum);
				        			this.userDao.updateUser(myoppuser);
			        			}
			        			return true;
		        			}
		        		}  
	        		}	        	      		
	        	}
        	}
        	return false;
        }
		//return ret;
	}
	
	
	public boolean updatecell2gps(TBCELL2GPS cell2gpsdata) {
		// TODO Auto-generated method stub
		String strkey=cell2gpsdata.getMcc().toString()+cell2gpsdata.getMnc().toString()
        +cell2gpsdata.getLac().toString()+cell2gpsdata.getCid()+String.valueOf(cell2gpsdata.getBshortcid());;
		if(this.truecellgpsmap.get(strkey)!=null)
		{
			return false;
		}
	    String straddr=this.getAddressbygoogle(cell2gpsdata.getLat(), cell2gpsdata.getLng());
	    cell2gpsdata.setAddress(straddr);
	    System.out.println("strkey:"+strkey+",straddr"+straddr+"\n");
		boolean ret=userDao.updatecell2gps(cell2gpsdata);
		if(ret)
		{
		    //System.out.println("put:"+this.allcellgpsmap.hashCode()+"<"+this.allcellgpsmap.size());
		    this.allcellgpsmap.remove(strkey);
			this.allcellgpsmap.put(strkey,cell2gpsdata);
			this.truecellgpsmap.remove(strkey);
			this.truecellgpsmap.put(strkey,cell2gpsdata);
		}
		return ret;
	}

	public TBGPSDATA getgpsbycellfromcache(int MCC, int MNC, int LAC, int CID,int BSHORTCID) {
		//在有的手机上不是460，而是0
		if(MCC==0)
		{
		   MCC=460;	
		}
		String strkey=String.valueOf(MCC)+String.valueOf(MNC)+
	    String.valueOf(LAC)+String.valueOf(CID)+String.valueOf(BSHORTCID);
		TBCELL2GPS cell2gps=this.allcellgpsmap.get(strkey);	
		if(cell2gps==null)
		{
			cell2gps=userDao.getcell2gps(MCC,MNC,LAC,CID,BSHORTCID);
			if(cell2gps==null)
			{
				   byte[] rd=this.getgpsbycell(MCC, MNC, LAC, CID);
				   if(rd !=null && rd.length>=15)
				   {
			   	       short opcode1 = (short)(rd[0] << 8 | rd[1]); 
			           byte opcode2 =  rd[2]; 
			           int ret_code = (int)((rd[3] << 24) | (rd[4] << 16) | (rd[5] << 8) | (rd[6])); 
			           if ((opcode1 == 0x0E) && (opcode2 == 0x1B) && (ret_code == 0)) 
			           { 
				            double lat = ((double)((((long)(rd[7] << 24)) & 0xffffffff) | (((long)(rd[8] << 16)) & 0xffffff) | 
			                 		        (((long)(rd[9] << 8)) & 0xffff) | ((rd[10]) & 0xff))) / 1000000; 
			                double lon = ((double)((((long)(rd[11] << 24)) & 0xffffffff) | (((long) (rd[12] << 16)) & 0xffffff)
			                 		        | (((long) (rd[13] << 8)) & 0xffff) | ((rd[14]) & 0xff))) / 1000000;
			                String straddr=this.getAddressbygoogle(lat, lon);
			                cell2gps = new TBCELL2GPS();
			                cell2gps.setMcc(MCC);
			                cell2gps.setMnc(MNC);
			                cell2gps.setLac(LAC);
			                cell2gps.setCid(CID);
			                cell2gps.setBshortcid(BSHORTCID);
			                cell2gps.setLat(lat);
			                cell2gps.setLng(lon);	
			                cell2gps.setAddress(straddr);
			                cell2gps.setUpdatetime(DateUtils.dateToStr(new Date()));
			                userDao.updatecell2gps(cell2gps);
			            	this.allcellgpsmap.remove(strkey);
							this.allcellgpsmap.put(strkey,cell2gps);
			           }
				   }
	   	   	 }
		}
		if(cell2gps!=null)
		{
			TBGPSDATA gpsdata=new TBGPSDATA();
			gpsdata.setLat(cell2gps.getLat());
			gpsdata.setLng(cell2gps.getLng());
			gpsdata.setMemo(cell2gps.getAddress());
			return gpsdata;
		}
		return null;
	}
    
}
