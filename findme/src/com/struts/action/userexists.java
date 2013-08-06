/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.entity.TBGPSDATA;
import com.entity.TBUSER;

/** 
 * MyEclipse Struts
 * Creation date: 05-06-2009
 * 
 * XDoclet definition:
 * @struts.action
 */
public class userexists extends Action {
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String username=request.getParameter("u");
		String password=request.getParameter("p");
		if(username != null && !username.equals("") 
				&& password!=null && !password.equals(""))
		{	
			TBUSER u = new TBUSER();
			u.setUsername(username);
			u.setPassword(password);
			u = this.userBiz.login(u);
			try {
	            PrintWriter out = response.getWriter();
	    		if(u == null)
				{
		            out.write("ERROR");
				}
	    		else
	    		{
	    			out.write("SUCCESS");
	    		}
	            out.flush();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		return null;
	}
}