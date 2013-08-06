package com.anygo;

import android.content.BroadcastReceiver; 
import android.content.ComponentName;
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED"; 
	private static final String TAG = "BootBroadcastReceiver";
	@Override 
	public void onReceive(Context context, Intent intent) { 
	  if (intent.getAction().equals(ACTION)){ 

	   //开机启动服务SmsDealerService处理所有业务
	   Intent sayHelloIntent=new Intent(context,ActivityMain.class); 
	   sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	   Log.d(TAG, "onReceive");   
	   context.startActivity(sayHelloIntent); 
	   ComponentName comp = new ComponentName(context.getPackageName(),SmsDealerService.class.getName());
	   ComponentName service = context.startService(new Intent().setComponent(comp));  
	   /*Intent i = new Intent(context, SmsDealerService.class); 
	   ComponentName service = context.startService(i);*/
	   if (null == service) 
	   {              
		   Log.e(TAG,"Could not start service " );     
       }
	   else
	   {
	      Log.e(TAG,"start service succed" ); 
	   }
	  } 
	} 
	
}
