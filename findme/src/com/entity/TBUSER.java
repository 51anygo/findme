package com.entity;

/**
 * TBUSER entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TBUSER implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String email;
	private String nickname;
	private String regdate;
	private String memo;
	private String phonenumber;
	private Integer ispnverify;
	private Integer lastposid;

	// Constructors

	/** default constructor */
	public TBUSER() {
	}

	/** full constructor */
	public TBUSER(String username, String password, String email,
			String nickname, String regdate, String memo) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.regdate = regdate;
		this.memo = memo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRegdate() {
		return this.regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Integer getIspnverify() {
		return ispnverify;
	}

	public void setIspnverify(Integer ispnverify) {
		this.ispnverify = ispnverify;
	}

	public Integer getLastposid() {
		return lastposid;
	}

	public void setLastposid(Integer lastposid) {
		this.lastposid = lastposid;
	}

}