package com.entity;

import java.util.Date;

public class Paywarnrecord {
	public enum Status{
		noread,
		read
	}
	private int id;
	private String status;
	private int notice;
	private Date curtime;

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCurtime() {
		return curtime;
	}

	public void setCurtime(Date curtime) {
		this.curtime = curtime;
	}

}
