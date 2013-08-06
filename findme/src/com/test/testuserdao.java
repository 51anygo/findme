package com.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.biz.Globals;
import com.biz.Util;
import com.biz.impl.UserBizimpl;
import com.common.DateUtils;
import com.dao.myuserdao;

public class testuserdao {
	public static void main(String[] args) {
		/*try
		{
			System.out.println(System.getProperty("user.dir"));
		   byte[] clientbytes=Util.readFile("./WebRoot/index.jsp");
		   int a=clientbytes.length;
		   int b=a;
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

        String shortCID = "";   
            int MCC = 460;
            int MNC = 0;
            int LAC = 10160;
            int CID = 3710;
            HttpURLConnection connection = null;
            OutputStream output = null;
            //OutputStreamWriter outr = null;
    		try
    		{
    			byte[] pd = PostData(MCC, MNC, LAC, CID, shortCID == "shortcid");
    		   String strurl="http://www.google.com/glm/mmap";
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
   		      return;
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
      	       while (totalBytesRead < rd.length)
      	       {
      	           totalBytesRead += in.read(rd, totalBytesRead, rd.length - totalBytesRead);
      	       }
      	       in.close(); 
      	       connection.disconnect();
      	       short opcode1 = (short)(rd[0] << 8 | rd[1]); 
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
   		 } */
    }
}


	/**
	 * @param args
	 */
/*public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
		"applicationContext.xml");
		myuserdao dao = (myuserdao) context.getBean("myuserdao");
		Tbuser u = new Tbuser();
		u.setPassword("1234");
		u.setUsername("abcd");
		dao.addUser(u);
		Globals g = new Globals();
		String dms=g.TranDegreeToDMs(22.551047);
		double d=g.TranDMsToDegree("22°33′4″");
		System.out.println(dms);
		System.out.println(d);;
		UserBizimpl ub = new UserBizimpl();
		ub.getAddressbygoogle(1.0,1.0);
		long nowtime=0;
		String gpstime="1262248319";
		//String gpstime="1262246645";
		nowtime=Long.parseLong(gpstime);
		Date nowdate=new Date();
		nowdate.setTime(nowtime*1000);
		gpstime=DateUtils.dateToStr(nowdate);
		nowtime=0;
	}

}*/
