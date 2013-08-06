package com.entity;

/**
 * TBSYSTEMSET entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBSYSTEMSET implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String value;

	// Constructors

	/** default constructor */
	public TBSYSTEMSET() {
	}

	/** full constructor */
	public TBSYSTEMSET(String name, String value) {
		this.name = name;
		this.value = value;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}