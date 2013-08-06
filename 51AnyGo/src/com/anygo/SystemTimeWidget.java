package com.anygo;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;

public class SystemTimeWidget extends Activity implements SharedPreferences {

	private static String PREF = "MySystemTimeWidget";
	private static String TITLE = "SystemTimeWidget";
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	public Editor edit() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, ?> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getBoolean(String key, boolean defValue) {
		// TODO Auto-generated method stub
		return false;
	}

	public float getFloat(String key, float defValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(String key, int defValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLong(String key, long defValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getString(String key, String defValue) {
		// TODO Auto-generated method stub

		String title = ""; 
		if (key.equals("SystemTimeWidget")) {
			title = this.TITLE;
		}
		return title;
		
//		return null;
	}

	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		// TODO Auto-generated method stub
		
	}
	

   
}