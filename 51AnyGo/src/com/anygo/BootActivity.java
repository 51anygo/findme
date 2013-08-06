package com.anygo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity; 
import android.os.Bundle; 
import android.util.Log;
import android.widget.TextView; 
public class BootActivity extends Activity {
	@Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 


        TextView tv = new TextView(this); 
        tv.setText("Hello. I started!"); 

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
        setContentView(tv); 
    } 

}
