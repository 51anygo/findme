package com.anygo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.net.impl.reguserpackage;
import com.anygo.R;
import com.anygo.R.string;
import com.anygo.db.DatabaseHelper;
import com.anygo.entity.MyConfig;
import com.anygo.service.IMyConfigService;
import com.anygo.service.MyConfigService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Setting extends OrmLiteBaseActivity<DatabaseHelper>	
implements OnClickListener {
	private IMyConfigService myconfigService = null;
	private MyConfig myconfig = null;
    private static final int MENU_MAIN = Menu.FIRST;
    private static final int MENU_NEW = MENU_MAIN + 1;
    private static final int MENU_BACK = MENU_NEW + 1;
    
    public static final String SETTING_INFOS = "SETTING_Infos";
    public static final String SETTING_GPS = "SETTING_Gps";
    public static final String SETTING_MAP = "SETTING_Map";
    public static final String SETTING_GPS_POSITON = "SETTING_Gps_p";
    public static final String SETTING_MAP_POSITON = "SETTING_Map_p";
    public static final String SETTING_NOSLEEP = "SETTING_NOSLEEP";
    public static final String SETTING_USERNAME ="SETTING_USERNAME";
    public static final String SETTING_PASSWORD = "SETTING_PASSWORD";
	private static MyApp appState;
    
    private Button mSubmitButton;
    private Button mUsrRegButton;
    private Spinner mGpsSpinner;
    private Spinner mMapSpinner;
    private EditText mUsrnameEditText;
    private EditText mPasswordEditText;
    private EditText mSmsTagEditText;
    private EditText mPhoneNumText;
    private CheckBox mNoSleep;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        appState = (MyApp)getApplicationContext();
		myconfigService = new MyConfigService(getHelper().getMyConfigDao());
		//myconfig =  myconfigService.findMyconfigByID(appState.MYCONFIG_ID);
        setContentView(R.layout.setting);
        setTitle(R.string.menu_setting);

        findViews();
        setListensers();
        restorePrefs();
        ExitApplication.getInstance().addActivity(this); 
    }

    private void storePrefs()
    {
    	/*boolean bAdd=false;
        if(null==myconfig)
        {
        	myconfig=new  MyConfig();
        	appState.myconfig.setaId(appState.MYCONFIG_ID);
        	bAdd=true;
        }*/
        appState.setUsername(mUsrnameEditText.getText().toString().trim());
        appState.myconfig.setUsername(mUsrnameEditText.getText().toString().trim());
        appState.setPassword(mPasswordEditText.getText().toString().trim()); 
        appState.myconfig.setPassword(mPasswordEditText.getText().toString().trim()); 
        appState.setTxtPhonenumber(mPhoneNumText.getText().toString().trim());   
        appState.myconfig.setTxtPhonenumber(mPhoneNumText.getText().toString().trim());   
        appState.setSmsTag(mSmsTagEditText.getText().toString().trim());
        appState.myconfig.setSmstag(mSmsTagEditText.getText().toString().trim());
        int gpspos=mGpsSpinner.getSelectedItemPosition();
        String gpsvalue=mGpsSpinner.getItemAtPosition(gpspos).toString();
        appState.setSettingGpsP(gpspos);
        try{
            int gpsv = Integer.parseInt(gpsvalue);
            appState.setSettingGpsV(gpsv);
                     }catch(Exception e){
                      return;
          }
         int mappos=mMapSpinner.getSelectedItemPosition();
         String mapvalue=mMapSpinner.getItemAtPosition(mappos).toString();
         appState.setSettingMapP(mappos);
         try{
             int mapv = Integer.parseInt(mapvalue);
             appState.setSettingMapV(mapv);
                      }catch(Exception e){
                       return;
           }
        appState.setSettingMapP(mMapSpinner.getSelectedItemPosition());
        appState.setNoSleep(mNoSleep.isChecked());
        appState.myconfig.setNosleep(mNoSleep.isChecked());
        if(appState.bAdd)
        	myconfigService.addMyConfig(appState.myconfig);  
        else
        	myconfigService.save(appState.myconfig);   
	}
    
    private void restorePrefs()
    {
        try
        {
        	mUsrnameEditText.setText(appState.getUsername());
	        //mUsrnameEditText.setText(appState.getUsername());
	        mPasswordEditText.setText(appState.getPassword());
	        //mPasswordEditText.setText(appState.getPassword());
            mSmsTagEditText.setText(appState.getSmsTag());
            //mSmsTagEditText.setText(appState.getSmsTag());
            mPhoneNumText.setText(appState.getTxtPhonenumber());
            //mPhoneNumText.setText(appState.getTxtPhonenumber());
	        int gpspos=appState.getSettingGpsP();
	        //int gpspos=appState.getSettingGpsP();
	        if(gpspos>mGpsSpinner.getCount())
	        {
	        	gpspos=1;
	        }
	        mGpsSpinner.setSelection(gpspos);
	        int mappos=appState.getSettingMapP();
	        if(mappos>mMapSpinner.getCount())
	        {
	        	mappos=1;
	        }
	        mMapSpinner.setSelection(mappos);
	        //mGpsSpinner.setSelection(appState.getSettingGpsP());
	        //mMapSpinner.setSelection(appState.getSettingMapP());
	        mNoSleep.setChecked(appState.getNoSleep()); 	       
        }
        catch(Exception ee)
        {
            //System.out.print("ee:"+ee.getMessage());
        	//Toast.makeText(Setting.this, ee.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Button.OnClickListener settingSubmit = new Button.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String gps = mGpsSpinner.getSelectedItem().toString();
            String map = mMapSpinner.getSelectedItem().toString();
            String smstag = mSmsTagEditText.getText().toString();
            if (smstag.trim().equals("") || smstag.length()<3 )
            {
                Toast.makeText(Setting.this, R.string.setting_smstaginputerror,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else if (gps.equals("") || map.equals(""))
            {
                Toast.makeText(Setting.this, R.string.setting_null, Toast.LENGTH_SHORT).show();
            }
            else {
                storePrefs();
                Toast.makeText(Setting.this, R.string.setting_ok, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Setting.this, ActivityMain.class);
                startActivity(intent);
            }
        }
    };
    
    public void onBackPressed() {
    	 String gps = mGpsSpinner.getSelectedItem().toString();
         String map = mMapSpinner.getSelectedItem().toString();
         String smstag = mSmsTagEditText.getText().toString();
 		 Resources resource = Setting.this.getResources();
		 // 取出数组资源
		 int[] intArrayTemp = resource.getIntArray(R.array.setting_smstaginputcharnum);
         if (smstag.trim().equals("") || smstag.length()< intArrayTemp[0] )
         {
             Toast.makeText(Setting.this, R.string.setting_smstaginputerror,
                     Toast.LENGTH_SHORT).show();
             return;
         }
         else if (gps.equals("") || map.equals(""))
         {
             Toast.makeText(Setting.this, R.string.setting_null, Toast.LENGTH_SHORT).show();
         }
         else {
             storePrefs();
             Toast.makeText(Setting.this, R.string.setting_ok, Toast.LENGTH_SHORT).show();
             Intent intent = new Intent();
             intent.setClass(Setting.this, ActivityMain.class);
             startActivity(intent);
         }
    }
    
    
    private void setListensers()
    {
        mSubmitButton.setOnClickListener(settingSubmit);
    }

    private void findViews()
    {
        mSubmitButton = (Button) findViewById(R.id.setting_submit);
        mUsrnameEditText = (EditText) findViewById(R.id.EditTextUsrName);
        mPasswordEditText = (EditText) findViewById(R.id.EditTextPassWord);
        mSmsTagEditText = (EditText) findViewById(R.id.EditTextSmsTag);
        mUsrRegButton = (Button) findViewById(R.id.ButtonUsrReg);
        mGpsSpinner = (Spinner) findViewById(R.id.setting_gps);
        mPhoneNumText = (EditText) findViewById(R.id.EditTextPhoneNum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gps, 
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGpsSpinner.setAdapter(adapter);
        
        mMapSpinner = (Spinner) findViewById(R.id.setting_map);
        mNoSleep = (CheckBox)  findViewById(R.id.setting_nosleep);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.map, 
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMapSpinner.setAdapter(adapter2);
        mUsrRegButton.setOnClickListener(new OnClickListener()
        {        	
            @Override
            public void onClick(View v)
            {      
            	 String usrname = mUsrnameEditText.getText().toString();
                 String password = mPasswordEditText.getText().toString();
                 if (usrname.trim().equals(""))
                 {
                     Toast.makeText(Setting.this, R.string.setting_usrnameinputerror,
                             Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (password.trim().equals(""))
                 {
                     Toast.makeText(Setting.this, R.string.setting_pwdinputerror,
                             Toast.LENGTH_SHORT).show();
                     return;
                 }  
                 getInput(usrname,password);
            }
            
        });
    }
 
    //http://topic.csdn.net/u/20101230/23/f4d2de78-0f09-4842-ad3e-a4bdae16da00.html?05352873538458467
    void getInput(String strusername,String strpassword){    
        try
        {
	        reguserpackage regpack=new reguserpackage();
	        regpack.setStrusername(strusername);
	        regpack.setStrpassword(strpassword);
	        byte[] tempbytes=regpack.getpackage();
  	        String httpurl = appState.getHttpserverurl(); 
	        URL url = new URL(httpurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setUseCaches(false);   
	        conn.setConnectTimeout(10000);
	        conn.setRequestMethod("POST");
	        OutputStream outStrm = conn.getOutputStream(); 
	        DataOutputStream objOutput = new DataOutputStream(outStrm);
	        //objOutput.writeObject(new String("this is a string..."));
            objOutput.write(tempbytes);
	        objOutput.flush();
	        objOutput.close();	      
	        //conn.connect();
	        int resCode = conn.getResponseCode();
	        if(resCode == HttpURLConnection.HTTP_OK)
            {  
	            // 鍙栧緱Response鍐呭 
	        	 InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "utf-8");
	        	 int i;  
	        	 String strresult  = "";  
                 // read  
                 while ((i = isr.read()) != -1) {  
                	 strresult = strresult + (char) i;  
                 }  
                 isr.close();
		        //Toast.makeText(Setting.this, R.string.setting_logintesetok, Toast.LENGTH_SHORT).show();		        
		        Toast.makeText(Setting.this, strresult, Toast.LENGTH_SHORT).show();
	        } 
	        else
	        {
	        	 Toast.makeText(Setting.this, R.string.setting_logintesetfail, Toast.LENGTH_SHORT).show();
	        }
	        conn.disconnect();
        }
        catch(Exception ee)
        {
            //System.out.print("ee:"+ee.getMessage());
        	 Toast.makeText(Setting.this, ee.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_MAIN, 0, R.string.menu_main).setIcon(R.drawable.icon);
        menu.add(0, MENU_NEW, 0, R.string.menu_new).setIcon(R.drawable.new_track);
        menu.add(0, MENU_BACK, 0, R.string.menu_back).setIcon(R.drawable.back);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent();
        switch (item.getItemId())
        {
        case MENU_NEW:
            intent.setClass(Setting.this, NewTrack.class);
            startActivity(intent);
            return true;
        case MENU_BACK:
            finish();
            break;
        case MENU_MAIN:
            intent.setClass(Setting.this, ActivityMain.class);
            startActivity(intent);
            return true;
        default:
            break;
        }
        return true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        storePrefs();
    }

    @Override
	public void onClick(View v) {
		/*msg = "";
		switch (v.getId()) {
		case R.id.btnRegAccount:
			accountID++;
			Account account = new Account("�ͻ�" + accountID);
			msg = accountService.reg(account);
			break;
		default:
			break;
		}*/
		//tvMsg.setText(msg);
	}
    
}
