package com.rapipay.newOTPGenerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class ReGenerateOTP extends Exception 
{

	@Override
	public String toString() {
		return "ReGenerateOTP not allowedd";
	}
	
	

}
