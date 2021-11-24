package com.rapipay.newOTPGenerate.services;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(OTPService.class);

	@Value("${app.generate}")
	public int x;
	@Value("${app.validate}")
	public int y;

	@Autowired
	private OtpDao otpDao;

	@Autowired
	private JavaMailSender sender;   //javamailsender is synchronous
	//asynchronous means running the blocks parallely

	@Override
	public String generateOTP() {
		int otp;
		Random random = new Random();
		otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);

	}

	@Override
	public OTPEntities addData(OTPEntities otpEntities)
			throws InvalidEmail, InvalidMobile, InvalidChannel, ReGenerateOTP, NullValueException {

		otpEntities.setOtp(generateOTP());
		LOGGER.info("Entered generate otp section");

		// to ensure otp is not generated before x minute
		OTPEntities oe = otpDao.findById(otpEntities.getUser_id()).orElse(null);
		//System.out.println(x + " hello");
		if (Strings.isNullOrEmpty(otpEntities.getChannelName())) {
			LOGGER.error("channel is null for the input "+otpEntities.getChannelName());
			throw new NullValueException("channel cant be null");
		}
		if (Strings.isNullOrEmpty(otpEntities.getUser_id())) {
			LOGGER.error("user id is null. entered user id is: "+otpEntities.getUser_id());
			throw new NullValueException("user id cant be null");
		}
		if (otpEntities.getOrder_id() == 0) {
			LOGGER.error("order id is null. entered order id is: "+otpEntities.getOrder_id());
			throw new NullValueException("order id cant be null");
		}
		
		if (oe != null) {
			if (((System.currentTimeMillis() - oe.getTimeStamp()) / 60000) < x) {
				LOGGER.error("can not generate otp again. otp regenerate requested at "+ System.currentTimeMillis());
				throw new ReGenerateOTP();
			}
		}
		if (otpEntities.getChannelName().equalsIgnoreCase("email")) {
			if (!checkEmail(otpEntities.getUser_id())) {
				LOGGER.error("email is invalid. entered email id is: "+otpEntities.getUser_id());
				throw new InvalidEmail();
			} else {
				LOGGER.info("mail sent");
				System.out.println(otpEntities.getUser_id());
				sendEmail(otpEntities.getUser_id(), "otp is: " + otpEntities.getOtp(), "otp");

			}

		} else if (otpEntities.getChannelName().equalsIgnoreCase("sms")) {
			if (!otpEntities.getUser_id().matches("[0-9]+")) {
				LOGGER.error("invalid mobile number. entered number is: "+ otpEntities.getUser_id());
				throw new InvalidMobile();
			}
			if (otpEntities.getUser_id().length() != 10) {
				LOGGER.error("mobile number not 10 digits. entered mobile number is: "+otpEntities.getUser_id());
				throw new InvalidMobile();
			}

		} else {
			LOGGER.error("channel name not valid. entered channel name is: "+otpEntities.getChannelName());
			throw new InvalidChannel();
		}

		System.out.println(otpEntities.getOtp());
		otpEntities.setTimeStamp();
		otpDao.save(otpEntities);
		LOGGER.info("entries saved");
		return otpEntities;

	}

	@Override
	public OTPEntities validateOtp(String uId, String otp)
			throws NoUserException, InvalidOTPException, TimeStampExceededException {
		// TODO Auto-generated method stub
		LOGGER.info("entered validate otp section");
		long ts = System.currentTimeMillis();
		OTPEntities otpEntities = otpDao.findById(uId).orElseThrow(() -> new NoUserException());
		// String channelName= otpEntities.getChannelName();
		// System.out.println(otpEntities);
		if ((ts - otpEntities.getTimeStamp()) / 60000 < y) {
			if (otp.equals(otpEntities.getOtp())) {
				LOGGER.info("otp verified succesfully");
				System.out.println("otp verified correctly");
				return otpEntities;
			} else {
				LOGGER.error("otp doesnt match. entered otp is: "+otp);
				throw new InvalidOTPException();
			}

		} else {
			LOGGER.error("time stamp exceeded. time when otp is entered is: "+System.currentTimeMillis());

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

	
	@Async
	public void sendEmail(String toEmail, String body, String subject) {
		LOGGER.info("mail sent to the user");
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("vrindakohli0909@gmail.com");
		simpleMessage.setTo(toEmail);
		simpleMessage.setText(body);
		simpleMessage.setSubject(subject);
		sender.send(simpleMessage);
		System.out.println("mail sent");

	}

}
