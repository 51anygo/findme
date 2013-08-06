<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！正在定位...</title>
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
     <script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
  <script type='text/javascript' src='dwr/engine.js'></script>
      <script type='text/javascript' src='dwr/util.js'></script>
            <script type='text/javascript' src='js/checkinput.js'></script>
    <script type="text/javascript">
    var oldgpsid=null;
    var lant;
    var longi;
    var now;
    var speed;
    var angle;
    var map;
    var mkarrays=new Array(); 
    var oldpoint=null;  
	var zoom=15;
	var followmap=false;
           
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
     DWREngine.setTimeout(70000);//设置一个超时时间 
     DWREngine.setErrorHandler(eh);//超时以后错误的处理函数 
	function eh(msg, ex) 
	{ 
		//alert(msg + ", date=" + ex.when); 
	} 
     
function checkUsername()
{
    //alert('timer');
	JUserCheck.getGps(GetGpsCallback);
}

function GetGpsCallback(returnVal)
{
    //alert(returnVal);
    if(returnVal==null)
      return;
    if(returnVal.id==null || returnVal.id==oldgpsid)
    {
      return;
    }
    oldgpsid=returnVal.id;
	lant=returnVal.lat;
	longi=returnVal.lng;
	now=returnVal.gpstime;
	speed=returnVal.speed;
	angle=returnVal.angle;
	//alert('timer');
	if(lant>0 && longi>0)
	{
	  updatepos();
	}
}

function updatepos()
{
    var latmsg=document.getElementById("latmsg");
    if(latmsg!=null)
    {
   	  latmsg.innerHTML=lant;
   	} 
    //JUserCheck.checkUser(usrname,GetUserExistCallback);
	var lngmsgtext=document.getElementById("lngmsg");
	if(lngmsgtext!=null)
	{
   	  lngmsgtext.innerHTML=longi;
   	} 
   	if(speed!=null)
	{
	   	var spdmsgtext=document.getElementById("spdmsg");
	   	if(spdmsgtext!=null)
	   	{
	   	  spdmsgtext.innerHTML=speed+'公里/小时';
	   	}
   	} 
   	if(angle!=null)
	{
	   	var headingmsgtext=document.getElementById("headingmsg");
	   	if(headingmsgtext!=null)
	   	{
	   	  headingmsgtext.innerHTML=angle+'度';
	   	}
   	} 
   	var lastupdatedmsgtext=document.getElementById("lastupdatedmsg");
   	if(now!=null) 
   	{
   	  //alert(now);
   	  now=new Date(now).pattern("yyyy-MM-dd EEE HH:mm:ss");
   	  if(lastupdatedmsgtext!=null)
   	  {
   	     	lastupdatedmsgtext.innerHTML=now;  
   	  }
   	}  	
    if (map!=null)
    {
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
        if(document.getElementById("followSelected").checked)
        {          
             map.setCenter(point,zoom);
        }      
        if(mkarrays.length>1)
        {
          map.removeOverlay(mkarrays[mkarrays.length-1]); 
        }
		map.addOverlay(mk=new GMarker(point));
		mkarrays.push(mk);			
		var polyline = new GPolyline([
								  oldpoint,
								  point
								], "#ff0000", 5);
		map.addOverlay(polyline);
		oldpoint= point;
    }
 }
    //<![CDATA[
function load() { 
      DWREngine.setActiveReverseAjax(true);
      startTimer();
      if (GBrowserIsCompatible()) {
	    map = new GMap2(document.getElementById("map"));
	    map.enableScrollWheelZoom(); 	       
		map.addControl(new GLargeMapControl());	
		//map.addControl(new GOverviewMapControl());
		map.addControl(new GMapTypeControl());
		//map.addControl(new GScaleControl());
		map.removeMapType( G_HYBRID_MAP );
		G_SATELLITE_MAP.getName = function(){ return '卫星图';};  
		map.addMapType(G_SATELLITE_MAP); 
		var point = new GLatLng(36.94,106.08);
        map.setCenter(point,4);
		//map.addOverlay(mk=new GMarker(point));
        //mkarrays.push(mk);
        GEvent.addListener(map, "maptypechanged", function() {
	  	xOffset= -xOffset;
	  	yOffset= -yOffset;
		map.setCenter(new GLatLng(map.getCenter().lat()+ xOffset , map.getCenter().lng()+ yOffset));
		for(var i=0;i<mkarrays.length;i++)
	  	{
	  	  var temppt=mkarrays[i].getPoint();
		  mkarrays[i].setPoint(point=new GLatLng(temppt.lat()+ xOffset , temppt.lng()+ yOffset))
		}	 	        
        });
        GEvent.addListener(map, "zoomend",function(){     
             zoom=map.getZoom();  
        }); 
    }
 }
    //]]>
function checkTime() 
{ 
	alert('定时器测试');
} 
    
function  onshowall()
{
  if (GBrowserIsCompatible()) {
    if(oldpoint != null)
     {
	      map.setCenter(oldpoint,15);
	 }     
  }
}
    
var timer;
function startTimer(){
   timer= setInterval("checkUsername()",5000); //5秒执行一次
}

function stopTimer(){
   clearInterval(timer);
}

function change(i) //通过参数i来选择路径
{
	path=new Array(2); //声明一个存放rsc路径的数组，数组大小为3
	path[0]="block1.jsp"; //frame里面要放的页面
	path[1]="block2.jsp";
	document.getElementById("newsblock").src=path[i]; //重新设置路径
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

  <body onload="load()">
<%@include file="header.jsp"%>
<div style="width:100%;height:600px;margin-top:0px;" >
<%if(logusername=="") { %><div align="center";><FONT size="5" face="宋体">对不起，您还未登陆,请先<a href="login.jsp">登陆</a></FONT></div>&nbsp; <%}else{%>
	<table style="width:100%;background-color:#F0F2F4;" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="2" style="vertical-align:top; BORDER-RIGHT: #466f93 1px solid;width:310px;">
			  <div style="padding:10px;">					 
				<ul id="tabnav">
					<li class="tab1"><a href="index.html">实时定位</a></li>
					<li class="tab2"><a href="index2.html">Tab Two</a></li>
				</ul>
                <frame id="newsblock" name="tab" src=""> </frame>					
				</div>
				
			</td>
			<td colspan="2" id="mapSizer" style="vertical-align:top">
				<div style="padding:0px">
					<table style="width:100%">
						<tr>
							<td class="title-black" style="border-bottom:solid 1px #466F93">
								当前显示<span id="mapTitle" class="title-blue"></span>
							</td>
						</tr>
					</table>
					位置
					<span class="ContainerHeader" id="divMapHeader"></span>
					<div class="ContainerBody" id="mapBody">
					<!-- 'position: relative' seems to be a must for VE -->
					<div id="map" style="float:right;width:100%;height:600px">  
				</div>
				</div>
			</td>
	</table>
	<table cellspacing="0" cellpadding="0" style="width:100%;">
		<tr>
			<td width="50%" style="width:50%;text-align:left; ">&nbsp;</td>
			<td width="50%" style="text-align:right;">&nbsp;</td>
		</tr>
	</table>
	     <%}%>
</div>
<%@include file="footer.jsp"%>
  </body>
</html>