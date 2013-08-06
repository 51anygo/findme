package com.anygo;

import com.net.impl.getaddrbycellpackage;
import com.net.impl.updategpsbycellpackage;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class ListenOutBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "PhoneStatReceiver";
	private static boolean incomingFlag = false;
	private static String incoming_number = null;

	private Context mycontext;
	private static WindowManager mywm;
	private static TextView mytv;
	public static String ADDR_CHANGED_ACTION = "com.anygo.action.ADDR_CHANGED_ACTION";  

	// public ListenOutBroadcastReceiver(Context context){
	// this.context = context;
	// }

	/*
	 * 
	 * http://www.eoeandroid.com/thread-8994-3-1.html
	 * 对于来电状态的变化，用户可以在上面的例子代码中，加入自己想要实现的逻辑。 来电分三种状态： CALL_STATE_RINGING：来电响铃
	 * CALL_STATE_OFFHOOK：摘机 CALL_STATE_IDLE：挂机
	 * 去电的话，这里只监听了电话拨打出去，至于拨打出去的电话，是否被接通，看来android的一些相关文档，
	 * 好像都没有这样一个可拦截的状态变化，不知道有没有人实现过去电状态变化的监听。 (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive :" + intent.toString());
		// 监听来电
		TelephonyManager telM = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		// 如果是拨打电话
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			incomingFlag = false;			
			mycontext = context;
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			startGetAddrService(context,intent,phoneNumber);
			showAddrWindow(context,intent);			

		} else {
			// 如果是来电
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:
				incomingFlag = true;// 标识当前是来电
				incoming_number = intent.getStringExtra("incoming_number");
				Log.i(TAG, "RINGING :" + incoming_number);
				/*Intent i = new Intent(context, SmsDealerService.class);
				Bundle newb = new Bundle();
				newb.putString("callout", incoming_number);
				i.putExtras(newb);
				context.startService(i);*/
				startGetAddrService(context,intent,incoming_number);
				showAddrWindow(context,intent);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if (incomingFlag) {
					Log.i(TAG, "incoming ACCEPT :" + incoming_number);
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if (incomingFlag) {
					Log.i(TAG, "incoming IDLE");
				}
				if (mywm != null && mytv != null) {
					try {
						mywm.removeView(mytv);
						mywm = null;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				break;
			}

		}
		String strOppWhere = intent.getStringExtra("mOppWhere");
		if(strOppWhere!=null && strOppWhere.length()>1)
		{
			if(mytv!=null){
				mytv.setText(strOppWhere);
			}
		}

	}
	void startGetAddrService(Context context, Intent intent,String phoneNumber){
			
		Log.i(TAG, "call OUT:" + phoneNumber);
		Intent i = new Intent(context, SmsDealerService.class);
		Bundle newb = new Bundle();
		newb.putString("bc_receiver", ListenOutBroadcastReceiver.ADDR_CHANGED_ACTION);
		newb.putString("callout", phoneNumber);
		i.putExtras(newb);
		context.startService(i);
	}
	void showAddrWindow(Context context, Intent intent){
		if (mywm == null) {
			mywm = (WindowManager) context.getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			//params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
			//params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
			//		| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	
			//params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			//params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.gravity = Gravity.LEFT | Gravity.TOP;
			params.x = 5;
			params.y = 5;
			params.format = PixelFormat.RGBA_8888;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
			| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.format = PixelFormat.TRANSLUCENT;
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
			
			mytv = new TextView(context);
			//mytv.setText("这是悬浮窗口，来电号码：");
			mytv.setText("");
			mywm.addView(mytv, params);
		}
	}
}
