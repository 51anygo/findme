<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！</title>
    
    <style type="text/css">
<!--

body {
	margin-left: 2%;
	margin-top: 0%;
	margin-right: 2%;
	margin-bottom: 0px;
}
.title {
	font-size: 24px;
	font-weight: bold;
}

.errMsg {color: #FF0000}

-->
    </style>
</head>
<%@include file="global.jsp"%>
 <div id="header" style="background-color: #339999;">
		<table width="100%" border="0">
          <tr>
            <td width="15%"><a href="index.jsp"><img src="images/anygologo.JPG" alt=""     width="128px" height="66px" longdesc="" /></a></td>
            <td width="72%" align="right"> <%if(logusername!="") { %><%=logusername%>,欢迎您!<!--  <a href="logout.do">退出</a>-->&nbsp; <%}else{%>
            <a href="login.jsp">登陆</a>&nbsp;<%}%> </td>
            <td width="12%"><!-- <a href="reg.jsp">注册</a> -->  <a href="index.jsp">首页</a> <a href="download.jsp">下载</a></td>      
          </tr>
          <tr>
              </tr>
        </table>
</div> 