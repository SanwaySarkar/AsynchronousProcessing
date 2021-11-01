package com.asp.common;

public class Message {
	private String id;
	public Message(String id, String paramsJson, String groupCode, String userName, String messageType) {
		super();
		this.id = id;
		this.paramsJson = paramsJson;
		this.groupCode = groupCode;
		this.userName = userName;
		this.messageType = messageType;
	}
	private String paramsJson;
	private String groupCode;
	private String userName;
	private String messageType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParamsJson() {
		return paramsJson;
	}
	public void setParamsJson(String paramsJson) {
		this.paramsJson = paramsJson;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}
