package com.rapipay.newOTPGenerate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class InvalidChannel extends Exception{

	@Override
	public String toString() {
		return "InvalidChannel []";
	}

	
	

}
