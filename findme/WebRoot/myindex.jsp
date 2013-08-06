<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！</title>
     <script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
  <script type='text/javascript' src='dwr/engine.js'></script>
      <script type='text/javascript' src='dwr/util.js'></script>
 <!--  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false
    &amp;key=ABQIAAAA4encCJt73DHSBOVMNi-yVhSiXrmLyms9I5JDm6fR-AFDJU2vdRQzokL6HsymYBTNLPeivqhNdA6x9w"
    type="text/javascript">-->
 <!--  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false
    &amp;key=ABQIAAAA4encCJt73DHSBOVMNi-yVhSiXrmLyms9I5JDm6fR-AFDJU2vdRQzokL6HsymYBTNLPeivqhNdA6x9w"
    type="text/javascript">-->
    <!--  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false
    &amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ"
    type="text/javascript">
  </script>
   -->  
      <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ&hl=zh-CN"
      type="text/javascript"></script>
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
     /*beijing
     var xOffset= 0.001381939;
     var yOffset= 0.006142259;
     */
     
     //shanghai
     //var xOffset= -0.001889737;
     //var yOffset= 0.004844069;
     
     //shenzhen
     var xOffset= -0.002889737;
     var yOffset= 0.004800000;
function checkUsername()
{
    //alert('timer');
	//JUserCheck.getGps(GetGpsCallback);
	JUserCheck.getAddress(GetAddressCallback);
}

function GetAddressCallback(returnVal)
{
    //alert(returnVal);
    // alert('timer');
    if(returnVal==null || returnVal.length<2)
      return;
    if(returnVal[1]==null || returnVal[1]==0)
      return;
    if(returnVal[1]!=null && returnVal[1]!="")
    {
	    if(returnVal[2]!=null && returnVal[2]!="")
	    {
	      nextaddress=returnVal[1];
	      showAddress(returnVal[1]+returnVal[2]);    
	    }
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

function GetGpsCallback(returnVal)
{
    //alert(returnVal);
    // alert('timer');
    if(returnVal==null)
      return;
	lant=returnVal.lat;
	longi=returnVal.lng;
	if(lant>0 && longi>0)
	{
	  updatepos(lant,longi,now);
	}
}

function updatepos(lant,longi,now) {
      if (map!=null) {
            var type=map.getCurrentMapType();
            var point;
            if (type==G_NORMAL_MAP)
            {
	          point = new GLatLng(lant+xOffset, longi+yOffset);
	          //alert("ok");
	        }
	        else
	        {
	          point = new GLatLng(lant, longi);
	        }
	        if(oldpoint != null && point.lat()==oldpoint.lat() && point.lng()==oldpoint.lng())
		    {
		       return;
		    }
	        map.setCenter(point,zoom);        
			map.addOverlay(mk=new GMarker(point));
			mkarrays.push(mk);			
			var polyline = new GPolyline([
									  oldpoint,
									  point
									], "#ff0000", 10);
			map.addOverlay(polyline);
			oldpoint= point;
    }
 }
 
    function showAddress(address) {
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
    //<![CDATA[
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
        map.setCenter(point,zoom);
        //startTimer();
        JUserCheck.getAddress(GetAddressCallback);
    }

 }
    //]]>
function checkTime() 
{ 
	alert('定时器测试');
} 
    
    
var timer;
function startTimer(){
   timer= setInterval("checkUsername()",5000); //5秒执行一次
}

function stopTimer(){
   clearInterval(timer);
}

</Script>
    <style type="text/css">
<!--
.header {
	color: #000000;
	font-size: 30px;
}
.title {color: #6600CC; font-size: 36px; font-family: "新宋体"; }

body {
	margin-left: 2%;
	margin-top: 0%;
	margin-right: 2%;
	margin-bottom: 0px;
}

.errMsg {color: #FF0000}
-->
    </style>
</head>

  <body onload="load()">
  <div>
<%@include file="header.jsp"%>
</div>
  <div class="header" id="main" style="width:100%;height:600px;float:left;">
    <div align="center" class="title">任你行，茫茫人海，轻松定位！</div>
    <div id="nav" style="width:15%;height:600px;float:left;">    
	  <p><a href="reg.jsp">注册用户</a></p>
	  <p>&nbsp;</p>
	  <p><a href="tracing.jsp">精确定位</a></p>
	  <p>&nbsp;</p> 
	  <p><a href="ipmap.jsp">其它定位</a></p>
	  <p>&nbsp;</p>
	  <p><a href="download.jsp">下载客户端</a></p>
	  <p>&nbsp;</p>
	   <p><a href="demo.jsp">定位演示</a></p>
	  <p>&nbsp;</p>
	</div>
   <div id="map" style="width:80%;height:600px;float:right;">  
   </div>
  </div>
  &nbsp;
    <div align="center" id="bottom">
    <%@include file="footer.jsp"%> 
  </div></body>
</html>
