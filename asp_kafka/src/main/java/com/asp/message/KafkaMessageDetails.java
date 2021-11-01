package com.asp.message;

public class KafkaMessageDetails {
	
	@Override
	public String toString() {
		return "KafkaJobDetails [jobCategory=" + jobCategory + ", message=" + message + ", version=" + version
				+ ", groupCode=" + groupCode + ", userName=" + userName + "]";
	}

	//Type of class to be created 
	private String jobCategory;
	
	//json data to be initiated in the defined class
	private String message; 
	//create Date
	private String createdDate;
	
	//version of the code
	private String version;
	
	//client name code
	private String groupCode;
	
	//System as of now
	protected String userName;
	
	public KafkaMessageDetails() {
		
	}

	public KafkaMessageDetails(String jobCategory, String message, String createdDate, String version, String groupCode,
			String userName) {
		super();
		this.jobCategory = jobCategory;
		this.message = message;
		this.createdDate = createdDate;
		this.version = version;
		this.groupCode = groupCode;
		this.userName = userName;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
