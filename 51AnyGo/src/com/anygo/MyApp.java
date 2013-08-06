package com.anygo;

import org.json.JSONArray;
import org.json.JSONObject;

import com.anygo.db.DatabaseHelper;
import com.anygo.entity.MyConfig;
import com.anygo.service.IMyConfigService;
import com.anygo.service.MyConfigService;
import com.common.NetworkTool;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Log;

public class MyApp extends Application { 
	//public static final String PREFS_NAME = "MyPrefsFile";
	private String httpserverurl;
	private String smstag;
	//private String username;
	//private Settings settings;
	//private String password;
	private Boolean isemulator;
	private String txtPhoneModel;
	private String txtUpdateDateTime;
	private String txtTryUpdateTime;
	private String 	oldVerName;
	public String getOldVerName() {
		return oldVerName;
	}
	public void setOldVerName(String oldVerName) {
		this.oldVerName = oldVerName;
	}
	public int getOldVerCode() {
		return oldVerCode;
	}
	public void setOldVerCode(int oldVerCode) {
		this.oldVerCode = oldVerCode;
	}
	private String 	newVerName;
	private String 	txtPhonenumber;
    public static final Boolean ISEMULATOR = false;
    public static final Boolean ISLOCALHTTPSERVER = false;
	//public static final String UPDATE_SERVER = "http://www.51anygo.cn/downloads/android/";
    public String UPDATE_SERVER = "http://www.51anygo.cn/downloads/android/";
    public String REAL_HTTP_URL = "http://www.51anygo.cn:9080/q.do";
    public String LOCAL_URL_PREFIX = "http://192.168.0.103:8080/findme";
    //public String LOCAL_URL_PREFIX = "http://10.96.147.73:8080/findme";
    //public String TEST_URL_PREFIX = "http://www.51anygo.cn:9080";
    public static final String UPDATE_APKNAME = "51AnyGo.apk";
    public static final String UPDATE_VERJSON = "ver.json";
    //public static final String UPDATE_SAVENAME = "51AnyGo.apk";
    public static String downloadDir = "app/download/";
	public String getTxtPhonenumber() {
		//return settings.getString(SETTINGPHONENUM_KEY, "");
		String strnum=myconfig.getTxtPhonenumber();
		if(strnum==null)
		{
			strnum="";
		}
		return strnum;
	}
	public void setTxtPhonenumber(String txtPhonenumber) {
		Editor editor = settings.edit();
		editor.putString(SETTINGPHONENUM_KEY, txtPhonenumber);
		editor.commit();
	}
	private int oldVerCode;
	private int newVerCode;
	public String getTxtTryUpdateTime() {
		return txtTryUpdateTime;
	}
	public void setTxtTryUpdateTime(String txtTryUpdateTime) {
		this.txtTryUpdateTime = txtTryUpdateTime;
	}
	public String getTxtUpdateDateTime() {
		return txtUpdateDateTime;
	}
	public void setTxtUpdateDateTime(String txtUpdateDateTime) {
		this.txtUpdateDateTime = txtUpdateDateTime;
	}
	public String getTxtUpdateGpsPos() {
		return txtUpdateGpsPos;
	}
	public void setTxtUpdateGpsPos(String txtUpdateGpsPos) {
		this.txtUpdateGpsPos = txtUpdateGpsPos;
	}

