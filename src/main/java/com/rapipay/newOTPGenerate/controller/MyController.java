package com.rapipay.newOTPGenerate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapipay.newOTPGenerate.dto.RequestDto;
import com.rapipay.newOTPGenerate.dto.ResponseDto;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RequestMapping("/api/v1.1/otp")
@RestController
@Api(value = "/api/v1.1/otp", tags = "otp generation")
public class MyController implements ErrorController {
	//private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	@Autowired
	ModelMapper modelMapper; //entity to DTO conversion
	
	@Autowired
	private OTPServiceInterface otpService;
	
	/*@GetMapping("/genOtp")
	public String generateOTP() {
		return this.otpService.generateOTP();
	}*/
	private ResponseDto responseDto;
		
	@PostMapping(path="/addData")
	@ApiOperation(value = "generate otp", notes = "input user data to generate otp")
	public ApplicationResponseEntity<ResponseDto> addDataInDb(@RequestBody RequestDto requestDto) throws Exception {
			try {
				//dto to entity
				OTPEntities otpEntities= modelMapper.map(requestDto, OTPEntities.class); //maps dto to otpentities
				otpService.addData(otpEntities);
				
				//entity to dto in return
				return new ApplicationResponseEntity<>("200", "successfull", modelMapper.map(otpEntities, ResponseDto.class));
			} 
			catch (InvalidEmail | InvalidMobile | InvalidChannel | ReGenerateOTP | NullValueException e) {
				// TODO Auto-generated catch block
				throw e;


			}
			
			
		
		}
	
	@GetMapping("/validate/{user_id}/{otp}")
	@ApiOperation(value = "validate otp", notes = "input email/sms ie. your user id and the otp to validate otp")

	public ApplicationResponseEntity<ResponseDto>validateOTP(@PathVariable String user_id, @PathVariable String otp) {
		//System.out.println(user_id+""+otp);
	
			try {
				OTPEntities otpEntities=otpService.validateOtp(user_id, otp);
				return new ApplicationResponseEntity<>("200", "succesfull", modelMapper.map(otpEntities, ResponseDto.class));
			} catch (NoUserException | InvalidOTPException | TimeStampExceededException e) {
				// TODO Auto-generated catch block
				return new ApplicationResponseEntity<>("400", e.toString(), null);
			}
		
	}
	
}
