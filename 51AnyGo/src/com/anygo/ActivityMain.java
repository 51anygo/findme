package com.anygo;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMain extends ListActivity {
    
    private static final int MEUN_NEW = Menu.FIRST;
    private static final int MEUN_GO = MEUN_NEW + 1;
    private static final int MEUN_CON = MEUN_GO + 1;
    private static final int MEUN_SETTING = MEUN_CON + 1;
    private static final int MEUN_HELP = MEUN_SETTING + 1;
    private static final int MEUN_EXIT = MEUN_HELP + 1;
    long exitTime = 0;
    /* This Thread checks for Updates in the Background */ 
    /** Called when the activity is first created. */ 
    private Handler updateHandler = new  Handler(){
        @Override
        public void handleMessage(Message msg) {
        	checkVersion();
        }
    };

    
    class checkUpdateThread implements Runnable{
        public void run() { 
            try { 
            	  Message message = updateHandler.obtainMessage(); 
            	  MyApp appState = (MyApp)getApplicationContext();
            	  appState.getServerVer();
                  if (appState.getNewVerCode() >  appState.getOldVerCode()) {
                	  message.what = MyApp.UPDATE_NEEDTODATE;
                	  updateHandler.sendMessage(message); 
                  }
                        //发现新版本，提示用户更新
            } catch (Exception e) { 
            } 
        } 
    }; 
    
    /**
     * 检查更新版本
     */
    public void checkVersion(){
    	MyApp appState = (MyApp)getApplicationContext();
        if (appState.getNewVerCode() >  appState.getOldVerCode()) {
            //发现新版本，提示用户更新
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("软件升级")
                 .setMessage("发现新版本,建议立即更新使用.")
                 .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         //开启更新服务UpdateService
                         //这里为了把update更好模块化，可以传一些updateService依赖的值
                         //如布局ID，资源ID，动态获取的标题,这里以app_name为例
      /*                   Intent updateIntent =new Intent(ActivityMain.this, UpdateService.class);
                         updateIntent.putExtra("titleId",R.string.app_name);
                         startService(updateIntent);*/
                         Intent i = new Intent(ActivityMain.this, UpdateService.class);
                         i.putExtra("titleId",R.string.app_name);
                         ActivityMain.this.startService(i); 

                     }
                 })
                 .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
            alert.create().show();
        }else{
            //清理工作，略去
            cheanUpdateFile();
        }
    }
    
    protected void cheanUpdateFile()
    {
    	File updateFile = new File(MyApp.downloadDir,getResources().getString(R.string.app_name)+".apk");
    	if(updateFile.exists()){
    	   //当不需要的时候，清除之前的下载文件，避免浪费用户空间
    	   updateFile.delete();
    	}
    } 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        renderTracks();
        loadPhoneStatus();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
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
        //Intent intent = new Intent();
        //Bundle newb=new Bundle();
        // newb.putBoolean("bootcheckupdate", true);
        //intent.putExtras(newb);
        //intent.setClass(ActivityMain.this, UpdateActivity.class);
        //startActivity(intent);
        
        checkUpdateThread playObj = new checkUpdateThread();
        Thread playThread=new Thread(playObj);
        playThread.start();
        Intent i = new Intent(ActivityMain.this, SmsDealerService.class);
        ExitApplication.getInstance().addActivity(this); 
        ActivityMain.this.startService(i); 
    }

    private void renderTracks()
    {
        
    }
    
    private void init()
    {
        MyApp appState = (MyApp)getApplicationContext();
        int verCode = -1;
        String strtmp = this.getResources()  
			.getText(R.string.app_versionCode).toString();                
			verCode = Integer.parseInt(strtmp);
       
		appState.setOldVerCode(verCode);
        String verName = this.getResources()  
        .getText(R.string.app_versionName).toString();  
        appState.setOldVerName(verName);
    }


    private void loadPhoneStatus()
    {
      MyApp appState = (MyApp)getApplicationContext();
      TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
      String txtPhoneModel=Build.MODEL;
      //String txtPhoneNumber=phoneMgr.getLine1Number();
      String txtSdkVersion=Build.VERSION.SDK;//SDK版本号
      String txtOsVersion=Build.VERSION.RELEASE;//Firmware/OS 版本号
      appState.setTxtPhoneModel(txtPhoneModel);
      //appState.setTxtPhoneNumber(txtPhoneNumber);
      appState.setTxtSdkVersion(txtSdkVersion);
      appState.setTxtOsVersion(txtOsVersion);
      //txtPhoneModel.setText(Build.MODEL); //手机型号
      //txtPhoneNumber.setText(phoneMgr.getLine1Number());//本机电话号码
      //txtSdkVersion.setText(Build.VERSION.SDK);//SDK版本号
      //txtOsVersion.setText(Build.VERSION.RELEASE);//Firmware/OS 版本号
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        //menu.add(0, MEUN_NEW, 0, R.string.menu_new).setIcon(R.drawable.new_track);
        menu.add(0, MEUN_GO, 0, R.string.menu_go).setIcon(R.drawable.new_track);
        //okigt43menu.add(0, MEUN_CON, 0, R.string.menu_con).setIcon(R.drawable.con_track);
        menu.add(0, MEUN_SETTING, 0, R.string.menu_setting).setIcon(R.drawable.setting);
        menu.add(0, MEUN_HELP, 0, R.string.meun_help).setIcon(R.drawable.helps);
        menu.add(0, MEUN_EXIT, 0, R.string.meun_exit).setIcon(R.drawable.exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent();
        switch (item.getItemId())
        {
        case MEUN_NEW:
            intent.setClass(ActivityMain.this, NewTrack.class);
            startActivity(intent);
            return true;
        case MEUN_GO:
        	intent.setClass(ActivityMain.this, ShowTrack.class);
            startActivity(intent);
            return true;
        case MEUN_CON:
            return true;
        case MEUN_HELP:        	
             intent.setClass(ActivityMain.this, Help.class);
             startActivity(intent);
            return true;
        case MEUN_SETTING:
            intent.setClass(ActivityMain.this, Setting.class);
            startActivity(intent);
            return true;
        case MEUN_EXIT:
        	AppExit();
            return true;
        }
        return true;
    }

    private boolean AppExit()    {
    	ExitApplication.getInstance().exit();  
    	//android.os.Process.killProcess(android.os.Process.myPid()) ;
    	//System.exit(0);
    	return true;
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				AppExit();  
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
        Intent i = new Intent(ActivityMain.this, SmsDealerService.class);
    	ActivityMain.this.stopService(i);
		super.onDestroy();
	}      
    

}