	private String txtUpdateGpsPos;
	private static final String TAG = "MyApp";
    public static final String PREFS_NAME = "MyPrefsFile";
	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private static final String SMSTAG_KEY = "smstag";
	private static final String SETTINGGPSP_KEY = "settinggpsp";
	private static final String SETTINGGPSV_KEY = "settinggpsv";
	private static final String SETTINGMAPP_KEY = "settingmapp";
	private static final String SETTINGMAPV_KEY = "settingmapv";
	private static final String SETTINGPHONENUM_KEY = "settingphonenum";
	private static final String NOSLEEP_KEY = "nosleep";
	private static final String USERNAME_DEFAULT = "demo1";
	private static final String PASSWORD_DEFAULT = "demo1";
	//private static final String SMSTAG_DEFAULT = "hello";
	private static final String SMSTAG_DEFAULT = "你在哪";
	private static final int SETTINGGPSP_DEFAULT = 2;
	private static final int SETTINGMAPP_DEFAULT = 10;
	private static final int SETTINGGPSV_DEFAULT = 15;
	private static final int SETTINGMAPV_DEFAULT = 15;
    public static final int MYCONFIG_ID = 1;
    //下载状态  
	public final static int UPDATE_NEEDTODATE = 0;  
	private static final boolean NOSLEEP_DEFAULT = false;
	public boolean bAdd=false;
	private IMyConfigService myconfigService = null;
	public MyConfig myconfig = null;
	private SharedPreferences settings;
	public String getTxtPhoneModel() {
		return txtPhoneModel;
	}
	public void setTxtPhoneModel(String txtPhoneModel) {
		this.txtPhoneModel = txtPhoneModel;
	}
	public String getTxtSdkVersion() {
		return txtSdkVersion;
	}
	public void setTxtSdkVersion(String txtSdkVersion) {
		this.txtSdkVersion = txtSdkVersion;
	}
	public String getTxtOsVersion() {
		return txtOsVersion;
	}
	public void setTxtOsVersion(String txtOsVersion) {
		this.txtOsVersion = txtOsVersion;
	}

	private String txtPhoneNumber;
	private String txtSdkVersion;
	private String txtOsVersion;

