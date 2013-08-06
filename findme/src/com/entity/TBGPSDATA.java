package com.entity;

/**
 * TBGPSDATA entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBGPSDATA implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Double lat;
	private Double lng;
	private Double latoffset;
	private Double lngoffset;
	private Double alt;
	private String gpstime;
	private String updatetime;
	private Double speed;
	private String angle;
	private String memo;

	// Constructors

	/** default constructor */
	public TBGPSDATA() {
	}

	/** minimal constructor */
	public TBGPSDATA(Integer userid, Double lat, Double lng, Double alt) {
		this.userid = userid;
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
	}

	/** full constructor */
	public TBGPSDATA(Integer userid, Double lat, Double lng, Double latoffset,
			Double lngoffset, Double alt, String gpstime, String updatetime,
			Double speed, String angle, String memo) {
		this.userid = userid;
		this.lat = lat;
		this.lng = lng;
		this.latoffset = latoffset;
		this.lngoffset = lngoffset;
		this.alt = alt;
		this.gpstime = gpstime;
		this.updatetime = updatetime;
		this.speed = speed;
		this.angle = angle;
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

	public Double getLatoffset() {
		return this.latoffset;
	}

	public void setLatoffset(Double latoffset) {
		this.latoffset = latoffset;
	}

	public Double getLngoffset() {
		return this.lngoffset;
	}

	public void setLngoffset(Double lngoffset) {
		this.lngoffset = lngoffset;
	}

	public Double getAlt() {
		return this.alt;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	public String getGpstime() {
		return this.gpstime;
	}

	public void setGpstime(String gpstime) {
		this.gpstime = gpstime;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Double getSpeed() {
		return this.speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getAngle() {
		return this.angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}