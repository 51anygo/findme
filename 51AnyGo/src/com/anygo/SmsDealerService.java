package com.anygo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.anygo.ListenOutBroadcastReceiver;
import com.anygo.MyApp;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.net.impl.getaddrbycellpackage;
import com.net.impl.getaddrbygpspackage;
import com.net.impl.updategpsbycellpackage;
import com.net.impl.updategpspospackage;
import com.net.impl.updateiamcallingpackage;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import java.sql.SQLException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;
import com.anygo.db.DatabaseHelper;
import com.anygo.db.dao.MyCellPosDao;
import com.anygo.entity.MyCellPos;
import com.j256.ormlite.dao.Dao;

//http://www.wscxy.com/nuaa/article.asp?id=119
public class SmsDealerService extends Service {
	private static final String TAG = "SmsDealerService";
	private static TelephonyManager tm;
	private static Map<String, String> mysmsmap;
	private static Map<String, String> mycallingmap;
	// private MediaPlayer mediaPlayer = null;
	private Intent intent2bc = null;
	private Bundle bundle2bc = null;
	private String audioPath = null;
	private String bc_receiver = null;

	private static MyApp appState;
	// 定时提交GPS包线程
	newThread playObj = null;
	Thread playThread = null;
	// 电话包的处理线程
	callingThread callingObj = null;
	Thread callingThread = null;
	// 短信包的处理线程
	smsThread smsObj = null;
	Thread smsThread = null;
	WatchDogThread watchdogObj = null;
	Thread watThread = null;
	MediaPlayer mediaPlayer = null;
	long dogfeedms = 0;
	int lcd = -1;
	int lac = -1;
	int mcc = -1;
	int mnc = -1;
	int k = 0;
	private final int my_event = 0;
	final int NUM = 20000;
	String mIncomNumber = "";
	String mOppWhere = "";
	private TimerTask t;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case my_event: {
				sendBC4UpdateUI();
				break;
			}
			}
			super.handleMessage(msg);
		}

	};

	private DatabaseHelper DatabaseHelper = null;

	private DatabaseHelper getHelper() {
		if (DatabaseHelper == null) {
			DatabaseHelper = OpenHelperManager.getHelper(this,
					DatabaseHelper.class);
		}
		return DatabaseHelper;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (DatabaseHelper != null) {
			OpenHelperManager.releaseHelper();
			DatabaseHelper = null;
		}
	}

	private void resetTimer() {
		try {
			mTimerTask.cancel();
			mTimerTask = null;
			mTimer.cancel();
			mOppWhere = "";
			return;
		} catch (Exception e) {
			e.printStackTrace();

		}
		Log.i(TAG, "mTimerTask cancel");
	}

	private Timer mTimer;
	private MyTimerTask mTimerTask;

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			int nState = tm.getCallState();
			Log.i(TAG, "mTelManager.getCallState = " + nState);
			if (nState == TelephonyManager.CALL_STATE_IDLE) {
				resetTimer();
			}
			int icount = 0;

			while (mOppWhere.length() < 1) {
				mOppWhere = getHisAddress(mIncomNumber);
				if (mOppWhere.length() > 1) {
					if (0 == sendBC4UpdateUI()) {
						return;
					}
					mOppWhere = "";
				}
				// Toast toast = Toast.makeText(SmsDealerService.this,
				// "update where i am!", Toast.LENGTH_LONG);
				// toast.show();
				icount++;
				// 重试3次，就退出
				if (icount > 3)
					break;
			}
			// Message m=mHandler.obtainMessage(my_event);
			// mHandler.sendMessage(m);
		}
	}

	private int sendBC4UpdateUI() {
		intent2bc = new Intent(bc_receiver);
		// bc_receiver前面已有定义，是从Activity传过来的
		// 如果缺少下面这句，关掉再重新打开播放器里点“停止”并不能停掉
		// intent2bc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		bundle2bc = new Bundle();
		bundle2bc.putString("mOppWhere", mOppWhere);
		// 把flag传回去
		intent2bc.putExtras(bundle2bc);
		try {
			sendBroadcast(intent2bc);// 发送广播　　
			// 发送后，在Activity里的updateUIReceiver的onReceiver()方法里就能做相应的更新界面的工作了
			Log.i(TAG, "sendBroadcast(intent2bc)");
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mysmsmap = new HashMap<String, String>();
		// mycallingmap=new HashMap<String,String>();
		mycallingmap = new HashMap<String, String>();
		// Log.i(TAG,"onCreate");
		tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		appState = (MyApp) getApplicationContext();
		// playObj=new newThread();
		// playThread=new Thread(playObj);
		// playThread.start();

		callingObj = new callingThread();
		callingThread = new Thread(callingObj);
		callingThread.start();

		smsObj = new smsThread();
		smsThread = new Thread(smsObj);
		smsThread.start();

		// watchdogObj=new WatchDogThread();
		// watThread=new Thread(watchdogObj);
		// watThread.start();

		Log.d(TAG, "service onCreate");
		// mediaPlayer=MediaPlayer.create(SmsDealerService.this,R.raw.friend);
		if (mediaPlayer == null) {
			// return;
		}
	}

	public void pruduce(List basket, String newcallcoming) {
		int basketLimit = 10;
		synchronized (basket) {
			while (basket.size() == basketLimit) {

				System.out.println("basket is full");
				try {
					basket.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			// basket.add(basket.size()+1);
			basket.add(newcallcoming);
			System.out.println("put " + basket.size());
			basket.notifyAll();
		}

	}

	public String consume(List basket) {
		String getcallnum = "";
		synchronized (basket) {

			while (basket.size() == 0) {

				System.out.println("the bask is empty");
				try {
					basket.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			getcallnum = basket.get(basket.size() - 1).toString();
			// System.out.println("get "+basket.get(basket.size()-1));
			basket.remove(basket.size() - 1);
			basket.notifyAll();
		}
		return getcallnum;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "onStart onCreate");
		Bundle bunde = intent.getExtras();
		if (bunde == null || bunde.equals("")) {
			return;
		}
		String content = bunde.getString("context");
		String to = bunde.getString("sms_caller");
		if (content != null && !content.equals("") && to != null
				&& !to.equals("")) {
			String uppercontent = content.toUpperCase();
			String smstag = appState.getSmsTag();
			// 输入的短信必须与原文一模一样
			// if(uppercontent.equals(smstag.toUpperCase())) {
			// 输入的短信只需要包含原文
			if (uppercontent.indexOf(smstag.toUpperCase()) >= 0) {
				// super.stopSelf(startId);
				// mysmsmap.put(to, to);
				smsincoming(to);
				return;
			}
			return;
		}
		bc_receiver = bunde.getString("bc_receiver");
		String callout = bunde.getString("callout");
		if (callout != null && !callout.equals("")) {
			mIncomNumber = callout;
			try {
				callincoming("123");
			} catch (Exception e) {
				e.printStackTrace();

			}
			// 必须确保有注册信息才能使用通话显示地址功能
			/*
			 * if(appState.getUsername().length()>0 &&
			 * appState.getPassword().length()>0 &&
			 * appState.getTxtPhonenumber().length()>0) {
			 * //mycallingmap.put(callout, callout); //mycallingmap.notify();
			 * pruduce(mycallingmap,callout); //callingincoming(callout); }
			 */
			return;
		}

		return;
	}

	private boolean getcellinfo() {
		Boolean isemulator = appState.getIsemulator();
		lcd = -1;
		lac = -1;
		mcc = -1;
		mnc = -1;
		try {
			GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();
			lcd = gcl.getCid();
			lac = gcl.getLac();
			mcc = Integer.valueOf(tm.getNetworkOperator().substring(0, 3));
			mnc = Integer.valueOf(tm.getNetworkOperator().substring(3, 5));
		} catch (NullPointerException ex) {
			// 2.1 no entry,2.2 entry
			// return false;
		}
		if (isemulator) {
			lcd = 12241;
			lac = 21289;
			mcc = 460;
			mnc = 0;
		}
		if (lac > 0) {
			return true;
		}
		return false;
	}

	private void smsincoming(String from) {
		synchronized (mysmsmap)// 1.进入同步块
		{
			mysmsmap.put(from, from);
			// mysmsmap.wait(); //2.进入暂停状态
			mysmsmap.notify(); // 唤醒另外一个进程
		}
	}

	private void callincoming(String from) {
		synchronized (mycallingmap)// 1.进入同步块
		{
			mycallingmap.put(from, from);
			mycallingmap.notify(); // 唤醒另外一个进程
		}
	}

	private void updateTryDateWidget() {
		Date d1 = new java.util.Date();
		// DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// SimpleDateFormat simpledateformat=new SimpleDateFormat(
		// "HH:MM:SS");//日期格式
		SimpleDateFormat simpledateformat1 = new SimpleDateFormat("HH:mm:ss",
				Locale.CHINA);
		String strnow1 = simpledateformat1.format(d1);
		appState.setTxtTryUpdateTime(strnow1);
	}

	private void updateWidget(String strmsg) {
		Date d1 = new java.util.Date();
		// DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// SimpleDateFormat simpledateformat=new SimpleDateFormat(
		// "HH:MM:SS");//日期格式
		SimpleDateFormat simpledateformat1 = new SimpleDateFormat("HH:mm:ss",
				Locale.CHINA);
		String strnow1 = simpledateformat1.format(d1);
		appState.setTxtUpdateDateTime(strnow1);
		appState.setTxtUpdateGpsPos(strmsg);
	}

	/**
	 * 后台发送短信函数，不会弹出界面 需用到<uses-permission
	 * android:name="android.permission.SEND_SMS" />权限
	 * 
	 * @param destinationAddress
	 *            目标手机号
	 * @param message
	 *            短信内容
	 * @return
	 */
	private String sendSMS(String destinationAddress, String message) {
		String strRet = "";
		// 获取信息内容
		// 移动运营商允许每次发送的字节数据有限，
		// 我们可以使用Android给我们提供 的短信工具。
		if (message != null) {
			try {
				SmsManager sms = SmsManager.getDefault();
				// 如果短信没有超过限制长度，则返回一个长度的List。
				List<String> texts = sms.divideMessage(message);
				for (String text : texts) {
					sms.sendTextMessage(destinationAddress, null, text, null,
							null);
					/*********************************************************
					 * 说明sms.sendTextMessage(destinationAddress, scAddress,
					 * text, sentIntent, deliveryIntent)：
					 * destinationAddress:接收方的手机号码 scAddress:短信服务中心号码(null即可)
					 * text:信息内容 sentIntent:发送是否成功的回执， DeliveryIntent:接收是否成功的回执。
					 *********************************************************/
				}
			} catch (Exception ex) {
				strRet = "发送失败：" + ex.getMessage();
				Log.d("Error in SendingSms", ex.getMessage());
			}
			strRet = "发送成功！";
		}
		return strRet;
	}

	class newThread implements Runnable {
		public boolean play = false;
		public String txtPhoneModel = appState.getTxtPhoneModel();
		public String txtPhoneNumber = appState.getTxtPhonenumber();

		public void run() {
			while (true) {
				int nGpsSleep = appState.getSettingGpsV();
				try {
					updateTryDateWidget();
					dogfeedms = System.currentTimeMillis();
					String httpurl = appState.getHttpserverurl();
					if (getcellinfo()) {
						Log.i(TAG, "update cell pos," + "mnc" + mnc + ",mcc"
								+ mcc + ",lac" + lac + ",lcd" + lcd);
						updateTryDateWidget();
						updategpsbycellpackage updategpsbycellpack = new updategpsbycellpackage();
						updategpsbycellpack.setMnc(mnc);
						updategpsbycellpack.setMcc(mcc);
						updategpsbycellpack.setLac(lac);
						updategpsbycellpack.setLcd(lcd);
						updategpsbycellpack.setBshortcid(0);
						updategpsbycellpack.setNeedreply(1);
						updategpsbycellpack.setStrusername(appState
								.getUsername());
						updategpsbycellpack.setStrpassword(appState
								.getPassword());
						String strmsg = updategpsbycellpack.getInput(httpurl,
								mnc, mcc, lac, lcd);
						if (strmsg != null && strmsg.length() > 0) {

						}
						Log.i(TAG, "update cell pos result:" + strmsg);
						strmsg = getCurrentAddress();
						Log.i(TAG, "update cell pos getCurrentAddress():"
								+ strmsg + "mnc" + mnc + ",mcc" + mcc + ",lac"
								+ lac + ",lcd" + lcd);
						updateWidget(strmsg);
						// Thread.sleep(5000);
						Thread.sleep(nGpsSleep * 1000);
					}

				} catch (Exception e) {
					try {
						Thread.sleep(nGpsSleep * 1000);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}

	String getHisAddress(String strcallout) {
		if (!getcellinfo()) {
			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return "";
		}
		updateiamcallingpackage iamcallingpack = new updateiamcallingpackage();
		iamcallingpack.setStrusername(appState.getUsername());
		iamcallingpack.setStrpassword(appState.getPassword());
		iamcallingpack.setStrmycalnum(appState.getTxtPhonenumber());
		iamcallingpack.setStrdestcalnum(strcallout);
		iamcallingpack.setNeedreply(1);
		String httpurl = appState.getHttpserverurl();
		String strmsg = iamcallingpack.getInput(httpurl, mnc, mcc, lac, lcd);
		if (strmsg == null || strmsg.length() < 1) {
			try {
				// Thread.sleep(5*1000);
				Thread.sleep(500);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return "";
		}
		return strmsg;
	}

	String getCurrentAddress() {
		if (!getcellinfo()) {
			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return "";
		}
		String strmsg = "";
		MyCellPosDao mycellsposDao = new MyCellPosDao();
		List<MyCellPos> mycellposlist = new ArrayList<MyCellPos>();
		Dao<MyCellPos, Integer> mycellposDao = null;
		try {
			mycellposDao = getHelper().getMyCellPosDao();
			mycellposlist = mycellsposDao.findCellPos(mycellposDao, mnc, mcc,
					lac, lcd);
			if (mycellposlist.size() > 0) {
				strmsg = mycellposlist.get(0).getAddr();
			}
			// 添加数据
			// MyCellPos mycellpos = new MyCellPos();
			// mycellposDao.create(mycellpos);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strmsg == null || strmsg.length() < 1) {
			getaddrbycellpackage gpspack = new getaddrbycellpackage();
			String httpurl = appState.getHttpserverurl();
			strmsg = gpspack.getInput(httpurl, mnc, mcc, lac, lcd);
			if (strmsg == null || strmsg.length() < 1) {
				try {
					Thread.sleep(5 * 1000);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return "";
			} else {
				try {
					mycellsposDao.addCellPos(mycellposDao, mnc, mcc, lac, lcd,
							strmsg);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return strmsg;
	}

	class callingThread implements Runnable {
		public boolean play = false;
		public String txtPhoneModel = appState.getTxtPhoneModel();
		public String txtPhoneNumber = appState.getTxtPhonenumber();

		public void run() {
			while (true) {
				try {
					int nState = tm.getCallState();
					Log.i(TAG, "mTelManager.getCallState = " + nState);
					synchronized (mycallingmap)// 1.进入同步块
					{
						if (nState == TelephonyManager.CALL_STATE_IDLE) {
							mycallingmap.clear();
						}
						if (mycallingmap.size() <= 0) {
							mycallingmap.wait(); // 2.进入暂停状态
						}
					}
					// 回复短信
					while (mycallingmap.size() > 0) {
						String strmsg = getHisAddress(mIncomNumber);
						if (strmsg.length() < 1) {
							// Thread.sleep(5 * 1000);
							//mOppWhere = "获取对方地址失败";
							//sendBC4UpdateUI();
							break;
						}
						if (strmsg.length() > 1) {
							mOppWhere = strmsg;
							sendBC4UpdateUI();
							// Message m=mHandler.obtainMessage(my_event);
							// mHandler.sendMessage(m);
							try {
								synchronized (mycallingmap)// 1.进入同步块
								{
									mycallingmap.clear();
									mycallingmap.wait(); // 2.进入暂停状态
								}
							} catch (InterruptedException exp) {
								exp.printStackTrace();
							}
						}

					}
				} catch (Exception e) {
					try {
						Thread.sleep(5 * 1000);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}

	class smsThread implements Runnable {
		public boolean play = false;
		public String txtPhoneModel = appState.getTxtPhoneModel();
		public String txtPhoneNumber = appState.getTxtPhonenumber();

		public void run() {
			while (true) {
				int nGpsSleep = appState.getSettingGpsV();
				try {
					synchronized (mysmsmap)// 1.进入同步块
					{
						if (mysmsmap.size() <= 0) {
							mysmsmap.wait(); // 2.进入暂停状态
						}
					}
				} catch (InterruptedException exp) {
					exp.printStackTrace();
				}
				try {

					// 回复短信
					while (mysmsmap.size() > 0) {
						String strmsg = getCurrentAddress();
						if (strmsg.length() < 1) {
							Thread.sleep(nGpsSleep * 1000);
							continue;
						}
						try {
							synchronized (mysmsmap)// 1.进入同步块
							{

								for (Object o : mysmsmap.keySet()) {
									Log.i(TAG, "deal sms");
									String to = mysmsmap.get(o);
									SmsManager sms = SmsManager.getDefault();
									strmsg = "我现在大约在:" + strmsg;
									sendSMS(to, strmsg);
									mysmsmap.remove(to);
								}
							}
						} catch (Exception exp) {
							exp.printStackTrace();
						}
					}
				} catch (Exception e) {
					try {
						Thread.sleep(nGpsSleep * 1000);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}

	class WatchDogThread implements Runnable {
		public void run() {
			while (true) {
				try {
					long nowms = System.currentTimeMillis();
					int nGpsSleep = appState.getSettingGpsV();
					// 超过5倍的更新时间自动重启线程
					if (dogfeedms > 0
							&& (nowms - dogfeedms) > 5 * nGpsSleep * 1000) {
						playThread.stop();
						playObj = new newThread();
						playThread = new Thread(playObj);
						playThread.start();
					}
					Thread.sleep(30000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
