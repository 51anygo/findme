package com.anygo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class TimeService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		//创建一个AppWidgetManager的实例
		AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(this);
		//获得RemoteViews
		RemoteViews mRemoteViews = MyTimeWidget.getTimeView(this);
		int[] appIds = mAppWidgetManager.getAppWidgetIds(new ComponentName(this, MyTimeWidget.class));
		mAppWidgetManager.updateAppWidget(appIds, mRemoteViews);
		
		long now = System.currentTimeMillis();
		long unit = 1000;
		PendingIntent mPendingIntent = PendingIntent.getService(this, 0, intent, 0);
		AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, now + unit, mPendingIntent);
	}
}
