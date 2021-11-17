package com.rapipay.newOTPGenerate.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class OTPEntities {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String user_id;

	private long order_id;

	private String channelName;
	private String otp;
	private long timeStamp;
	public OTPEntities() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OTPEntities(String user_id, String channelName, long order_id) {
		super();
		this.user_id = user_id;
		this.channelName = channelName;
		this.order_id=order_id;
	}
	
	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp() {
		this.timeStamp = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "otpEntity [user_id=" + user_id + ", order_id=" + order_id + ", channelName=" + channelName + ", otp="
				+ otp + ", timeStamp=" + timeStamp + "]";
	}

	public OTPEntities(String user_id, long order_id, String channelName, String otp, long timeStamp) {
		super();
		this.user_id = user_id;
		this.order_id = order_id;
		this.channelName = channelName;
		this.otp = otp;
		this.timeStamp = timeStamp;
	}
	

	
	
}
	
// jacoco:prepare-agent test install jacoco:report