package com.anygo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.net.formattransfer;
import com.net.impl.getgpsbycellpackage;
import com.anygo.R;

public class ShowTrack extends MapActivity {
    
    private MapView mMapView;
    private Button mGps;
    private Button mZin;
    private Button mZout;
    private Button mStreetview;
    private Button mTraffic;
    private Button mSat;
    
	private static MyApp appState;  
    private MapController mc;
    private GeoPoint mDefPoint;
    private String mDefCaption = "";
    private TelephonyManager tm;

    public void onCreate(Bundle icicle) {
    	try
    	{
	        super.onCreate(icicle);
		    appState = (MyApp)getApplicationContext(); 
	        setContentView(R.layout.show_track);
	        tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	        mMapView=(MapView) findViewById(R.id.mv);
	        // 允许通过触摸拖动地图
	        mMapView.setClickable(true);
	        // 当触摸地图时在地图下方会出现缩放按钮，几秒后就会消失
	        mMapView.setBuiltInZoomControls(true);
	        mc = mMapView.getController(); 
	        mc.setCenter(new GeoPoint(39971036,116314659));//设置地图中心
	        mc.setZoom(10);//设置缩放级别  
	        if(mMapView==null)
	        {
	          Toast.makeText(this, "hehe", Toast.LENGTH_SHORT);
	        } 
	        findViews();
    	}
    	catch(Exception ex)
        {
           Log.d("cao", ex.getMessage());
           Toast.makeText(this, "hehe", Toast.LENGTH_SHORT);
       }
    }

