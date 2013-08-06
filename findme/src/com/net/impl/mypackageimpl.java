package com.net.impl;
/*
 不够严谨，下面是我的总结：
C++                    Java
虚函数     --------   普通函数
纯虚函数   --------   抽象函数
抽象类     --------   抽象类
虚基类     --------   接口
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;
import com.net.formattransfer;
import com.net.mypackage;
abstract public class mypackageimpl implements mypackage {
	protected int type;
	protected int len;
	
	public static final int  INTLEN          =4;
	public static final int  DOUBLELEN       =8;
	public static final int  LONGLEN         =4;
	public static final int  PACKAGEHEADERLEN=8;
	
	//获得基站对应的GPS地址，由GOOGLE提供接口
	public static final int  TYPEGETGPSBYCELL=0x0001;
	//获得GPS位置对应的地址
	public static final int  TYPEGETADDRBYGPS=0x0002;
	//注册新用户
	public static final int  TYPEREGUSER     =0x0003;
	//提交GPS信息更新GPS位置
	public static final int  TYPEUPDATEGPSPOS=0x0004;
	//获得更新客户端的程序
	public static final int  TYPEUPDATECLIENT=0x0005;
	//检查是否需要更新客户端
	public static final int  TYPECHKUPDATECLIENT=0x0006;
    //通过采集终端数据更新基站对应的GPS位置
	public static final int  TYPEUPDATECELLGPS=0x0007;
	//优先从自己的数据库获得基站的GPS位置
	public static final int  TYPEGETGPSBYCELLFROMLOCAL=0x0008;
	//提交基站信息更新GPS位置
	public static final int  TYPEUPDATEGPSBYCELL=0x0009;
	//检测程序版本是否需要升级
	public static final int   TYPECHKFREECLIENT=0x000A;
	//通过基站取地址和GPS位置
	public static final int   TYPEGETADDRBYCELL=0x000B;
	//通话时提交基站地址，被叫号码
	public static final int   TYPEUPDATEIAMCALLING=0x000C;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getLen() {
		return len;
	}
	
	public void setLen(int len) {
		this.len = len;
	} 
	
	static public mypackage preparse(byte []pd)
	{ 		   
		if(pd.length<8)
		{
		  return null;
		}
		int type=-1;
		int len=-1;
		type=formattransfer.lBytesToInt(pd); 
		byte[] tempbytes = new byte[4];
		System.arraycopy(pd, 4, tempbytes, 0, 4);
		len=formattransfer.lBytesToInt(tempbytes); 
		mypackage newmypack = null;
	    switch(type)
		{
			case TYPEGETGPSBYCELL:
				newmypack = new getgpsbycellpackage();
			    break;
			case TYPEGETADDRBYGPS:
				newmypack = new getaddrbygpspackage();
			    break;
			case TYPEREGUSER:
				newmypack = new reguserpackage();
			    break;
			case TYPEUPDATEGPSPOS:
			    newmypack = new updategpspospackage();
			    break;
			case TYPEUPDATECLIENT:
				newmypack = new updateclientpackage();
				break;
			case TYPECHKUPDATECLIENT:
				newmypack = new chkupdateclientpackage();
				break;
			case TYPEUPDATECELLGPS:
				newmypack = new updatecellgpspackage();
				break;
			case TYPEGETGPSBYCELLFROMLOCAL:
				newmypack = new getgpsbycellfromlocalpackage();
				break;	
			case TYPEGETADDRBYCELL:
				newmypack = new getaddrbycellpackage();
				break;	
			case TYPEUPDATEGPSBYCELL:
				newmypack = new updategpsbycellpackage();
				break;
			case TYPECHKFREECLIENT:
				newmypack = new chkfreeclientpackage();
				break;
			case TYPEUPDATEIAMCALLING:
				newmypack = new updateiamcallingpackage();
				break;
			default:
				break;
		}
	    if(newmypack!=null)
	    {
			newmypack.setLen(len);
			newmypack.setType(type);
			newmypack.parse(pd);
	    }
		return newmypack;
	}
	
	abstract public mypackage parse(byte []pd);
	abstract public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz);

}
