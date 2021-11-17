package com.rapipay.newOTPGenerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NullValueException extends Exception{

	public NullValueException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

	

}
