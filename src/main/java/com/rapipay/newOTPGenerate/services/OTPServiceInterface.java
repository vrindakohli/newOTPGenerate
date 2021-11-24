package com.rapipay.newOTPGenerate.services;

import org.springframework.http.ResponseEntity;

import com.rapipay.newOTPGenerate.entities.OTPEntities;
import com.rapipay.newOTPGenerate.exception.InvalidChannel;
import com.rapipay.newOTPGenerate.exception.InvalidEmail;
import com.rapipay.newOTPGenerate.exception.InvalidMobile;
import com.rapipay.newOTPGenerate.exception.InvalidOTPException;
import com.rapipay.newOTPGenerate.exception.NoUserException;
import com.rapipay.newOTPGenerate.exception.NullValueException;
import com.rapipay.newOTPGenerate.exception.ReGenerateOTP;
import com.rapipay.newOTPGenerate.exception.TimeStampExceededException;

public interface OTPServiceInterface {
	public String generateOTP();
	public OTPEntities addData(OTPEntities otpEntities) throws InvalidEmail, InvalidMobile, InvalidChannel, ReGenerateOTP, NullValueException;
	public OTPEntities validateOtp(String uId, String otp) throws NoUserException, InvalidOTPException, TimeStampExceededException;
	

}
