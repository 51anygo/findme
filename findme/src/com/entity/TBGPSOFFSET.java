package com.entity;

/**
 * TBGPSOFFSET entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBGPSOFFSET implements java.io.Serializable {

	// Fields

	private Integer id;
	private Double lat;
	private Double lng;
	private Double latoffset;
	private Double lngoffset;
	private String address;

	// Constructors

	/** default constructor */
	public TBGPSOFFSET() {
	}

	/** minimal constructor */
	public TBGPSOFFSET(Double lat, Double lng, Double latoffset,
			Double lngoffset) {
		this.lat = lat;
		this.lng = lng;
		this.latoffset = latoffset;
		this.lngoffset = lngoffset;
	}

	/** full constructor */
	public TBGPSOFFSET(Double lat, Double lng, Double latoffset,
			Double lngoffset, String address) {
		this.lat = lat;
		this.lng = lng;
		this.latoffset = latoffset;
		this.lngoffset = lngoffset;
		this.address = address;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}