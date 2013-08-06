package com.anygo.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "MyCellPos") //不写的话表名默认为类名(小写)account
public class MyCellPos implements Serializable {

	private static final long serialVersionUID = -951130775361713082L;	    
	@DatabaseField(generatedId=true) //设置为自增长的主键
	private int aId;
    @DatabaseField  
	private int mcc;
    @DatabaseField  
	private	int mnc;
    @DatabaseField  
	private	int lac;
    @DatabaseField  
	private	int lcd;
    @DatabaseField  
    private String addr;  
	@DatabaseField      
	private	double  lat; 
	@DatabaseField     
	private	double  lng;  
  //一定要有一个无参的构造函数
	public MyCellPos() {
		super();
	}
	
	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public int getLcd() {
		return lcd;
	}

	public void setLcd(int lcd) {
		this.lcd = lcd;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

  
}
