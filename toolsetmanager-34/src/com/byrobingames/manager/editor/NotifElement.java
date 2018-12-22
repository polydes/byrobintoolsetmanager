package com.byrobingames.manager.editor;

import java.io.Serializable;

public class NotifElement implements Serializable {
	
	private String title;
	private String message;
	private Integer timeinterval;
	private String doevery;
	private Integer notifid;
			
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Integer getTimeinterval() {
		return timeinterval;
	}


	public void setTimeinterval(Integer timeinterval) {
		this.timeinterval = timeinterval;
	}


	public String getDoevery() {
		return doevery;
	}


	public void setDoevery(String doevery) {
		this.doevery = doevery;
	}


	public Integer getNotifid() {
		return notifid;
	}


	public void setNotifid(Integer notifid) {
		this.notifid = notifid;
	}
		
	

}
