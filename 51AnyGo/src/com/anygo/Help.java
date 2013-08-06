package com.anygo;

import com.anygo.R;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class Help extends Activity
{
    private Button mChkUpdateButton;
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
                         Intent i = new Intent(Help.this, UpdateService.class);
                         i.putExtra("titleId",R.string.app_name);
                         Help.this.startService(i); 

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        setTitle(R.string.meun_help);
        mChkUpdateButton = (Button) findViewById(R.id.helps_button1);
        mChkUpdateButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(Help.this, R.string.setting_pwdinputerror,
                //        Toast.LENGTH_SHORT).show();
                //return;
               /* Intent intent = new Intent();
                Bundle newb=new Bundle();
	            newb.putBoolean("bootcheckupdate", false);
	            intent.putExtras(newb);
                intent.setClass(Help.this, UpdateActivity.class);
                startActivity(intent);*/
            }
        });
        ExitApplication.getInstance().addActivity(this); 
    }
    


    
}
