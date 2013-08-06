<%
/**
 *	$RCSfile: global.jsp,v $
 *	$Revision: 1.4 $
 *	$Date: 2002/10/14 13:30:14 $
 */
%>
<jsp:useBean id="myuserbiz" scope="application" class="com.biz.impl.UserBizimpl"/>
<jsp:setProperty name="myuserbiz" property="*"/>

<%@ page import="java.util.*,
                 com.biz.impl.*,
                 com.entity.*"
%>

<%  
    //init forumfactory and pageUser
    TBUSER myuser=myuserbiz.isLogin(request.getSession());
    String logusername="";
    if(myuser!=null)
    {
     logusername=myuser.getUsername();
    }
    String lasturl=request.getHeader("Referer");
    if(lasturl==null)
    {
       lasturl="index.jsp";
    }
    //String lasturl=request.getHeader("Referer").replaceAll( 
	//				"http://" + request.getHeader("Host")+ request.getContextPath(), ""); 
			
%>

