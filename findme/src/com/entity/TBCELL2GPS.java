package com.entity;

/**
 * TBCELL2GPS entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBCELL2GPS implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mcc;
	private Integer mnc;
	private Integer lac;
	private Integer cid;
	private Integer bshortcid;
	private Double lat;
	private Double lng;
	private String updatetime;
	private Integer userid;
	private String address;
	private String destcalnum;
	private Integer iscalling;

	// Constructors

	/** default constructor */
	public TBCELL2GPS() {
	}

	/** minimal constructor */
	public TBCELL2GPS(Integer mcc, Integer mnc, Integer lac, Integer cid,
			Integer bshortcid, Double lat, Double lng, String updatetime,
			String address) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.lac = lac;
		this.cid = cid;
		this.bshortcid = bshortcid;
		this.lat = lat;
		this.lng = lng;
		this.updatetime = updatetime;
		this.address = address;
	}

	/** full constructor */
	public TBCELL2GPS(Integer mcc, Integer mnc, Integer lac, Integer cid,
			Integer bshortcid, Double lat, Double lng, String updatetime,
			Integer userid, String address) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.lac = lac;
		this.cid = cid;
		this.bshortcid = bshortcid;
		this.lat = lat;
		this.lng = lng;
		this.updatetime = updatetime;
		this.userid = userid;
		this.address = address;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMcc() {
		return this.mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMnc() {
		return this.mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public Integer getLac() {
		return this.lac;
	}

	public void setLac(Integer lac) {
		this.lac = lac;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getBshortcid() {
		return this.bshortcid;
	}

	public void setBshortcid(Integer bshortcid) {
		this.bshortcid = bshortcid;
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

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDestcalnum() {
		return destcalnum;
	}

	public void setDestcalnum(String destcalnum) {
		this.destcalnum = destcalnum;
	}

	public Integer getIscalling() {
		return iscalling;
	}

	public void setIscalling(Integer iscalling) {
		this.iscalling = iscalling;
	}

}