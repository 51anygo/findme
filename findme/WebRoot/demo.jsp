<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>任你行,实时定位好帮手！正在演示...</title> 
    <script src="http://ditu.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAEtb3NKNqyjJHHlorg5PYqBQbYjXhEd3FxeYcGHkXOpVqXj-KNBTXDfG3JLCBTO1sm-pPXzgGup8uDQ&hl=zh-CN"
      type="text/javascript"></script>
     <script type='text/javascript' src='dwr/interface/JUserCheck.js'></script>
  <script type='text/javascript' src='dwr/engine.js'></script>
   <script type='text/javascript' src='dwr/util.js'></script>
   <script type='text/javascript' src='js/checkinput.js'></script>
   <script type="text/javascript" src='js/calendar.js'></script>
   <script type="text/javascript">
    var oldgpsid=null;
    var lant;
    var longi;
    var lantoffset;
    var longioffset;
    var now;
    var speed;
    var angle;
    var map;
    var mkarrays=new Array(); 
    var oldpoint=null;  
	var zoom=12;
	var timer=null;
	var followmap=false;
	var querysdate=null; //查询开始时间
	var queryedate=null;       //查询结束时间
	var queryndate=null;  //当前查询时间
	var queryintime=5000;      //查询间隔
	var chinacenter=null;  //中国地图的中心
    var gpsAyyars = new Array();   
    var bsended=false;       
    var isendseconds=null;
    var sharegpsid=null;
    var name=null;
    var bgntime=null;
    var endtime=null;
    var userid=null;
    var memo=null; 
    var resetzoom=true;
       
     /*beijing
     var xOffset= 0.001381939;
     var yOffset= 0.006142259;
     */
     
     //shanghai
     //var xOffset= -0.001889737;
     //var yOffset= 0.004844069;
     
     //shenzhen
     var xOffset= 0;
     var yOffset= 0;
     DWREngine.setTimeout(70000);//设置一个超时时间 
     DWREngine.setErrorHandler(eh);//超时以后错误的处理函数 
	function eh(msg, ex) 
	{ 
		//alert(msg + ", date=" + ex.when); 
	} 
 
 function compareDate(d1, d2) {  // 时间比较的方法，如果d1时间比d2时间大，则返回true   
     return Date.parse(d1.replace(/-/g, "/")) > Date.parse(d2.replace(/-/g, "/"));
}   

  
function checkUsername()
{
    //alert(queryndate);
    var d=new Date();
    if(!bsended || (d.getTime()-isendseconds)>5000)
    {
		JUserCheck.getGpssharebytime(sharegpsid,queryndate,GetGpsCallback);
		bsended=true;
		d=new Date();
		isendseconds=d.getTime();
	}
}



function GetGpsHistory()
{
	    var fromdate=document.getElementById("fromdate").value;
	    if(compareDate(bgntime,fromdate) || compareDate(fromdate,endtime))
	    {
	   	  alert('请输入正确的开始时间,应该在右边轨迹时间段的范围内!');
	   	  return;
	   	} 
	   	var todate=document.getElementById("todate").value;
	    if(compareDate(todate,endtime) || compareDate(fromdate,todate))
	    {
	   	  alert('请输入正确的结束时间,应该在右边轨迹时间段的范围内!');
	   	  return;
	   	} 
	   	stopTimer();
	   	querysdate=fromdate;
	   	queryedate=todate;
	   	queryndate=fromdate;
	   	clearmk();
	   	checkUsername();
        //JUserCheck.getGpsshare(0,0,GetGpsShareCallback);
}
 
