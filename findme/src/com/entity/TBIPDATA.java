package com.entity;

/**
 * TBIPDATA entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBIPDATA implements java.io.Serializable {

	// Fields

	private Integer id;
	private Long ipnum1;
	private Long ipnum2;
	private String address;
	private String address1;

	// Constructors

	/** default constructor */
	public TBIPDATA() {
	}

	/** minimal constructor */
	public TBIPDATA(Integer id, String address) {
		this.id = id;
		this.address = address;
	}

	/** full constructor */
	public TBIPDATA(Integer id, Long ipnum1, Long ipnum2, String address,
			String address1) {
		this.id = id;
		this.ipnum1 = ipnum1;
		this.ipnum2 = ipnum2;
		this.address = address;
		this.address1 = address1;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIpnum1() {
		return this.ipnum1;
	}

	public void setIpnum1(Long ipnum1) {
		this.ipnum1 = ipnum1;
	}

	public Long getIpnum2() {
		return this.ipnum2;
	}

	public void setIpnum2(Long ipnum2) {
		this.ipnum2 = ipnum2;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

}