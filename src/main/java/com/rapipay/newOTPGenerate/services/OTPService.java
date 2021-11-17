package com.rapipay.newOTPGenerate.services;

import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.rapipay.newOTPGenerate.dao.OtpDao;
import com.rapipay.newOTPGenerate.entities.OTPEntities;
import com.rapipay.newOTPGenerate.exception.InvalidChannel;
import com.rapipay.newOTPGenerate.exception.InvalidEmail;
import com.rapipay.newOTPGenerate.exception.InvalidMobile;
import com.rapipay.newOTPGenerate.exception.InvalidOTPException;
import com.rapipay.newOTPGenerate.exception.NoUserException;
import com.rapipay.newOTPGenerate.exception.NullValueException;
import com.rapipay.newOTPGenerate.exception.ReGenerateOTP;
import com.rapipay.newOTPGenerate.exception.TimeStampExceededException;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class OTPService implements OTPServiceInterface {
	@Value("${app.generate}")
	public int x;
	@Value("${app.validate}")
	public int y;
	
	@Autowired
	private OtpDao otpDao;

	@Override
	public String generateOTP() {
		int otp;
		Random random = new Random();
		otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);

	}

	@Override
	public OTPEntities addData(OTPEntities otpEntities) throws InvalidEmail, InvalidMobile, InvalidChannel, ReGenerateOTP, NullValueException {

		
		
		// to ensure otp is not generated before x minute
		OTPEntities oe = otpDao.findById(otpEntities.getUser_id()).orElse(null);
		System.out.println(x+" hello");
		if(Strings.isNullOrEmpty(otpEntities.getChannelName())) {
			throw new NullValueException("channel cant be null");
		}
		if(Strings.isNullOrEmpty(otpEntities.getUser_id())) {
			throw new NullValueException("user id cant be null");
		}
		if(otpEntities.getOrder_id()==0) {
			throw new NullValueException("order id cant be null");
		}
		if (oe!=null) {
			if (((System.currentTimeMillis() - oe.getTimeStamp()) / 60000) < x) {
				throw new ReGenerateOTP();
			}
		}
		if (otpEntities.getChannelName().equalsIgnoreCase("email")) {
			if(!checkEmail(otpEntities.getUser_id()))
			{
				throw new InvalidEmail();
			}

		} else if (otpEntities.getChannelName().equalsIgnoreCase("sms")) {
			if (!otpEntities.getUser_id().matches("[0-9]+")) {
				throw new InvalidMobile();
			}
			if (otpEntities.getUser_id().length() != 10) {
				throw new InvalidMobile();
			}

		} 
		else 
		{
			throw new InvalidChannel();
		}
		
		
		
		otpEntities.setOtp(generateOTP());
		System.out.println(otpEntities.getOtp());
		otpEntities.setTimeStamp();
		otpDao.save(otpEntities);
		return otpEntities;


	}

	@Override
	public ResponseEntity<OTPEntities> validateOtp(String uId, String otp)
			throws NoUserException, InvalidOTPException, TimeStampExceededException {
		// TODO Auto-generated method stub
		long ts = System.currentTimeMillis();
		OTPEntities otpEntities = otpDao.findById(uId).orElseThrow(() -> new NoUserException());
		// String channelName= otpEntities.getChannelName();
		// System.out.println(otpEntities);
		if ((ts - otpEntities.getTimeStamp()) / 60000 < y) {
			if (otp.equals(otpEntities.getOtp())) {
				return ResponseEntity.ok().body(otpEntities);
			} else {
				throw new InvalidOTPException();
			}

		} else {
			throw new TimeStampExceededException();

		}

	}
	public boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mat = pattern.matcher(email);
		if (!mat.matches()) {
			return false;
		}
		return true;
	}

}
