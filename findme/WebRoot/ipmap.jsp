<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>任你行,实时定位好帮手！IP/基站定位</title>
		<script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
		<script type='text/javascript' src='dwr/engine.js'></script>
		<script type='text/javascript' src='dwr/util.js'></script>
		<script type='text/javascript' src='js/checkinput.js'></script>
 <!--  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false
    &amp;key=ABQIAAAA4encCJt73DHSBOVMNi-yVhSiXrmLyms9I5JDm6fR-AFDJU2vdRQzokL6HsymYBTNLPeivqhNdA6x9w"
    type="text/javascript">-->
    <!--  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false
    &amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ"
    type="text/javascript">
  </script>

      <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ&hl=zh-CN"
      type="text/javascript"></script>  --> 
    <script src="http://ditu.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ&hl=zh-CN"
      type="text/javascript"></script>   
		<script type="text/javascript">
    var lant;
    var longi;
    var now;
    var map=null;
    var geocoder=null;
    var mkarrays=new Array(); 
    var oldpoint=null;  
	var zoom=4;
    var nextaddress=null;
    function GetAddressbyipCallback(returnVal)
    {
	    if(returnVal==null)
	      return;
	    if(returnVal[0]!=null)
	    {
	     var iptext = document.getElementById("ip");
	     if(iptext!=null)
    	 {
    	   iptext.value=returnVal[0];
    	 } 
	    }
	    if(returnVal[1]!=null && returnVal[1]!="")
	    {
		    nextaddress=returnVal[1];   
		}
	    if(returnVal[2]!=null && returnVal[2]!="")
	    {
	       nextaddress=nextaddress+returnVal[2];   
	    }
	    if(nextaddress!=null && nextaddress!="")
	    {
	    	showAddress(nextaddress); 
	    }
	    if(returnVal[3]!=null)
	    {
	    	var resultMsg = document.getElementById("result");
	    	if(resultMsg!=null)
	    	{
	    	  resultMsg.innerHTML=returnVal[3];
	    	} 
	    }
   }
    

    function showAddress(address) {
      //alert("不能解析:1 ");
      if (geocoder) {
      geocoder.getLatLng(
      address,
      function(point) {
        if (!point) {
          //alert("不能解析: " + address);
          if(nextaddress!=null && nextaddress!="")
          {
             var tempaddress=nextaddress;
             nextaddress=null;
             showAddress(tempaddress);   
          }
        } else {
          map.setCenter(point, 13);
          var marker = new GMarker(point);
          map.addOverlay(marker);
          var catorMsg = '<div style="font-size:16px;color:#000000;width:200px;padding:8px;">'
          +address+ '<br/>' + point.toString()+'</div>'          
          marker.openInfoWindowHtml(catorMsg);
        }
      }
    );
  }
}

    function load() { 
      if (GBrowserIsCompatible()) {
	    map = new GMap2(document.getElementById("map"));
	    geocoder = new GClientGeocoder();
	    map.enableScrollWheelZoom(); 	       
		map.addControl(new GLargeMapControl());	
		//map.addControl(new GOverviewMapControl());
		map.addControl(new GMapTypeControl());
		//map.addControl(new GScaleControl());
		//map.removeMapType( G_HYBRID_MAP );
		G_SATELLITE_MAP.getName = function(){ return '卫星图';};  
		map.addMapType(G_SATELLITE_MAP); 
        var point = new GLatLng(36.94,106.08);
        map.setCenter(point,4);
        JUserCheck.getAddress(GetAddressbyipCallback);
    }

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
    var ip=document.getElementById("ip").value;
    var num=document.getElementById("num").value;
    var resultMsg = document.getElementById("result");
   	if(resultMsg!=null)
   	{
   	  resultMsg.innerHTML="";
   	} 
    //JUserCheck.getAddressbyip(ip,num,GetAddressbyipCallback);
    JUserCheck.getAddressbylike(ip,num,GetAddressbyipCallback);
    return false;
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
	color: #6600CC;
	font-size: 36px;
	font-family: "新宋体";
}

.errMsg {
	color: #FF0000
}
-->
</style>
	</head>
	<body onload="load()">
<div>
		<form action="" method="post" id="ipmap" name="ipmap"
			onsubmit="return onmysubmit()">
			<%@include file="header.jsp"%>
			<div class="header" id="main" style="width:100%;height: 600px">
				<div align="center" class="title"> 
					IP/基站定位，轻松定位！ 
				</div>
				<div id="nav" style="width:18%; float: left; height: 600px">		
					<p>
					</p>
					<p> 
						IP/基站:&nbsp<input type="text" id="ip" name="ip" value="" size="18" />&nbsp;
					</p>
					<p>验证码: 
						<input type="text" id="num" name="num" value="" style="width: 40px;" maxlength="15" /> 
						<img hspace="4" height="20" align="" src="authImage" id="random" valign="absmiddle" /> 
					</p>
					<p>
						<input id="submit" type="submit" name="submit" onclick="onmysubmit();"
							value="开始" />
					</p>
                    <p> 
						查询结果:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					</p>
					<p>
						<span class="errMsg" id="result"></span>
					</p>
					<table>
				     <tr>
                     <td>使用帮助:<br />ip的格式为:202.96.125.106 基站的格式为:10160,3721</td>
                     </tr>
                    </table>
				</div>
				<div id="map" style="width:80%;height:600px;float:right;">
				</div>
		</form>
		</div>
		<%@include file="footer.jsp"%>
	</body>