package com.anygo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle; 
import android.telephony.SmsMessage;

 

public class SMSReceiver extends BroadcastReceiver {
/*声明静态字符串,并使用android.provider.Telephony.SMS_RECEIVED作为Action为短信的依据*/
private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED"; 


@Override 
public void onReceive(Context context, Intent intent) 
{
  // TODO Auto-generated method stub 
  /*判断传来Intent是否为短信*/
  if (intent.getAction().equals(mACTION)) 
  { 
    /*构造一个字符串变量sb*/
    StringBuilder sb = new StringBuilder(); 
    /*接收由Intent传来的数据*/
    Bundle bundle = intent.getExtras(); 
    /*判断Intent是有资料*/
    if (bundle != null) 
    { 
      /* pdus为 android内建短信参数 identifier
       * 透过bundle.get("")并传pdus的对象*/
      Object[] myOBJpdus = (Object[]) bundle.get("pdus"); 
      
      /*构造短信对象array,并依据收到的对象长度来建立array的己弋*/
      SmsMessage[] messages = new SmsMessage[myOBJpdus.length];  
      
      for (int i = 0; i<myOBJpdus.length; i++) 
      {  
        messages[i] = SmsMessage.createFromPdu ((byte[]) myOBJpdus[i]);  
      }
      

      /* 将送来的短信安并自定义信息在StringBuilder当中 */  
      for (SmsMessage currentMessage : messages) 
      {
       // if (currentMessage.getMessageBody().contains("getlocate")) 
    	  { 

        /* 发信人的电话号码 */ 
          sb.append(currentMessage.getDisplayOriginatingAddress());  

        /* 取得传来短信的BODY */  
        //    sb.append(currentMessage.getDisplayMessageBody());
            String to=currentMessage.getOriginatingAddress();
            String content=currentMessage.getMessageBody();//
            String uppercontent=content.toUpperCase();
			//if("number".equals(sender)){ 
			//    abortBroadcast(); 
			//    }//如果不想让机主接收到某个号码的短信， 
            //可以取消这段注释， number 为指定的号码 
            //也可在此处给这个号码回复的内容。。。。。 
            //MyApp appState = (MyApp)context; 
  	       // final String httpurl = appState.getHttpserverurl(); 
            //if(uppercontent.contains("HELLO")) {
                /*设定让加Activity以一个新的task来执行*/	  
	            Intent i = new Intent(context, SmsDealerService.class); 
	            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle newb=new Bundle();
	            newb.putString("sms_caller", to);
	            i.putExtras(newb);
	            newb.putString("context",content);
	            i.putExtras(newb);
	            context.startService(i); 
          //  }
        }            
      }  
    }       

  }
} 
}