package com.rapipay.newOTPGenerate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rapipay.newOTPGenerate.dao.OtpDao;
import com.rapipay.newOTPGenerate.entities.OTPEntities;
import com.rapipay.newOTPGenerate.exception.InvalidChannel;
import com.rapipay.newOTPGenerate.exception.InvalidEmail;
import com.rapipay.newOTPGenerate.exception.InvalidMobile;
import com.rapipay.newOTPGenerate.exception.NoUserException;
import com.rapipay.newOTPGenerate.exception.ReGenerateOTP;
import com.rapipay.newOTPGenerate.exception.TimeStampExceededException;
import com.rapipay.newOTPGenerate.services.OTPService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OtpNewServiceTest {
	
	@Autowired
	OtpDao dao;
	@Autowired
	OTPService otpService;
	

	OTPEntities otp1= new OTPEntities("1234567890","mob",1);
	OTPEntities otp2= new OTPEntities("1122222222", 1, "sms", "123456", 1234);
	OTPEntities otp3= new OTPEntities("1234567890", 1, "sms", "123456", 1234);
	OTPEntities otp4= new OTPEntities("1234567890", 1, "sms", "123456", 1234);
	OTPEntities otp5= new OTPEntities("1234567890", 1, "sms", "123456", 1234);
	OTPEntities otp6= new OTPEntities("1234567890", 1, "sms", "123456", 1234);

	
	@Test
	public void testGenerate() throws Exception {
		
		
		try {
			assertEquals(otp1.getUser_id(), otpService.addData(otp1));
			Assertions.assertThrows(Exception.class, ()->{
				dao.save(otp2);
			});
			assertEquals(otp2.getUser_id(), otpService.addData(otp1));
			Assertions.assertThrows(InvalidEmail.class, ()->
			{
				otpService.addData(otp2);
			});
			assertEquals(otp3.getUser_id(), otpService.addData(otp1));

			Assertions.assertThrows(InvalidMobile.class, ()->{
				otpService.addData(otp3);
			});
			
			//assertEquals(otp4.getUser_id(), otpService.addData(otp1));
			Assertions.assertThrows(Exception.class, ()->{
				otpService.addData(otp4);
			});
			
			
		}
		catch (Exception e) {
			throw e;
		}
			// TODO: handle exception
		}
		
	
	
	@Test
	public void validateOTP() {
		OTPEntities otpEntities= new OTPEntities();
		OTPService otpService= new OTPService();
	
//		try {
//			otpService.addData(otp5);
//			assertEquals(true, otpService.validateOtp(otp5.getUser_id(), otp5.getOtp()));
//			otp1.setOtp("1234");
//			assertEquals(false, otpService.validateOtp(otp1.getUser_id(), otp1.getOtp()));
//			Assertions.assertThrows(NoUserException.class, ()->{
//				otpService.validateOtp(otp5.getUser_id(), otp5.getOtp());
//			});
//			
//			Assertions.assertThrows(ReGenerateOTP.class, ()->{
//				otpService.validateOtp(otp5.getUser_id(), otp5.getOtp());
//			});
//			
//			Assertions.assertThrows(TimeStampExceededException.class, ()->{
//				otpService.validateOtp(otp5.getUser_id(), otp5.getOtp());
//			});
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//		}
			
	}			
			
@Test
	@DisplayName("validate email")
	public void testcheckEmail() {
		OTPService otpService= new OTPService();

		boolean expected=true;
		boolean actual=otpService.checkEmail("abc@gmail.com");
		assertEquals(expected, actual);
		
	}
	
	@Test
	@DisplayName("validate email2")
	public void testcheckEmail2() {
		OTPService otpService= new OTPService();

		boolean expected=false;
		boolean actual=otpService.checkEmail("abc@com");
		assertEquals(expected, actual);
		
	}
	
	@Test
	@DisplayName("validate otp")
	public void testOTP() {
		OTPService otpService= new OTPService();

		String expected="123456";
		String actual=otpService.generateOTP();
		assertEquals(expected.length(), actual.length());
		
	}


	@Test
	public void testotp() {
		OTPService otpService= new OTPService();

		OTPEntities otpentities=new OTPEntities("1234567890", 1, "sms", "123456", 1234);
		assertThrows(Exception.class, ()->{
			otpService.addData(otpentities);
		});
	}
	
	@Test
	public void verifyOtp() {
		OTPService otpService= new OTPService();

		OTPEntities otpentities=new OTPEntities("1234567890", 1, "sms", "123456", 1234);
		assertThrows(Exception.class, ()->{
			otpService.addData(otpentities);
		});
		
	}
	
	@Test
	public void invalidChannel() {
		OTPService otpServices=new OTPService();
		//OTPEntities otpentities=new OTPEntities("7777666444", 1, "mobile", "123456", 1234);
		OTPEntities oe= new OTPEntities();
		oe.setOrder_id(10);
		oe.setUser_id("9898090898");
		oe.setChannelName("mobile");
		oe.setOtp(otpServices.generateOTP());
		oe.setTimeStamp();
		assertThrows(Exception.class, ()->{
			otpServices.addData(oe);
		});
	}
	
	@Test
	public void invalidMobile() {
		OTPService otpServices=new OTPService();
		OTPEntities otpentities=new OTPEntities("12390", 1, "sms", "123456", 1234);
		assertThrows(Exception.class, ()->{
			otpServices.addData(otpentities);
		});
		
	}
	
	@Test
	public void invalidEmail() {
		OTPService otpServices=new OTPService();
		OTPEntities otpentities=new OTPEntities("vrinda", 1, "email", "123456", 1234);
		assertThrows(Exception.class, ()->{
			otpServices.addData(otpentities);
		});
		
	}
	
	
}