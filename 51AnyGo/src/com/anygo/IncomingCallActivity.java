package com.anygo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle; 
import android.widget.TextView; 
import android.widget.Toast;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class IncomingCallActivity extends Activity {
	private String strincomingnum;
	private final LocationListener locationListener = new LocationListener() {   
        public void onLocationChanged(Location location) { 
        	if (location != null) {   
                String latitude = Double.toString(location.getLatitude());
                String longitude = Double.toString(location.getLongitude());
                String altitude = Double.toString(location.getAltitude());
            }   
   }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
  };

	private void sendSMS(String phoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				IncomingCallActivity.class), 0);
		// if message's length more than 70 ,
		// then call divideMessage to dive message into several part ,and call
		// sendTextMessage()
		// else direct call sendTextMessage()
		if (message.length() > 70) {
			ArrayList<String> msgs = sms.divideMessage(message);
			for (String msg : msgs) {
				sms.sendTextMessage(phoneNumber, null, msg, pi, null);
			}
		} else {
			sms.sendTextMessage(phoneNumber, null, message, pi, null);
		}
		Toast.makeText(IncomingCallActivity.this, "发送成功", Toast.LENGTH_LONG).show();	
		
	}
	@Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
       // sendSMS("68411","888888888");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
            //TODO
             
             Writer result = new StringWriter();         
             PrintWriter printWriter = new PrintWriter(result);         
             ex.printStackTrace(printWriter);         
             String stacktrace = result.toString(); 
             
             Log.d("TEMP1","_____"+ex.toString()+" "+stacktrace);
             
             //Log.d("TEMP1",ex.getLocalizedMessage());
             //Toast.makeText(readlog.this, "异常关闭", Toast.LENGTH_SHORT).show();
             finish();
            }
              
           });
        String strmsg;		
        Bundle extras=getIntent().getExtras();    
        if(extras!=null)
        	strincomingnum=extras.getString("incoming_number");
        LocationManager locationManager = (LocationManager) 
        getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  
        10000, 0, locationListener); 
        //TextView tv = new TextView(this); 
        //tv.setText("Hello. I started!"); 
        //setContentView(tv); 
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) 
        {  
	    	 double dlat=location.getLatitude();
	    	 double dlng=location.getLongitude();
	    	 double dalt=location.getAltitude();
	    	 DecimalFormat  df = new DecimalFormat("#,##0.00"); 
	         df.format(dlng);
	         df.format(dlat); 
	         df.format(dalt);
	         String strlatitude = Double.toString(dlat);
	         String strlongitude = Double.toString(dlng);
	         String straltitude = Double.toString(dalt);         
	         //strmsg="经度:"+strlongitude+"纬度:"+strlatitude+"高度:"+straltitude;
	         //sendSMS("68411",strmsg);
	         //Toast.makeText(IncomingCallActivity.this, strmsg, Toast.LENGTH_LONG).show();
        }
        else
        {
        	//Toast.makeText(IncomingCallActivity.this, "无法取得GPS", Toast.LENGTH_LONG).show();
        }
    } 

}
