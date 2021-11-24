package com.rapipay.newOTPGenerate.controller;

public class ApplicationResponseEntity <T>{

	private String responseCode;
	private String responseMessage;
	private T responseBody;
	public ApplicationResponseEntity(String responseCode, String responseMessage, T responseBody) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseBody = responseBody;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public T getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}
	
	
	
}
