package com.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.jcenterhome.util.Common;

import com.common.DateUtils;
import com.common.MySecurity;
import com.dao.myuserdao;
import com.entity.TBCELL2GPS;
import com.entity.TBGPSDATA;
import com.entity.TBGPSOFFSET;
import com.entity.TBGPSSHARE;
import com.entity.TBIPDATA;
import com.entity.TBSYSTEMSET;
import com.entity.TBUSER;

/**
 * @author new
 *
 */
public class myuserdaoimpl extends HibernateDaoSupport implements myuserdao {
	private static boolean isInit=false;
public List findAllUser( ){
	List list = (List) getHibernateTemplate().execute(
	            new HibernateCallback() {

	        public Object doInHibernate(Session session)
	                throws HibernateException {
	            Query q =session.createQuery("from TBUSER");
	             List cats = q.list();
	            return cats;
	        }
	    });//内部类，返回一个List
	     return list;
	    } 

		public boolean deleteUser(TBUSER u) {
			try {			
				String stroldusername=u.getUsername();
				String stroldpassword=u.getPassword();
				u.setUsername(MySecurity.encrypt(u.getUsername()));
				u.setPassword(MySecurity.encrypt(u.getPassword()));
				this.getHibernateTemplate().delete(u);	
				u.setUsername(stroldusername);
				u.setPassword(stroldpassword);
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
			return true;
		}
		
	public boolean updateUser(TBUSER u)	{
		boolean flag = false;
		try{
			this.getHibernateTemplate().update(u);
			flag = true;
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	public boolean addUser(TBUSER u) {
		try {
			//this.getHibernateTemplate().delete(u);
			TBUSER snsuser=new TBUSER();
			snsuser.setUsername(u.getUsername());
			snsuser.setPassword(u.getPassword());	
			u.setUsername(MySecurity.encrypt(u.getUsername()));
			u.setPassword(MySecurity.encrypt(u.getPassword()));
			//u.setIspnverify(0);
			this.getHibernateTemplate().save(u);			
			if(!issnsexists(snsuser.getUsername()))
			{
				regassns(snsuser);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public TBGPSOFFSET getgpsoffset(final TBGPSDATA gpsdata)
    {
    	TBGPSOFFSET gpsoffset=null;
    	if(gpsdata!=null)
		{
			gpsoffset = (TBGPSOFFSET) this.getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session s)
								throws HibernateException, SQLException {
							Object ret=null;
							try
							{
							    Query qry = s.createQuery("from TBGPSOFFSET where (abs(lat-?)+abs(lng-?)) in (select min(abs(lat-?)+abs(lng-?)) " +
								"from TBGPSOFFSET) ");
								qry.setFirstResult(0);
								qry.setMaxResults(1);
								//Query qry = s.createQuery("from TBGPSDATA as u where u.id=?");
								qry.setDouble(0, gpsdata.getLat());
								qry.setDouble(1, gpsdata.getLng());
								qry.setDouble(2, gpsdata.getLat());
								qry.setDouble(3, gpsdata.getLng());
								ret=qry.uniqueResult();
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
								ret=null;
							}
							return ret;
						}
					}); 
		}
    	return gpsoffset;
    }
    
    
	public TBGPSDATA lastestgpsdata(final boolean bonlygps,final TBUSER u) {
		if (u == null) {
			return null;
		}
		final TBGPSDATA gpsdata = (TBGPSDATA) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						//Object resultobj= s.createQuery("max(id) from TBGPSDATA as u where u.userid=?").uniqueResult();
						//int maxid  = (Integer) s.createQuery("select max(id) from TBGPSDATA as u where u.userid=? order by ").setParameter(0, u.getId()).list().get(0);
						Query qry = null;
						if(bonlygps)
						{
							qry=s.createQuery("from TBGPSDATA as u where u.userid=? and (speed<>88 or angle<>0) order by u.id desc");
						}
						else
						{
							qry=s.createQuery("from TBGPSDATA as u where u.userid=? order by u.id desc");
						}
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						//Query qry = s.createQuery("from TBGPSDATA as u where u.id=?");
						qry.setInteger(0, u.getId());
						return qry.uniqueResult();
					}
				});
		TBGPSOFFSET gpsoffset=getgpsoffset(gpsdata);
		if(gpsoffset!=null)
		{
			gpsdata.setLatoffset(gpsoffset.getLatoffset());
			gpsdata.setLngoffset(gpsoffset.getLngoffset());
		}
		return gpsdata;
	}
	
