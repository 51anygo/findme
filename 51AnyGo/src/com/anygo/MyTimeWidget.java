package com.anygo;

import java.util.Calendar;

import com.anygo.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

public class MyTimeWidget extends AppWidgetProvider {

	private static String[] weekdays = {"星期日", "星期一", "星期二", "星期三", 
			"星期四", "星期五", "星期六"};
	private static MyApp appState;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		//启动更新Widget的Service
		context.startService(new Intent(context, TimeService.class));
	}
	
	static RemoteViews getTimeView(Context context) {
	    appState = (MyApp)context.getApplicationContext();
	    String txtupdatetime=appState.getTxtUpdateDateTime();
	    if(txtupdatetime==null)
	    {
	    	txtupdatetime="位置暂未更新！";
	    }
	    String txtupdategps=appState.getTxtUpdateGpsPos();
	    if(txtupdategps==null)
	    {
	    	txtupdategps="";
	    }
	    String txttryupdategps=appState.getTxtTryUpdateTime();
	    if(txttryupdategps==null)
	    {
	    	txttryupdategps="";
	    }
		Time mTime = new Time();
		//时间设置为系统当前时间
		mTime.setToNow();
		int mhour = mTime.hour;
		String apm = "";
		if (mhour>=0 && mhour<12) {
			apm = "AM";
		}else if (mhour>=12) {
			apm = "PM";
		}
		//时间字符串格式化
		CharSequence mCharSequence = DateFormat.format("hh:mm:ss", mTime.toMillis(false))+" "+apm;
		
		int myear = mTime.year;
		int mmonth = mTime.month+1;//月份+1是一年中的第几个月
		int mmonthday = mTime.monthDay;//一月中的日期
		int mweekday = mTime.weekDay;

		String mDate = format(myear)+"-"+format(mmonth)+"-"+format(mmonthday);
		
		//时间显示在Widget上
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mywidget);
		//mRemoteViews.setTextViewText(R.id.widget_system_date, mDate);
		//mRemoteViews.setTextViewText(R.id.widget_system_time, mCharSequence);
		mRemoteViews.setTextViewText(R.id.widget_system_date, txtupdatetime);		
		mRemoteViews.setTextViewText(R.id.widget_system_time, txtupdategps);
		mRemoteViews.setTextViewText(R.id.widget_system_weekday, txttryupdategps);
		//mRemoteViews.setTextViewText(R.id.widget_system_weekday, weekdays[mweekday]);
		return mRemoteViews;
	}
	//format the string of time
	private static String format(int t) {
		String s = ""+t;
		if (s.length()==1) {
			s = "0" + s;
		}
		return s;
	}
}