    private void findViews()
    {
        //mMapView = (MapView) findViewById(R.id.mv);
        mGps = (Button) findViewById(R.id.gps);
        mZin = (Button) findViewById(R.id.zin);
        mZout= (Button) findViewById(R.id.zout);
        mSat = (Button) findViewById(R.id.sat);
        mStreetview = (Button) findViewById(R.id.streetview);
        mTraffic= (Button) findViewById(R.id.traffic);
        mGps.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                centerOnGPSPosition();
            	/*new AlertDialog.Builder(ShowTrack.this)   
            	                .setTitle("标题")
            	                .setMessage("简单消息框")
            	                .setPositiveButton("确定", null)
            	                .show();*/

            }
            
        });
        mZin.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
            	/*new AlertDialog.Builder(ShowTrack.this)   
            	                .setTitle("标题")
            	                .setMessage("Zin点击")
            	                .setPositiveButton("确定", null)
            	                .show();*/
            	mc.zoomIn();


            }
            
        });
 
        mZout.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
            	/*new AlertDialog.Builder(ShowTrack.this)   
            	                .setTitle("标题")
            	                .setMessage("Zout点击")
            	                .setPositiveButton("确定", null)
            	                .show();*/
            	mc.zoomOut();

            }
            
        });
        
        mSat.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {            	
            	// 卫星模式为True on click
                mMapView.setSatellite(true); //卫星模式为True
                mMapView.setTraffic(false); //交通模式为False
                mMapView.setStreetView(false);//街景模式为False
            }
            
        });
        mTraffic.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // 交通模式为True on click
                mMapView.setSatellite(false); //卫星模式为False
                mMapView.setTraffic(true); //交通模式为True
                mMapView.setStreetView(false);//街景模式为False
            }
            
        });
        mStreetview.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // 街景模式为True on click
                mMapView.setSatellite(false); //卫星模式为False
                mMapView.setTraffic(false); //交通模式为False
                mMapView.setStreetView(true);//街景模式为True
            }
            
        });
    }
    
    private void centerOnGPSPosition() {
        String provider = "gps";
        /*LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location loc = lm.getLastKnownLocation(provider);
        if(loc==null)
        {
            mDefPoint = new GeoPoint((int) (22.6948331* 1000000),
                    (int) ( 113.8068808 * 1000000));
        }
        else
        {
            mDefPoint = new GeoPoint((int) (loc.getLatitude() * 1000000),
                (int) (loc.getLongitude() * 1000000));
        }*/
        //mDefPoint = new GeoPoint(22691036,1138014659);
        int lcd=-1;
        int lac=-1;
        int mcc=-1;
        int mnc=-1;
        try{
	        GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();
	        lcd = gcl.getCid();
	        lac = gcl.getLac();
	        mcc = Integer.valueOf(tm.getNetworkOperator().substring(0,3));
	        mnc = Integer.valueOf(tm.getNetworkOperator().substring(3,5));
	        if(lac==-1)
	        {
	          	Toast.makeText(ShowTrack.this, "获得基站信息失败！", Toast.LENGTH_SHORT).show();
	          	return ;
	        }
        } catch (NullPointerException ex) {
        	Toast.makeText(ShowTrack.this, "获得基站信息失败！", Toast.LENGTH_SHORT).show();
            return ;
        }
    	getgpsbycellpackage gpspack=new getgpsbycellpackage();
    	gpspack.setMnc(mnc);
    	gpspack.setMcc(mcc);
    	gpspack.setLac(lac);
    	gpspack.setLcd(lcd);
	    final String httpurl = appState.getHttpserverurl(); 
        mDefPoint=gpspack.getInput(httpurl,mnc,mcc,lac,lcd);
        if(mDefPoint==null)
        {
        	Toast.makeText(ShowTrack.this, "基站寻址失败，可能网络不通！", Toast.LENGTH_SHORT).show();
        	return;
	    }
        mDefCaption = "I'm Here.";
        mc.animateTo(mDefPoint);
        mc.setCenter(mDefPoint);
        //mc.setZoom(10);//设置缩放级别  
        // show Overlay on map.
        MyOverlay mo = new MyOverlay();
        mo.onTap(mDefPoint, mMapView);
        mMapView.getOverlays().add(mo);
    }

    GeoPoint getInput(int mnc,int mcc,int lac,int lcd){ 
    	GeoPoint retpoint=new GeoPoint((int) (22.6948331* 1000000),
                (int) ( 113.8068808 * 1000000));
        try
        {
        	getgpsbycellpackage gpspack=new getgpsbycellpackage();
        	gpspack.setMnc(mnc);
        	gpspack.setMcc(mcc);
        	gpspack.setLac(lac);
        	gpspack.setLcd(lcd);
	        byte[] tempbytes=gpspack.getpackage();
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
		        //byte[] buffer = new byte[1024];  
	           String strresult;
	           int recv_len = conn.getContentLength();//.getContentLength()此连接
	           int readBytes=0 ;
	           byte[] buffer = new byte[recv_len];
	           InputStream inputStream = conn.getInputStream();           
	           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	           int read = 0;
	           System.out.println("length=" + recv_len);
	           long startTime2=System.currentTimeMillis();
	           while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) 
	           {
	           byteArrayOutputStream.write(buffer, 0, read);                
	                       readBytes += read;
	           }
     			//byte[] prepbuf=recv_len;
     			if(buffer.length>0 && recv_len>2)
     			{
     				//AfxMessageBox(strHeader);
     				//prepbuf+=strlen("\r\n\r\n");//去掉两个回车
     				byte[] mytempbytes = new byte[4];
     				double lat=formattransfer.hBytesToInt(buffer)/1000000.;
     				System.arraycopy(buffer, 4, mytempbytes, 0, 4);
     				double lng=formattransfer.hBytesToInt(mytempbytes)/1000000.;
     				return new GeoPoint((int) (lat* 1000000),
     		                (int) ( lng * 1000000));
     			}
     			else 
     			{
     				return retpoint;
     			}
     		}
	        //Toast.makeText(Setting.this, R.string.setting_logintesetok, Toast.LENGTH_SHORT).show();		        
	        conn.disconnect();
        }
        catch(Exception ee)
        {
            //System.out.print("ee:"+ee.getMessage());
        	 Toast.makeText(ShowTrack.this, ee.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return retpoint;
    }
    
	private void sendSMS(String phoneNumber, String message) {
		// ---sends an SMS message to another device---
		SmsManager sms = SmsManager.getDefault();
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				ShowTrack.class), 0);
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
		Toast.makeText(ShowTrack.this, "发送成功", Toast.LENGTH_LONG).show();
	}
	
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }
    
    
    protected class MyOverlay extends Overlay {
        @Override
        public void draw(Canvas canvas, MapView mv, boolean shadow) {
            super.draw(canvas, mv, shadow);

            if (mDefCaption.length() == 0) {
                return;
            }

            Paint p = new Paint();
            int[] scoords = new int[2];
            int sz = 5;

            // Convert to screen coords
            Point myScreenCoords = new Point();
            mMapView.getProjection().toPixels(mDefPoint, myScreenCoords);
            // mMapView.set
            // mv.getPointXY(mDefPoint, scoords);
            // Draw point caption and its bounding rectangle
            scoords[0] = myScreenCoords.x;
            scoords[1] = myScreenCoords.y;
            p.setTextSize(14);
            p.setAntiAlias(true);

            int sw = (int) (p.measureText(mDefCaption) + 0.5f);
            int sh = 25;
            int sx = scoords[0] - sw / 2 - 5;
            int sy = scoords[1] - sh - sz - 2;
            RectF rec = new RectF(sx, sy, sx + sw + 10, sy + sh);

            p.setStyle(Style.FILL);
            p.setARGB(128, 255, 0, 0);

            canvas.drawRoundRect(rec, 5, 5, p);

            p.setStyle(Style.STROKE);
            p.setARGB(255, 255, 255, 255);
            canvas.drawRoundRect(rec, 5, 5, p);
            // canvas.d

            canvas.drawText(mDefCaption, sx + 5, sy + sh - 8, p);

            // Draw point body and outer ring
            p.setStyle(Style.FILL);
            p.setARGB(88, 255, 0, 0);
            p.setStrokeWidth(1);
            RectF spot = new RectF(scoords[0] - sz, scoords[1] + sz, scoords[0]
                    + sz, scoords[1] - sz);
            canvas.drawOval(spot, p);

            p.setARGB(255, 255, 0, 0);
            p.setStyle(Style.STROKE);
            canvas.drawCircle(scoords[0], scoords[1], sz, p);
        }
    }
}
