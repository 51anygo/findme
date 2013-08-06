/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.struts.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.biz.UserBiz;
import com.entity.TBUSER;
import com.struts.form.logForm;

/** 
 * MyEclipse Struts
 * Creation date: 04-04-2009
 * 
 * XDoclet definition:
 * @struts.action path="/login" name="logForm" input="/form/login.jsp" scope="request" validate="true"
 */

public class logAction extends Action {
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
		logForm mylogForm = (logForm) form;
		 String result="";
		 int ierror=0;
		 TBUSER user=new TBUSER();
		 user.setUsername(mylogForm.getUsr());
		 user.setPassword(mylogForm.getPwd());
		 String strlasturl=mylogForm.getLasturl();
		 String strforwardurl=mylogForm.getForwardurl();
		 if(strforwardurl!=null && strforwardurl!="")
		 {
			 strlasturl=strforwardurl;
		 }
	     String responsetext=this.userBiz.checkCRCNum(mylogForm.getNum(), request.getSession());
		 if(responsetext!=""){
				ierror++;
				result=result+String.valueOf(ierror)+": "+responsetext+"<br>";
			}
			TBUSER loginuser=this.userBiz.login(user);
			if(loginuser==null){
				ierror++;
				result=result+String.valueOf(ierror)+": 登陆失败:用户名或密码错误!"+"<br>";
			}
			if(ierror>0){
				result=result+"<br>";
				request.setAttribute("usr",mylogForm.getUsr());
				request.setAttribute("pwd", mylogForm.getPwd());
				request.setAttribute("result", result);
				request.setAttribute("forwardurl", strlasturl);				
			    return new ActionForward("/login.jsp");
			}			
			HttpSession mysession=request.getSession();
			mysession.setAttribute("loguser", loginuser.getUsername());		    
	        try {    
	        	if(strlasturl!=null && strlasturl!="" && strlasturl.lastIndexOf("logout.do")==-1)
	        	{
	               response.sendRedirect(strlasturl);
	        	}
	        	else
	        	{
	        	  return new ActionForward("/index.jsp");
	        	}
	            return null;    
	        } catch (IOException e) {    
	            e.printStackTrace();    
	        }    
	        return null;
		    //return new ActionForward("/index.jsp", false);
	}
}