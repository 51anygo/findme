package com.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.biz.UserBiz;
import com.common.DateUtils;
import com.common.MySecurity;
import com.entity.TBGPSDATA;
import com.entity.TBUSER;

public class timesyncAction extends Action {
	public static final long SECONDS_1900_TO_1970 = 2208988800L;
	
	/*
	 * Generated Methods
	 */
	private UserBiz userBiz;
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	/*
	 * (RFC-868)协议是一种较简单的协议。此协议提供了一个独立于站点的，机器可读的日期和时间信息。时间服务返回的是以秒数，是从1900年1月1日午夜到现在的秒数。
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {		
		String strnowtime;	
		Date nowdate=new Date();
		long nowtime=nowdate.getTime()/1000+SECONDS_1900_TO_1970;
		strnowtime=String.valueOf(nowtime);
	    try 
	    {
	        PrintWriter out = response.getWriter();
	        out.write(strnowtime); //stand for the report success
	        out.close();
	      } 
		  catch (IOException e)
	      {
	        e.printStackTrace();
	      }
		  return null;
	}
	
}