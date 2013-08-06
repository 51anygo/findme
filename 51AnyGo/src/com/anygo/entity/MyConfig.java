package com.anygo.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "myconfig") //不写的话表名默认为类名(小写)account
public class MyConfig implements Serializable {

	private static final long serialVersionUID = -951130775361713081L;	    
	@DatabaseField(generatedId=true) //设置为自增长的主键
	private int aId;
    @DatabaseField  
    private String txtPhonenumber;  
    @DatabaseField  
    private String smstag;  
	@DatabaseField      
    private boolean nosleep;
    @DatabaseField      
    private String  username;    
    @DatabaseField      
    private String  password; 
    @DatabaseField      
    private int  gpspos; 
    
  public int getGpspos() {
		return gpspos;
	}

	public void setGpspos(int gpspos) {
		this.gpspos = gpspos;
	}

	public String getSmstag() {
		return smstag;
	}

	//一定要有一个无参的构造函数
	public MyConfig() {
		super();
	}
	
	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

    public String getTxtPhonenumber() {
		return txtPhonenumber;
	}
	public void setTxtPhonenumber(String txtPhonenumber) {
		this.txtPhonenumber = txtPhonenumber;
	}
	public String getSmsTag() {
		return smstag;
	}
	public void setSmstag(String smstag) {
		this.smstag = smstag;
	}
	public boolean isNosleep() {
		return nosleep;
	}
	public void setNosleep(boolean nosleep) {
		this.nosleep = nosleep;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}  

}
