/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.struts.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.servlet.ServletOutputStream;
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
import com.hook.Reflector;
import com.net.mypackage;
import com.net.impl.getgpsbycellpackage;
import com.net.impl.mypackageimpl;

/** 
 * MyEclipse Struts
 * Creation date: 12-21-2009
 * 
 * XDoclet definition:
 * @struts.action
 */
public class queryaddrAction extends Action {
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
	//http://localhost:8080/findme/q.do?a=22.7264&n=113.8011
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	 
		int contentlength=request.getContentLength();
		if(contentlength<=0)
		{
		    return null;
		}
		try 
	    {
		    InputStream in = request.getInputStream();
		    byte [] bytes = new byte[contentlength]; 
		    in.read(bytes);
	        in.close(); 
	        mypackage findpack=mypackageimpl.preparse(bytes);
	        if(findpack!=null)
	        {
	        	findpack.response(request,response,this.userBiz);
	        	return null;
	        }
	    } 
	    catch (IOException e)
	    {
	       e.printStackTrace();
	    }
		return null;
	}
}