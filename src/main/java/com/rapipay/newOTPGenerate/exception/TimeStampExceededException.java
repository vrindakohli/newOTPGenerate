package com.rapipay.newOTPGenerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class TimeStampExceededException extends Exception{

	@Override
	public String toString() {
		return "TimeStampExceededException []";
	}

	
	
	

}
