package com.rapipay.newOTPGenerate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapipay.newOTPGenerate.entities.OTPEntities;
import com.rapipay.newOTPGenerate.exception.InvalidChannel;
import com.rapipay.newOTPGenerate.exception.InvalidEmail;
import com.rapipay.newOTPGenerate.exception.InvalidMobile;
import com.rapipay.newOTPGenerate.exception.InvalidOTPException;
import com.rapipay.newOTPGenerate.exception.NoUserException;
import com.rapipay.newOTPGenerate.exception.NullValueException;
import com.rapipay.newOTPGenerate.exception.ReGenerateOTP;
import com.rapipay.newOTPGenerate.exception.TimeStampExceededException;
import com.rapipay.newOTPGenerate.services.OTPServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MyController implements ErrorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	
	@Autowired
	private OTPServiceInterface otpService;
	
	/*@GetMapping("/genOtp")
	public String generateOTP() {
		return this.otpService.generateOTP();
	}*/
	
	@PostMapping(path="/addData")
	public OTPEntities addDataInDb(@RequestBody OTPEntities otpEntities) throws InvalidEmail, InvalidMobile, InvalidChannel, ReGenerateOTP, NullValueException {
		//System.out.println("1");
		LOGGER.info("Entered generate otp section");		
			return this.otpService.addData(otpEntities);
		
		}
	
	@GetMapping("/validate/{user_id}/{otp}")
	public ResponseEntity<OTPEntities> validateOTP(@PathVariable String user_id, @PathVariable String otp) throws NoUserException, InvalidOTPException, TimeStampExceededException {
		//System.out.println(user_id+""+otp);
	
			return this.otpService.validateOtp(user_id, otp);
		
	}
	
}
