package com.anygo.db;

import java.sql.SQLException;  
import java.util.HashMap;
import java.util.Map;

import android.content.Context;  
import android.database.sqlite.SQLiteDatabase;  
import android.util.Log;  
   
import com.anygo.entity.MyCellPos;
import com.anygo.entity.MyConfig;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;  
import com.j256.ormlite.dao.Dao;  
import com.j256.ormlite.support.ConnectionSource;  
import com.j256.ormlite.table.TableUtils;  
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	      
	    private static final String DATABASE_NAME = "anygo.db";  
	    private static final int DATABASE_VERSION = 1;  
	    
	    
	    @SuppressWarnings("rawtypes")  
	    Map<String, Dao> daoMaps = null; // 所有DAO的集合，这不是必须的，你可以采用你的方式   

	    //private Dao<MyConfig,Integer> stuDao = null;  
	      
	    public DatabaseHelper(Context context){  
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	        initDaoMaps(); // 自定义方法 ,初始化dao的Map.  
	    }  
	    
	    @SuppressWarnings("rawtypes")  
	    private void initDaoMaps() {  
	        daoMaps = new HashMap<String, Dao>();  
	        daoMaps.put("myconfigDao", null);  
	        daoMaps.put("mycellposDao", null);  
	    }  
	  
	    /**  
	     * 创建SQLite数据库  
	     */  
	    @Override  
	    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {  
	        try {  
	            TableUtils.createTable(connectionSource, MyConfig.class);  
	            TableUtils.createTable(connectionSource, MyCellPos.class);  
	        } catch (SQLException e) {  
	            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);  
	        }  
	    }  
	  
	    /**  
	     * 更新SQLite数据库  
	     */  
	    @Override  
	    public void onUpgrade(  
	            SQLiteDatabase sqliteDatabase,   
	            ConnectionSource connectionSource,   
	            int oldVer,  
	            int newVer) {  
	        try {  
	            TableUtils.dropTable(connectionSource, MyConfig.class, true);  
	            TableUtils.dropTable(connectionSource, MyCellPos.class, true);  
	            onCreate(sqliteDatabase, connectionSource);  
	        } catch (SQLException e) {  
	            Log.e(DatabaseHelper.class.getName(),   
	                    "Unable to upgrade database from version " + oldVer + " to new "  
	                    + newVer, e);  
	        }  
	    }  
	    
	    @Override  
	    public void close() {  
	        super.close();  
	        daoMaps.clear();  
	    }
	    
	    //给它一个实体类，返回该实体类专用的Dao，功能很强大  
	    @SuppressWarnings("unchecked")  
	    public Dao<MyCellPos,Integer>  getMyCellPosDao() {  
	    	Dao<MyCellPos,Integer>  mycellposDao = daoMaps.get("mycellposDao");  
	        if (mycellposDao == null) {  
	            try {  
	            	mycellposDao = getDao(MyCellPos.class);  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return mycellposDao;  
	    } 
	    
	    
	  //给它一个实体类，返回该实体类专用的Dao，功能很强大  
	    @SuppressWarnings("unchecked")  
	    public Dao<MyConfig,Integer>  getMyConfigDao() {  
	    	Dao<MyConfig,Integer>  myconfigDao = daoMaps.get("myconfigDao");  
	        if (myconfigDao == null) {  
	            try {  
	            	myconfigDao = getDao(MyConfig.class);  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return myconfigDao;  
	    } 
	    
	      
	    /*public Dao<MyConfig,Integer> getMyConfigDao() throws SQLException{  
	        if(stuDao == null){  
	            stuDao = getDao(MyConfig.class);  
	        }  
	        return stuDao;  
	    } */ 
}