	public Boolean getIsemulator() {
		return isemulator;
	}
	public void setIsemulator(Boolean isemulator) {
		this.isemulator = isemulator;
	}
	/*public String getSmstag() {
		return smstag;
	}
	public void setSmstag(String smstag) {
		this.smstag = smstag;
		setSmsTag(smstag);
	}*/
	public String getHttpserverurl() {
		return httpserverurl;
	}
	public void setHttpserverurl(String httpserverurl) {
		this.httpserverurl = httpserverurl;
	}
	 

 
	public boolean getServerVer() {
        try {
            String verjson = NetworkTool.getContent(UPDATE_SERVER
                    + UPDATE_VERJSON);
            JSONArray array = new JSONArray(verjson);
            if (array.length() > 0) {
                JSONObject obj = array.getJSONObject(0);
                try {
                    newVerCode = Integer.parseInt(obj.getString("verCode"));
                    newVerName = obj.getString("verName");
                } catch (Exception e) {
                    newVerCode = -1;
                    newVerName = "";
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }
	
 
	public String getNewVerName() {
		return newVerName;
	}
	public void setNewVerName(String newVerName) {
		this.newVerName = newVerName;
	}
	public int getNewVerCode() {
		return newVerCode;
	}
	public void setNewVerCode(int newVerCode) {
		this.newVerCode = newVerCode;
	}
	
   private DatabaseHelper DatabaseHelper = null;

	private DatabaseHelper getHelper() {
		if (DatabaseHelper == null) {
			DatabaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return DatabaseHelper;
	}
	
	public final static boolean isAndroidEmulator() {
        return Build.BRAND.toLowerCase().indexOf("generic") != -1
                        && Build.MODEL.toLowerCase().indexOf("sdk") != -1;
}
	
	@Override 
	public void onCreate() { 
		super.onCreate(); 
		myconfigService = new MyConfigService(getHelper().getMyConfigDao());
		myconfig =  myconfigService.findMyconfigByID(MYCONFIG_ID);
	    if(null==myconfig)
        {
        	myconfig=new  MyConfig();
        	myconfig.setaId(MYCONFIG_ID);
        	bAdd=true;
        }
		settings = this.getSharedPreferences(MyApp.PREFS_NAME, Context.MODE_PRIVATE); 
		//setIsemulator(false);
		//ISEMULATOR=isAndroidEmulator();
		setIsemulator(isAndroidEmulator());
		httpserverurl=REAL_HTTP_URL;
		if(getSmsTag().trim().equals(""))
		{
			setSmsTag("你在哪");
		}
		//this.username="demo1";
		//this.password="eddemo1";
		load();
	}
	
	protected void load(){	
		// Save user preferences. We need an Editor object to
		// make changes. All objects are from android.context.Context
		
		if(ISLOCALHTTPSERVER)
		{
		    setHttpserverurl(LOCAL_URL_PREFIX+"/q.do");
			UPDATE_SERVER = LOCAL_URL_PREFIX+"downloads/android/";
		}
		if(this.isemulator)
		{
			setSmsTag("HELLO");
		}
	  } 
	public void setSettingGpsP(int settinggpsp) {
		Editor editor = settings.edit();
		editor.putInt(SETTINGGPSP_KEY, settinggpsp);
		editor.commit();
	}
 
	
	public int getSettingGpsP() {
		return settings.getInt(SETTINGGPSP_KEY, SETTINGGPSP_DEFAULT);
	}
 
	public void setSettingGpsV(int settinggpsv) {
		Editor editor = settings.edit();
		editor.putInt(SETTINGGPSV_KEY, settinggpsv);
		editor.commit();
	}
	public int getSettingGpsV() {
		return settings.getInt(SETTINGGPSV_KEY, SETTINGGPSV_DEFAULT);
	}
 
	public void setSettingMapP(int settingmapp) {
		Editor editor = settings.edit();
		editor.putInt(SETTINGMAPP_KEY, settingmapp);
		editor.commit();
	}
 
	
	public int getSettingMapP() {
		return settings.getInt(SETTINGMAPP_KEY, SETTINGMAPP_DEFAULT);
	}
	
	public void setSettingMapV(int settingmapv) {
		Editor editor = settings.edit();
		editor.putInt(SETTINGMAPP_KEY, settingmapv);
		editor.commit();
	}
 
	
	public int getSettingMapV() {
		return settings.getInt(SETTINGMAPV_KEY, SETTINGMAPV_DEFAULT);
	}
	
	public void setNoSleep(boolean nosleep) {
		Editor editor = settings.edit();
		editor.putBoolean(NOSLEEP_KEY, nosleep);
		editor.commit();
	}
 
	
	public boolean getNoSleep() {
		return settings.getBoolean(NOSLEEP_KEY, NOSLEEP_DEFAULT);
	}
	/**
	 * Set the username in the preferences.
	 *
	 * @param username the username to save into prefs
	 */
	public void setUsername(String username) {
		Editor editor = settings.edit();
		editor.putString(USERNAME_KEY, username);
		editor.commit();
	}
 
	/**
	 * @return the username from the prefs
	 */
	public String getUsername() {
		//String strusername=settings.getString(USERNAME_KEY, USERNAME_DEFAULT);
		//return strusername;
		String strusername=myconfig.getUsername();
		if(strusername==null)
		{
			strusername=USERNAME_DEFAULT;
		}
		return strusername;
	}
 
	public void setSmsTag(String tmpsmstag) {
		smstag=tmpsmstag;
		Editor editor = settings.edit();
		editor.putString(SMSTAG_KEY, smstag);
		editor.commit();
	}
 

	public String getSmsTag() {
		//smstag=settings.getString(SMSTAG_KEY, SMSTAG_DEFAULT);
		smstag=myconfig.getSmstag();
		if(smstag==null)
		{
			smstag=SMSTAG_DEFAULT;
		}
		return smstag;
	}
 
	/**
	 *
	 * Set the password in the preferences.
	 *
	 * @param password password to save into prefs
	 */
	public void setPassword(String password) {
		Editor editor = settings.edit();
		editor.putString(PASSWORD_KEY, password);
		editor.commit();
	}
 
	/**
	 * @return the password stored in prefs
	 */
	public String getPassword() {
		//return settings.getString(PASSWORD_KEY, PASSWORD_DEFAULT);
		String strpassword=myconfig.getPassword();
		if(strpassword==null)
		{
			strpassword=PASSWORD_DEFAULT;
		}
		return strpassword;
	}
	
  
  public static String getAppName(Context context) {
          String verName = context.getResources()
          .getText(R.string.app_name).toString();
          return verName;
  }
} 
