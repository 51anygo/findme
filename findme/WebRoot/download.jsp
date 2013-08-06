<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！下载软件</title>
     <script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
  <script type='text/javascript' src='dwr/engine.js'></script>
      <script type='text/javascript' src='dwr/util.js'></script>
            <script type='text/javascript' src='js/checkinput.js'></script>
    <script type="text/javascript">

    //<![CDATA[
</Script>
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

  <body>
<%@include file="header.jsp"%>
  <p><strong>定位程序</strong><strong>下载:</strong></p>
   <p> </p>
  <div><strong>适用于Windows CE平台</strong>： <a href="downloads/wince5/51AnyGoCe5.rar">51AnyGoCe5.rar</a></div>
  <div>
    <blockquote>
      <p>说明：支持虚拟多个串口，网上定位功能，在E路航LH950下测试成功</p>
    </blockquote>
  </div>
  <ul>
    <li>建议在Windows CE5 及以上版本使用。 </li>
  </ul>
  <p> </p>
    <div><strong>适用于PPC/Windows Mobile平台</strong>(<FONT color="#ff0000">新</FONT>)： <a href="downloads/ppc2003/51AnyGoPPC.rar">51AnyGoPPC.rar</a>&nbsp; <a href="downloads/ppc2003/51AnyGoShow.rar" target="_blank">安装演示</a></div>
  <div>
    <blockquote>
      <p>说明：支持虚拟多个串口、短信定位、手机防盗、网上定位等功能，在多普达s900/s1下测试成功</p>
    </blockquote>
  </div>
  <ul>
    <li>建议在PPC 2003或Windows Mobile 6及以上版本使用。 </li>
  </ul>
   <p></p>
  <p><a href="demo.jsp">定位演示</a>&nbsp&nbsp&nbsp&nbsp&nbsp<a href="tracing.jsp">开始定位</a></p>
  <p> </p>
  <%@include file="footer.jsp"%>
  </body>
</html>