function GetGpsShareCallback(returnVal)
{
    if(returnVal==null || returnVal.id==null)
    {
       return;
    }
    else
    {
	    sharegpsid=returnVal.id;
		lant=returnVal.lat;
		longi=returnVal.lng;
		name=returnVal.name;
	    userid=returnVal.userid;
	    memo=returnVal.memo;
	    querysdate=returnVal.bgntime;
	    queryedate=returnVal.endtime;
	    bgntime=returnVal.bgntime;
	    endtime=returnVal.endtime;
	    document.getElementById("fromdate").value=querysdate;
	    document.getElementById("todate").value=queryedate;
	    document.getElementById("mapTitle").innerHTML=name+',时间段:'+bgntime+'-'+endtime;
		if(sharegpsid<0)
		{
		  return;
		}
		GetGpsHistory();
	}	
}

 
function clearmk()
{
    resetzoom=true;
    bsended=false;       
    isendseconds=null;
    gpsAyyars.length=0;
    oldgpsid=null;
    map.clearOverlays();
    map.setCenter(chinacenter,4);
    oldpoint=null;
    zoom=12;
}	
function GetGpsCallback(returnVal)
{
    bsended=false;
    var bOK=false;
    if(returnVal==null || returnVal.id==null || returnVal.id==oldgpsid)
    {
        bOK=false;
    }
    else
    {
	    oldgpsid=returnVal.id;
		lant=returnVal.lat;
		longi=returnVal.lng;
		lantoffset=returnVal.latoffset;
	    longioffset=returnVal.lngoffset;
		now=returnVal.updatetime;
		if(now<queryndate)
		{
		  //alert(now);
		  return;
		}
		if(compareDate(now,queryedate))
        {
           stopTimer();
           retrurn;
        }
		queryndate=now;
		speed=returnVal.speed;
		angle=returnVal.angle;
		//alert('timer');
		if(lant>0 && longi>0)
		{
		  bOK=true;
		  updatepos();
		}
	}
	
	var oldqueryintime=queryintime;
	//成功取得座标，则查询间隔改为500ms，否则还是5秒
	queryintime=1000;
	if(oldqueryintime!=queryintime || timer==null)
	{
	   stopTimer();
	   startTimer();
	}
	if(compareDate(queryndate,queryedate))
	{
		//alert('stopTimer');
	    stopTimer();
	    return;
	}
	//alert("queryndate:"+queryndate+",queryedate:"+queryedate);
}

function updatepos()
{
    var latmsg=document.getElementById("latmsg");
    if(latmsg!=null)
    {
   	  latmsg.innerHTML=formatFloat(lant, 6);
   	} 
    //JUserCheck.checkUser(usrname,GetUserExistCallback);
	var lngmsgtext=document.getElementById("lngmsg");
	if(lngmsgtext!=null)
	{
   	  lngmsgtext.innerHTML=formatFloat(longi, 6);
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
        var templat,templng;
       	templat=lant;
 	    templng=longi;
        //if (type==G_NORMAL_MAP)
        {
	       templat=templat-lantoffset;
	       templng=templng-longioffset
        }
        point = new GLatLng(templat, templng);
        if(document.getElementById("followSelected").checked)
        {          
             if(resetzoom)
             {
                map.setCenter(point,zoom);
                resetzoom=false;
             }
             else
             {
                map.panTo(point);
             }
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
								], "#00ff00", 2);
		map.addOverlay(polyline);
		gpsAyyars.push([lant,lantoffset,longi,longioffset]); 
		oldpoint= point;
    }
 }
    //<![CDATA[
