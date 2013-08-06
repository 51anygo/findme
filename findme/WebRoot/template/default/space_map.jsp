<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jchome.jsprun.com/jch" prefix="jch"%>
<jsp:include page="${jch:template(sConfig, sGlobal, 'headermap.jsp')}"/>

<c:choose>
<c:when test="${space.self}">
<!-- <h2 class="title"><img src="image/app/topic.gif">我的地图</h2> -->
</c:when>
<c:otherwise>
<jsp:include page="${jch:template(sConfig, sGlobal, 'space_menu.jsp')}"/>
</c:otherwise>
</c:choose>
<div style="width:100%;height:600px;margin-top:0px;" >

	<table style="width:100%;background-color:#F0F2F4;" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="2" style="vertical-align:top; BORDER-RIGHT: #466f93 1px solid;width:20%;">
			  <div style="padding:10px;">
					<div class="ContainerBody">
					  <div class="title-blue" style="border-bottom:solid 1px #466F93; padding:5px 0px">地图设置</div>
						<div style="padding:0px 5px">
						  <table height="30" style="width:100%">
								<tr>
									<td width="20%" height="40" style="text-align:left">
										<input type="checkbox" id="followSelected" name="dummy" checked="checked"/>	视图跟随<!-- checked="checked" -->
								  </td>			
									
								</tr>
								<tr><td valign="top" height="40">  <input type="checkbox" id="onlygps" name="dummy" />只看GPS座标
				                <tr>
								<td width="100%" height="20">
										<input type="button" onclick="onshowall()" id="showAll" value='最新位置' />
								  </td>
								  </tr>
						  </table>
					  </div>
					</div>
				</div>
				
				  <div style="padding:0px;">
					<div class="ContainerBody">
					  <div class="title-blue" style="border-bottom:solid 1px #466F93; padding:0px 0px">
					    <div>
                          <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td width="20" colspan="3" class="info_box_toppbg"><table cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                    <td id="userInfoTab" class="info_box_selected" style="white-space: nowrap;"><a href="javascript:void(0)" onclick="javascript:(function(){$('divUserInfoContent').setAttribute('className', 'visible'); $('divTrackInfoContent').setAttribute('className', 'invisible'); $('userInfoTab').setAttribute('className', 'info_box_selected'); $('trackInfoTab').setAttribute('className', 'info_box_unselected');    $('divUserInfoContent').setAttribute('class', 'visible'); $('divTrackInfoContent').setAttribute('class', 'invisible'); $('userInfoTab').setAttribute('class', 'info_box_selected'); $('trackInfoTab').setAttribute('class', 'info_box_unselected');})()" style="color:#466F93; font-weight:bold"><span class="tabs">信息<div align="left"><span id="username"></span></div></span></a> </td>
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
                                      <td><strong>${sNames[sGlobal.supe_uid]}</strong></td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td>名 称 :&nbsp; </td>
                                      <td><strong><div align="left"><span id="name"></span></div></strong> </td>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td>历史轨迹(<FONT color="#ff0000">新</FONT>)：</td>
                                      <td><a href="viewhis.jsp" target="_blank">查看</a></td>
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
                                      <td>经 度:&nbsp; </td>
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
                                      <td>通过安装在GPS导航仪或手机上的<a href="download.jsp" target="_blank" style="text-decoration:none;color:#0000ff">定位程序</a>，使用GPRS网络定时发送座标到网站并记录，用户可登陆网页实时查看或历史浏览被定位者的位置,效果即右图所示！</td>
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
		</tr>
	</table>

<script type="text/javascript">
//彩虹炫
var elems = selector('div[class~=magicflicker]'); 
for(var i=0; i<elems.length; i++){
	magicColor(elems[i]);
}
</script>


<jsp:include page="${jch:template(sConfig, sGlobal, 'footer.jsp')}"/>