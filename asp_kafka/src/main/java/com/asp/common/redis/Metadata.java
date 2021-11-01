package com.asp.common.redis;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Metadata")
public class Metadata implements Serializable{
	@Override
	public String toString() {
		return "Metadata [userName=" + userName + ", groupCode=" + groupCode + ", jobCategory=" + jobCategory + "]";
	}
	Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String userName;
	private String groupCode;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public Metadata( Long id,String userName, String groupCode, String jobCategory) {
		super();
		this.id=id;
		this.userName = userName;
		this.groupCode = groupCode;
		this.jobCategory = jobCategory;
		
	}
	
	public Metadata() {
		
	}
	private String jobCategory;

}