function load() { 
	var browser=navigator.appName 
	var b_version=navigator.appVersion 
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
	{ 
	
	} 
	else
	{
		DWREngine.setActiveReverseAjax(true);
	}
      //DWREngine.setActiveReverseAjax(true);
      //startTimer();
      if (GBrowserIsCompatible()) {
	    map = new GMap2(document.getElementById("map"));
	    map.enableScrollWheelZoom(); 	       
		map.addControl(new GLargeMapControl());	
		//map.addControl(new GOverviewMapControl());
		map.addControl(new GMapTypeControl());
		//map.addControl(new GScaleControl());
		//map.removeMapType( G_HYBRID_MAP );
		G_SATELLITE_MAP.getName = function(){ return '卫星图';};  
		map.addMapType(G_SATELLITE_MAP); 
		chinacenter = new GLatLng(36.94,106.08);
        map.setCenter(chinacenter,4);
        GEvent.addListener(map, "maptypechanged", function() {
	  	xOffset= -xOffset;
	  	yOffset= -yOffset;
		map.clearOverlays();
			oldpoint=null; 	 
		   var type=map.getCurrentMapType();
		   var templat,templng;
		   //alert(gpsAyyars.length);
			for(var i=0;i<gpsAyyars.length;i++)
		  	{
		  	   templat=gpsAyyars[i][0];
		  	   templng=gpsAyyars[i][2];
		  	   //if (type==G_NORMAL_MAP)
		  	   {
		  	     templat=templat-gpsAyyars[i][1];
		  	     templng=templng-gpsAyyars[i][3];
		  	   }
		  	   point = new GLatLng(templat, templng);
			   var polyline = new GPolyline([
										  oldpoint,
										  point
										], "#00ff00", 2);
				map.addOverlay(polyline);
				if(i==0 || i==gpsAyyars.length-1)
				{
				  map.addOverlay(mk=new GMarker(point));
				}
				//alert(gpsAyyars[i][0]);
				oldpoint=point;
			}  	
			map.setCenter(new GLatLng(oldpoint.lat(), oldpoint.lng()));
			//startTimer();		    
        });
        GEvent.addListener(map, "zoomend",function(){     
             zoom=map.getZoom();  
        }); 
       JUserCheck.getGpsshare(0,0,GetGpsShareCallback);
    }
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
    
function startTimer(){
   timer= setInterval("checkUsername()",queryintime); //根据条件来，如果得到数据则加快查询速度，否则变慢
   
}

function stopTimer(){
   clearInterval(timer);
   timer=null;
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
<%if(false) { %><div align="center";><FONT size="5" face="宋体">对不起，您还未登陆,请先<a href="login.jsp">登陆</a></FONT></div>&nbsp; <%}else{%>
	<table style="width:100%;background-color:#F0F2F4;" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="2" style="vertical-align:top; BORDER-RIGHT: #466f93 1px solid;width:310px;">
			  <div style="padding:10px;">
					<div class="ContainerBody">
					  <div class="title-blue" style="border-bottom:solid 1px #466F93; padding:5px 0px">地图设置</div>
						<div style="padding:0px 5px">
						  <table height="34" width="295">
								<tr>
									<td valign="top"><br /></td><td width="10%" height="15" style="text-align:left">
										<input type="checkbox" id="followSelected" name="dummy" checked="checked"/>	<!-- checked="checked" -->
								  </td>
									<td valign="top"><br /></td><td valign="top"><br /></td><td width="47%" style="text-align:left" align="left">视图跟随<!--<label for="followSelected">Follow selected user</label>-->									</td>
									<td width="43%">
								    <br />
								  </td>
								</tr>
								
											
						  </table>
					  </div>
					</div>
			  <div style="padding:10px;">
					<div class="ContainerBody">
					  <div class="title-blue" style="border-bottom:solid 1px #466F93; padding:5px 0px">演示时间<br /></div>
						<div style="padding:0px 5px">
						  <table height="65" width="249">								
								<tr>
									<td valign="top"><br /></td>
									<td width="47%" style="text-align:left">日期从:<!--<label for="followSelected">Follow selected user</label>-->									</td>
									<td width="43%">
								    <input type="text" value="" maxlength="100" id="fromdate" onclick="SelectDate(this,'yyyy/MM/dd hh:mm:ss')" readonly="true" style="width:150px;cursor:pointer" />
								  </td><td valign="top"></td>
								</tr>
								<tr>
									<td valign="top"><br /></td>
									<td width="47%" style="text-align:left">日期到:<!--<label for="followSelected">Follow selected user</label>-->									</td>
									<td width="43%">
								    <input type="text" value="" maxlength="100" id="todate"  onclick="SelectDate(this,'yyyy/MM/dd hh:mm:ss')" readonly="true" style="width:150px;cursor:pointer" />
								  </td><td valign="top"><br /></td>
								</tr>
							   <tr>
									<td valign="top"><br /></td>			
									<td width="75%">
								    <input type="button" value="重新演示" id="todate"  onclick="GetGpsHistory()" readonly="true" style="width:80px;cursor:pointer" />
								  </td><td valign="top"><br /></td>
								</tr>
						  </table>
					  </div>
					</div>
				</div>
				  <div style="padding:10px;">
					<div class="ContainerBody">
					  <div class="title-blue" style="border-bottom:solid 1px #466F93; padding:5px 0px">
					    <div>
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td width="284" colspan="3" class="info_box_toppbg"><table cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                    <td id="userInfoTab" class="info_box_selected" style="white-space: nowrap;"><a href="javascript:void(0)" onclick="javascript:(function(){$('divUserInfoContent').setAttribute('className', 'visible'); $('divTrackInfoContent').setAttribute('className', 'invisible'); $('userInfoTab').setAttribute('className', 'info_box_selected'); $('trackInfoTab').setAttribute('className', 'info_box_unselected');    $('divUserInfoContent').setAttribute('class', 'visible'); $('divTrackInfoContent').setAttribute('class', 'invisible'); $('userInfoTab').setAttribute('class', 'info_box_selected'); $('trackInfoTab').setAttribute('class', 'info_box_unselected');})()" style="color:#466F93; font-weight:bold"><span class="tabs">用户信息<div align="left"><span id="username"></span></div></span></a> </td>
                                  </tr>
                              </table></td>
                            </tr>
                            <tr>
                              <td colspan="3" class="info_box_gray"><div id="div">
                                  <table cellpadding="0" cellspacing="0" style="width:100%">
                                    <tr style="background-color:White">
                                      <td colspan="2" style="border-bottom: black 1px solid; height: 20px;"><strong> 用 户 名:&nbsp; </strong></td>
                                      <td style="width: 20px; border-bottom: black 1px solid; height: 20px;">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td> 用 户 名 :&nbsp; </td>
                                      <td><strong>51anygo</strong></td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td>名 称 :&nbsp; </td>
                                      <td><strong>任你行<div align="left"><span id="name"></span></div></strong> </td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                      <td></td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td style="height: 19px"></td>
                                      <td style="height: 19px"></td>
                                      <td style="height: 19px"></td>
                                    </tr>
                                  </table>
                              </div>
							  
                                  <div id="div2" class="info_box_gray invisible">
                                    <table cellspacing="0" cellpadding="0" style="width:100%">
                                      <tr>
                                        <td id="trackInterfaceStatus" style="color: Red;"></td>
                                      </tr>
                                    </table>
                                  </div></td>
                            </tr>
                          </table>
				        </div>
						<div>
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td width="284" colspan="3" class="info_box_toppbg"><table cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                    <td id="userInfoTab" class="info_box_selected" style="white-space: nowrap;"><a href="javascript:void(0)" onclick="javascript:(function(){$('divUserInfoContent').setAttribute('className', 'visible'); $('divTrackInfoContent').setAttribute('className', 'invisible'); $('userInfoTab').setAttribute('className', 'info_box_selected'); $('trackInfoTab').setAttribute('className', 'info_box_unselected');    $('divUserInfoContent').setAttribute('class', 'visible'); $('divTrackInfoContent').setAttribute('class', 'invisible'); $('userInfoTab').setAttribute('class', 'info_box_selected'); $('trackInfoTab').setAttribute('class', 'info_box_unselected');})()" style="color:#466F93; font-weight:bold"></a> </td>
                                  </tr>
                              </table></td>
                            </tr>
                            <tr>
                              <td colspan="3" class="info_box_gray"><div id="div">
                                  <table cellpadding="0" cellspacing="0" style="width:100%">
                                    <tr style="background-color:White">
                                      <td colspan="2" style="border-bottom: black 1px solid; height: 20px;"><strong>位置信息:&nbsp; </strong></td>
                                      <td style="width: 20px; border-bottom: black 1px solid; height: 20px;">&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td> 纬 度:&nbsp; </td>
                                      <td><strong><div align="left"><span id="latmsg"></span></div></strong></td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td width="59">经 度:&nbsp; </td>
                                      <td><strong><div align="left"><span id="lngmsg"></span></div></strong> </td>
                                      <td></td>
                                    </tr>            
                                    <tr>
                                      <td height="30" style="height: 19px">速 度:&nbsp;</td>
                                      <td style="height: 19px"><strong><div align="left"><span id="spdmsg"></span></div></strong></td>
                                      <td style="height: 19px"></td>
                                    </tr>
									 <tr>
                                      <td height="30" style="height: 19px">方 向:&nbsp;</td>
                                      <td style="height: 19px"><strong><div align="left"><span id="headingmsg"></span></div></strong></td>
                                      <td style="height: 19px"></td>
                                    </tr>
                                    <tr>
                                      <td height="30" style="height: 19px">日 期:&nbsp;</td>
                                      <td style="height: 19px"><strong><div align="left"><span id="lastupdatedmsg"></span></div></strong></td>
                                      <td style="height: 19px"></td>
                                    </tr>
                                     </table>
                                      <table cellpadding="0" cellspacing="0" style="width:100%">
                                      <tr style="background-color:White">
                                      <td colspan="2" style="border-bottom: black 1px solid; height: 20px;"><strong>原理说明:&nbsp; </strong></td>
                                      <td style="width: 20px; border-bottom: black 1px solid; height: 20px;">&nbsp;</td>
                                    </tr>
                                      <td>通过安装在GPS导航仪或手机上的<a href="download.jsp" target="_blank" style="text-decoration:none;color:#0000ff">定位程序</a>(此程序同时支持虚拟串口功能，可虚拟多达5个GPS端口,不影响正常的GPS导航功能,请在程序上设置好您的用户名与密码。)，使用GPRS网络定时发送座标到网站并记录，用户可登陆网页实时查看或历史浏览被定位者的位置,效果即右图所示！</td>
                                   </table>
                              </div>
							  
                                  <div id="div2" class="info_box_gray invisible">
                                    <table cellspacing="0" cellpadding="0" style="width:100%">
                                      <tr>
                                        <td id="trackInterfaceStatus" style="color: Red;"></td>
                                      </tr>
                                    </table>
                                  </div></td>
                            </tr>
                          </table>
				        </div>
					  </div>
						</div>
				</div>
				
			</td>
			<td colspan="2" id="mapSizer" style="vertical-align:top">
				<div style="padding:0px">
					<table style="width:100%">
						<tr>
							<td class="title-black" style="border-bottom:solid 1px #466F93">
								当前轨迹:<FONT color="#ff0000"><span id="mapTitle" class="title-blue"></span></FONT>
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
		</tr>
	</table>
	     <%}%>
	    <%@include file="footer.jsp"%>  
</div>

  </body>
</html>