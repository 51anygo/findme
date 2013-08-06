package com.net;


public interface mypackage {
	public int getType();
	void setType(int type);
	public int getLen();
	public void setLen(int len);
	public mypackage parse(byte []pd);
	public byte[] getpackage();
	//public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz);
}