	public TBGPSDATA getgpsdatabytime(final boolean bonlygps,final TBUSER u,final String strquerytime) {
		if (u == null) {
			return null;
		}
		final TBGPSDATA gpsdata = (TBGPSDATA) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						//Object resultobj= s.createQuery("max(id) from TBGPSDATA as u where u.userid=?").uniqueResult();
						//int maxid  = (Integer) s.createQuery("select max(id) from TBGPSDATA as u where u.userid=? order by ").setParameter(0, u.getId()).list().get(0);
						Query qry = null;
						if(bonlygps)
						{
							qry=s.createQuery("from TBGPSDATA as u where u.userid=? and u.updatetime>? and (speed<>88 or angle<>0) order by u.id");
						}
						else
						{
							qry=s.createQuery("from TBGPSDATA as u where u.userid=? and u.updatetime>? order by u.id");
						}
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						//Query qry = s.createQuery("from TBGPSDATA as u where u.id=?");
						qry.setInteger(0, u.getId());
						qry.setString(1,strquerytime);
						return qry.uniqueResult();
					}
				});
		TBGPSOFFSET gpsoffset=getgpsoffset(gpsdata);
		if(gpsoffset!=null)
		{
			gpsdata.setLatoffset(gpsoffset.getLatoffset());
			gpsdata.setLngoffset(gpsoffset.getLngoffset());
		}
		return gpsdata;
	}

	public TBUSER login(final TBUSER u) {
		if(!isInit){
			   List<TBUSER> users = (List<TBUSER>) this.findAllUser();  
			   int len = users.size();  
			   for (int i = 0; i < len; i++) {  
				      TBUSER user = users.get(i);  
				      String strusername=MySecurity.decrypt(user.getUsername());
				      String strupassword=MySecurity.decrypt(user.getPassword());
				      user.setUsername(strusername);
				      user.setPassword(strupassword);
			          System.out.println("User name:"+strusername);  
			          if(!issnsexists(user.getUsername()))
					  { 
						  regassns(user);
					 }
			      }  
			      isInit=true;
		}
		TBUSER user = (TBUSER) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBUSER as u where u.username=? and u.password=?");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						qry.setString(0, MySecurity.encrypt(u.getUsername()));
						qry.setString(1, MySecurity.encrypt(u.getPassword()));
						return qry.uniqueResult();
					}
				});
		if (user != null) {
			try {
				user.setUsername(MySecurity.decrypt(user.getUsername()));
				user.setPassword(MySecurity.decrypt(user.getPassword()));	
				if(!issnsexists(user.getUsername()))
				{
					regassns(user);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return user;
	}
	
	
	public TBUSER getUser(final Integer uid) {
		TBUSER user = (TBUSER) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBUSER as u where u.id=?");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						qry.setInteger(0, uid);
						return qry.uniqueResult();
					}
				});
		return user;
	}
	public boolean issnsexists(String strusername) {
		final String tempsql="select uid from jchome_member where username="
			+"'"+strusername+"'";
		List users = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List list=s.createSQLQuery(tempsql).list();
						return list;
					}
				});
		//能查询到就表示存在
		boolean ret=users != null && users.size()>0;
		return ret;
	}
	
	public void regassns(final TBUSER u) {
		//insert user to sns db
		String salt = Common.getRandStr(6, false);
		String userName=u.getUsername();
		String password=u.getPassword();
		String blacklist="";
		password = Common.md5(Common.md5(password) + salt);
		final String tempsql="insert into jchome_member (username,password,blacklist,salt) values("
		+"'"+userName+"',"+"'"+password+"',"+"'"+blacklist+"',"+"'"+salt+"')";
		//int newUid = dataBaseService.insertTable("member", insertData, true, false);
		this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						//s.createSQLQuery(tempsql).executeUpdate();
						s.connection().createStatement().execute(tempsql); 
						return null;
					}
				});
		return;
	}
	
	public boolean updategps(TBUSER u, TBGPSDATA gpsdata) {

		gpsdata.setUserid(u.getId());
		TBGPSOFFSET gpsoffset=getgpsoffset(gpsdata);
		if(gpsoffset!=null)
		{
			gpsdata.setLatoffset(gpsoffset.getLatoffset());
			gpsdata.setLngoffset(gpsoffset.getLngoffset());
		}
		this.getHibernateTemplate().save(gpsdata);
		return true;
	}

	public boolean isexists(String strusername) {
		final String strusrname = MySecurity.encrypt(strusername);;
		TBUSER user = (TBUSER) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBUSER as u where u.username=?");
						qry.setString(0, strusrname);
						return qry.uniqueResult();
					}
				});
		return user != null;
	}

	public TBUSER isemailexists(final String stremail) {

		TBUSER user = (TBUSER) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBUSER as u where u.email=?");
						qry.setString(0, stremail);
						return qry.uniqueResult();
					}
				});
		if (user != null) {
			try {
				user.setUsername(MySecurity.decrypt(user.getUsername()));
				user.setPassword(MySecurity.decrypt(user.getPassword()));
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return user;
	}

	public TBIPDATA getaddressbyip(final String strip) {
		String strtmpip=strip.trim();
		final long ipnum = getIpNum(strtmpip);
		if(ipnum<0)
		{
			return null;
		}
		TBIPDATA ipdata = (TBIPDATA) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBIPDATA as u where ? between u.ipnum1 and u.ipnum2");
						qry.setLong(0, ipnum);
						return qry.uniqueResult();
					}
				});
		return ipdata;
	}

	private static long getIpNum(String ipAddress) {
		long ipNum = 0;
		try
		{
			String[] ip = ipAddress.split("\\.");
			long a = Integer.parseInt(ip[0]);
			long b = Integer.parseInt(ip[1]);
			long c = Integer.parseInt(ip[2]);
			long d = Integer.parseInt(ip[3]);
			ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			ipNum = -1;
		}
		return ipNum;
	}

	private static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}

	public static boolean isInnerIP(String ipAddress) {
		boolean isInnerIp = false;
		long ipNum = getIpNum(ipAddress);
		/**   
		私有IP：A类  10.0.0.0-10.255.255.255   
		       B类  172.16.0.0-172.31.255.255   
		       C类  192.168.0.0-192.168.255.255   
		当然，还有127这个网段是环回地址   
		 **/
		long aBegin = getIpNum("10.0.0.0");
		long aEnd = getIpNum("10.255.255.255");
		long bBegin = getIpNum("172.16.0.0");
		long bEnd = getIpNum("172.31.255.255");
		long cBegin = getIpNum("192.168.0.0");
		long cEnd = getIpNum("192.168.255.255");
		isInnerIp = isInner(ipNum, aBegin, aEnd)
				|| isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
				|| ipAddress.equals("127.0.0.1");
		return isInnerIp;
	}

	public TBGPSSHARE getGpsshare(Double lat, Double lng) {

		TBGPSSHARE gpsshare = (TBGPSSHARE) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBGPSSHARE");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						return qry.uniqueResult();
					}
				});
		return gpsshare;
	}

	public TBUSER getUser(final TBUSER u) {
		final String strusername=u.getUsername();
		final String strencusername=MySecurity.encrypt(strusername);
		TBUSER tempuser=null;
		TBUSER user = (TBUSER) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s
								.createQuery("from TBUSER as u where u.username=?");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						qry.setString(0, strencusername);
						return qry.uniqueResult();
					}
				});
		if (user != null) {
			try {
				tempuser=new TBUSER();
				tempuser.setUsername(MySecurity.decrypt(user.getUsername()));
				tempuser.setPassword(MySecurity.decrypt(user.getPassword()));
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return tempuser;
	}

	public TBGPSDATA getGpssharebytime(final int shareid, final String strquerytime) {
		
		final TBGPSDATA gpsdata = (TBGPSDATA) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s.createQuery("from TBGPSDATA as u where u.userid in (select userid from TBGPSSHARE where id=? and bgntime<=? and endtime>=?) and u.updatetime>? order by u.id");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						//Query qry = s.createQuery("from TBGPSDATA as u where u.id=?");
						qry.setInteger(0, shareid);
						qry.setString(1,strquerytime);
						qry.setString(2,strquerytime);
						qry.setString(3,strquerytime);
						return qry.uniqueResult();
					}
				});
		if(gpsdata!=null)
		{
			TBGPSOFFSET gpsoffset=getgpsoffset(gpsdata);
			if(gpsoffset!=null)
			{
				gpsdata.setLatoffset(gpsoffset.getLatoffset());
				gpsdata.setLngoffset(gpsoffset.getLngoffset());
			}
		}
		return gpsdata;
	}

	public TBSYSTEMSET getsystemset(final String strname) {
		final TBSYSTEMSET systemset = (TBSYSTEMSET) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s.createQuery("from TBSYSTEMSET as u where u.name=?");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						qry.setString(0,strname);
						return qry.uniqueResult();
					}
				});
		return systemset;
	}

	public boolean updatecell2gps(TBCELL2GPS cell2gpsdata) {
		cell2gpsdata.setUpdatetime(DateUtils.dateToStr(new Date()));
		this.getHibernateTemplate().save(cell2gpsdata);
		return true;
	}

	public TBCELL2GPS getcell2gps(final int MCC,final int MNC,final int LAC,final int CID,
			                      final int BSHORTCID) {
	         	final TBCELL2GPS cell2gps = (TBCELL2GPS) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						Query qry = s.createQuery("from TBCELL2GPS as u where u.mcc=? and u.mnc=? " +
								    "and u.lac=? and u.cid=? and u.bshortcid=? order by u.id desc");
						qry.setFirstResult(0);
						qry.setMaxResults(1);
						qry.setInteger(0,MCC);
						qry.setInteger(1,MNC);
						qry.setInteger(2,LAC);
						qry.setInteger(3,CID);
						qry.setInteger(4,BSHORTCID);
						return qry.uniqueResult();
					}
				});
		return cell2gps;
	}

}
