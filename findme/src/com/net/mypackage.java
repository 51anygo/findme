package com.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biz.UserBiz;

public interface mypackage {
	public int getType();
	void setType(int type);
	public int getLen();
	public void setLen(int len);
	public mypackage parse(byte []pd);
	public void response(HttpServletRequest request,HttpServletResponse response,UserBiz userbiz);
}
