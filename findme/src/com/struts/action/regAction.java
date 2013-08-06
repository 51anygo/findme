/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.biz.UserBiz;
import com.dao.myuserdao;
import com.entity.TBUSER;
import com.struts.form.regForm;

/** 
 * MyEclipse Struts
 * Creation date: 03-31-2009
 * 
 * XDoclet definition:
 * @struts.action path="/reg" name="regForm" input="/reg.jsp" scope="request" validate="true"
 */
public class regAction extends Action {
	private UserBiz userBiz;
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	/*
	 * Generated Methods
	 */
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
		regForm myregform = (regForm) form;// TODO Auto-generated method stub
		 String result="";
        int ierror=0;
        String responsetext=this.userBiz.checkCRCNum(myregform.getNum(), request.getSession());
		if(responsetext!=""){
			ierror++;
			result=result+String.valueOf(ierror)+": "+responsetext+"<br>";
		}
		responsetext=this.userBiz.checkUser(myregform.getUsr());
		if(responsetext!=""){
			ierror++;
			result=result+String.valueOf(ierror)+": "+responsetext+"<br>";
		}
		/*responsetext=this.userBiz.checkEmail(myregform.getEmail());
		if(responsetext!=""){
			ierror++;
			result=result+String.valueOf(ierror)+": "+responsetext+"<br>";
		}*/
		if(ierror>0){
			result="注册失败:"+"<br>"+result;
			request.setAttribute("usr",myregform.getUsr());
			request.setAttribute("pwd", myregform.getPwd());
			request.setAttribute("repwd", myregform.getRepwd());
			request.setAttribute("email",myregform.getEmail());
			request.setAttribute("result", result);
		    return new ActionForward("/reg.jsp");
		}
		TBUSER user=new TBUSER();
		user.setUsername(myregform.getUsr());
		user.setPassword(myregform.getPwd());
		user.setEmail(myregform.getEmail());
	    if(this.userBiz.add(user))
	    {
		    return new ActionForward("/regsuc.jsp");
	    }
	    return new ActionForward("/index.jsp");
	}

}