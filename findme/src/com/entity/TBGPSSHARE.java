package com.entity;

/**
 * TBGPSSHARE entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBGPSSHARE implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Double lat;
	private Double lng;
	private String bgntime;
	private String endtime;
	private String name;
	private String memo;

	// Constructors

	/** default constructor */
	public TBGPSSHARE() {
	}

	/** minimal constructor */
	public TBGPSSHARE(Integer userid, Double lat, Double lng) {
		this.userid = userid;
		this.lat = lat;
		this.lng = lng;
	}

	/** full constructor */
	public TBGPSSHARE(Integer userid, Double lat, Double lng, String bgntime,
			String endtime, String name, String memo) {
		this.userid = userid;
		this.lat = lat;
		this.lng = lng;
		this.bgntime = bgntime;
		this.endtime = endtime;
		this.name = name;
		this.memo = memo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return this.lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getBgntime() {
		return this.bgntime;
	}

	public void setBgntime(String bgntime) {
		this.bgntime = bgntime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}