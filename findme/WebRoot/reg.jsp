<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！注册新用户</title>
     <script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
  <script type='text/javascript' src='dwr/engine.js'></script>
      <script type='text/javascript' src='dwr/util.js'></script>
            <script type='text/javascript' src='js/checkinput.js'></script>
    <script type="text/javascript">
    var inum_check=0;
    var bvalid=false;
 function usr_check()
{
    var usrname=document.getElementById("usr").value;
    var usrerrorMsg=document.getElementById("usrerrMsg");
    var response=checkUser(usrname);
   	if(response!="")
   	{
   	  usrerrorMsg.innerHTML=response; 
   	  return false;
   	}
    JUserCheck.checkUser(usrname,GetUserExistCallback);
	usrerrorMsg.innerHTML="";
	return true;	 
}
 
  function email_check()
{
    var emailvalue =document.getElementById("email").value;
	var emailerrorMsg=document.getElementById("emailerrorMsg");
	var response=checkEmail(emailvalue);
   if(response!="")
   {
	   emailerrorMsg.innerHTML=response; 
	   return false;
   }
	emailerrorMsg.innerHTML=""; 
	return true;
}
 
   function pwd_check()
{
    var pwdvalue =document.getElementById("pwd").value;
	var repwdvalue =document.getElementById("repwd").value;
	var pwderrorMsg=document.getElementById("pwderrorMsg");		
	var response=checkSimplePassword(pwdvalue);
	if(response!="")
   	{
   	  pwderrorMsg.innerHTML=response; 
   	  return false;
   	}
	if(pwdvalue!=repwdvalue)
	{
	   pwderrorMsg.innerHTML="两次密码不相同!"; 
	   return false;
	}
	pwderrorMsg.innerHTML=""; 
	return true;
}

   function num_check()
 {
    var num=document.getElementById("num").value;
    if(num!="")
    {
	  JUserCheck.checkCRCNum(num,GetRightNumCallback);
	 }
	return true;
}
function refresh(o){	
	//重载验证码
	var img = document.getElementById("random");
	var timenow = new Date().getTime();
    img.src = "authImage?d="+timenow;
}
function onmysubmit()
{
    if(!(usr_check() && pwd_check()
      && email_check() && num_check()))
    {
       return false;
    }
 	//document.getElementById('usrreg').submit();
    //return;
    var handle=setTimeout(function(){	
    	clearTimeout(handle);
		if(bvalid)
	    {
	      document.getElementById('usrreg').submit();
	      return false;
	    }
	}	
	,2000);
    
}
 
  function GetRightNumCallback(returnVal)
{
	var response=returnVal;
	var numerrorMsg=document.getElementById("numerrorMsg");
	if(response!="")
	{
	  inum_check++;
	  if(inum_check>=3)
	  {
	    refresh(document.getElementById('random'));
	    inum_check=0;
	  } 
	  numerrorMsg.innerHTML=response;
	  bvalid=false;
	  return;
	}
	else
	{
		bvalid=true;
	}
    numerrorMsg.innerHTML="";

}
 
 
 function GetUserExistCallback(returnVal)
{
    if(returnVal==null)
      return;
	var response=returnVal;
	var usrerrMsg=document.getElementById("usrerrMsg");
	if(response!="")
	{
	  usrerrMsg.innerHTML=response; 
	  bvalid=false;
	  return;
	}
	else
	{
	  bvalid=true;
	}
	usrerrMsg.innerHTML="";
}



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

  <body onload="onmysubmit()">
 <%@include file="header.jsp"%>
  <div id="header" style="width:100%;height:600px">
    <p align="center" class="title">用户注册  </p>
    <hr />
    <form action="reg.do" method="post" id="usrreg" name="usrregform"  onsubmit="return onmysubmit()">
      <label></label>
      <div align="center">
        <table width="718" border="0">
          <tr height="39">
            <td width="72">&nbsp;</td>
            <td width="72">&nbsp;</td>
            <td width="72">&nbsp;</td>
            <td width="85"><label>用户名：</label></td>
            <td width="126"><div align="left">
              <input id="usr" name="usr" type="text" value="${usr}" style="width: 120px;" maxlength="15" onblur="usr_check()"/>
            </div></td>
            <td width="265" ><div align="left"><span class="errMsg" id="usrerrMsg"></span></div></td>
          </tr>
          <tr>
            <td height="32">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>密 码 ：</td>
            <td>
              <div align="left">
                <input id="pwd" name="pwd"  type="password" value="${pwd}" style="width: 120px;" style="width: 120px;"size="17" maxlength="15" onblur="pwd_check()"/>
              </div></td><td><div align="left"><span class="errMsg" id="pwderrorMsg"></span></div></td>
          </tr>
          <tr height="39">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>密码确认：</td>
            <td><div align="left">
              <input id="repwd" name="repwd" type="password" value="${repwd}" style="width: 120px;" size="17" maxlength="15" onblur="pwd_check()"/>
            </div></td>
            <td ><div align="left"></div></td>
          </tr>
	      <tr height="39">
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
            <td>邮 箱：</td>
            <td><div align="left">
              <input id="email" name="email" type="text" value="${email}" style="width: 120px;" onblur="email_check()"/>
            </div></td>
            <td><div align="left"><span class="errMsg" id="emailerrorMsg"></span></div></td>
          </tr>
        </table>
      </div>
      <hr />
      <div align="center">
        <table width="952" border="0">
          <tr>
            <td width="66">&nbsp;</td>
            <td width="206">&nbsp;</td>
            <td width="58">&nbsp;</td>
              <td width="85"><div align="right">验 证 码： <img src="authImage"  height="20" id="random" align="" valign="absmiddle" hspace="4"/></div></td>
            <td width="237"><div align="left">
              <input id="num" type="text" name="num"  style="width: 120px;" maxlength="4" onblur="num_check()"/>
            <a href="javascript:refresh(document.getElementById('random'))">验证码看不清</a></div></td>
            <td width="274"><div align="left"><span class="errMsg" id="numerrorMsg"></span></div></td>
          </tr>
        </table>
        <table width="200" border="0">
          <tr>
            <td width="58">&nbsp;</td>
            <td width="42"><div align="center">
              <input id="reset" type="reset" name="reset" value="清空" />
            </div></td>
            <td width="86"><div align="center">
              <input id="submit" type="submit" name="submit" value="提交"/>
            </div></td>
          </tr>
        </table>
      </div>
	  <div align="center" class="errMsg">
      <table width="48%" height="88" border="0">
        <tr>
          <td align="left" >${result}</td>
        </tr>
      </table>
	  </div>
    </form>
        <%@include file="footer.jsp"%>
    </div>

  </body>
</html>