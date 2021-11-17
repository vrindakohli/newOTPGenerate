package com.rapipay.newOTPGenerate.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice  //allows to handle exceptions throughout the code. specialization of @Component
public class GlobalExceptionHandler {
	@ExceptionHandler(NoUserException.class)
	public ResponseEntity<?> userNotFoundException(NoUserException ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidOTPException.class)
	public ResponseEntity<?> invalidOtp(InvalidOTPException ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TimeStampExceededException.class)
	public ResponseEntity<?> timeStampExceeded(TimeStampExceededException ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidEmail.class)
	public ResponseEntity<?> emailNotValid(InvalidEmail ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidMobile.class)
	public ResponseEntity<?> mobileNotValid(InvalidMobile ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidChannel.class)
	public ResponseEntity<?> channelNotValid(InvalidChannel ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ReGenerateOTP.class)
	public ResponseEntity<?> reGenerateOtp(ReGenerateOTP ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.toString(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<?> nullValue(NullValueException ex, WebRequest web){
		ErrorDetails err= new ErrorDetails(new Date(), ex.getMessage(), web.getDescription(false));
		return new ResponseEntity<> (err, HttpStatus.NOT_FOUND);
	}
	
	

}
