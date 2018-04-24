package com.naruto.entity;

import java.io.Serializable;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月23日
 */
public class VisitLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String ip;
	private String browser;
	private String os;
	private int time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
