package com.xtivia.loginHistory.util;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

public class UserInformation {
	private String userID;	
	private String loginDate;	
	private Date lastLoginDate;
	private String firstName;
	private String lastName;
	private String status;
	private String statusOption;
	private String userAction;
	private String event1;
	private String event2;
	private String event3;
	private String event4;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public UserInformation(){
		
	}
	public String getEvent1() {
		return event1;
	}
	public void setEvent1(String event1) {
		this.event1 = event1;
	}
	public String getEvent2() {
		return event2;
	}
	public void setEvent2(String event2) {
		this.event2 = event2;
	}
	public String getEvent3() {
		return event3;
	}
	public void setEvent3(String event3) {
		this.event3 = event3;
	}
	public String getEvent4() {
		return event4;
	}
	public void setEvent4(String event4) {
		this.event4 = event4;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public String getStatusOption() {
		return statusOption;
	}
	public void setStatusOption(String statusOption) {
		this.statusOption = statusOption;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserInformation(String status,String statusOption,String userAction,String event1, String event2,String event3, String event4,Date lastLoginDate) {
		super();
		this.status = status;
		this.statusOption=statusOption;
		this.userAction=userAction;
		this.event1=event1;
		this.event2=event2;
		this.event3=event3;
		this.event4=event4;
		this.lastLoginDate=lastLoginDate;
	}
	

}
