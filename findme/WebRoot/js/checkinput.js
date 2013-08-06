
function checkEmail(s){  
 if(!/^\w+@\w+(\.\w+){1,3}$/.test(s))
 {
 return "请输入合理的邮箱地址！如:abc_123@gmail.com";
 }
 return "";
}

 function compareDate(d1, d2) {  // 时间比较的方法，如果d1时间比d2时间大，则返回true   
     return Date.parse(d1.replace(/-/g, "/")) > Date.parse(d2.replace(/-/g, "/"));
}   

function checkUser(s){  
 if(!/^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){4,19}$/.test(s))
 {
   return "用户名应该是5~15个字母、数字和下划线";
 }
 return "";
}

function isPhonenum(s){  
 return(/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/.test(s));
}

function isMobilenum(s){  
 return(/^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/.test(s));
}

function isURL(s){  
 return(/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/.test(s));
}

function isPID(s){  
 return(/^\d{15}(\d{2}[A-Za-z0-9])?$/.test(s));
}

function isCurrency(s){  
 return(/^\d+(\.\d+)?$/.test(s));
}

function isNum(s){  
 return(/^\d+$/.test(s));
}

function isZip(s){  
 return(/^[1-9]\d{5}$/.test(s));
 }
 
 function isQQ(s){
  return (/^[1-9]\d{4,8}$/.test(s));
 }
 
 function isRealnum(s){
  return (/^[-\+]?\d+$/.test(s));
 }
 
  function isEnglish(s){
  return (/^[A-Za-z]+$/.test(s));
 }
 
 function isChinese(s){
  return (/^[\u0391-\uFFE5]+$/.test(s));
 }
 
 function checkSimplePassword(s){
 if(s=="")
 {
   return "密码不能为空!";
 }  
 if(!/([\d\D]*){4,19}$/.test(s))
 {
   return "密码应该是5~15个任意字符!";
 }
 return "";
}
 
  function isComplexPassword(s){
  return (/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(s));
 }
 
function formatFloat(src, pos)
{
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
}


 /**  
 * 对Date的扩展，将 Date 转化为指定格式的String  
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符  
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)  
 * eg:  
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423  
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04  
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04  
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04  
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18  
 */  
 Date.prototype.pattern=function(fmt) {   
 if (fmt == null) fmt = "yyyy/MM/dd HH:mm:ss.SSS";
    var o = {   
    "M+" : this.getMonth()+1, //月份   
    "d+" : this.getDate(), //日   
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时   
    "H+" : this.getHours(), //小时   
    "m+" : this.getMinutes(), //分   
    "s+" : this.getSeconds(), //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S" : this.getMilliseconds() //毫秒   
    };   
    var week = {   
    "0" : "\u65e5",   
    "1" : "\u4e00",   
    "2" : "\u4e8c",   
    "3" : "\u4e09",   
    "4" : "\u56db",   
    "5" : "\u4e94",   
    "6" : "\u516d"  
    };   
    if(/(y+)/.test(fmt)){   
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
    }   
    if(/(E+)/.test(fmt)){   
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);   
    }   
    for(var k in o){   
        if(new RegExp("("+ k +")").test(fmt)){   
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
        }   
    }   
    return fmt;   
}  

