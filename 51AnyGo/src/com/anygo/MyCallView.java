package com.anygo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MyCallView extends Activity{
		int k=0;
		final int NUM=5;
		String incomNumber="888888";
		private Handler mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case my_event:{
					k++;
					if(k<NUM){
						ImageView i=new ImageView(MyCallView.this);
					i.setImageResource(R.drawable.icon);
					Toast toast = Toast.makeText(MyCallView.this, "未知地区", Toast.LENGTH_LONG);
					//toast.setView(i);
					toast.show();
					}else{
						finish();
					}
					
					break;
				}
				}
				super.handleMessage(msg);
			}
	    	
	    };
			Timer timer ;
		TimerTask t=new TimerTask(){
			
				@Override
				public void run() {
					
					Message m=mHandler.obtainMessage(my_event);
					mHandler.sendMessage(m);
				}
	        	
	        };
		private final int my_event=0;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			Intent i=this.getIntent();
			incomNumber=i.getStringExtra("mynumber");
			
			timer = new Timer(true);
	        timer.schedule(t, 0, 4000);
	        //finish();
		}
		
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}


		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			 timer.cancel();
			super.onDestroy();
		}
}
