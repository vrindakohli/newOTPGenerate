package com.rapipay.newOTPGenerate;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@SpringBootApplication
@EnableAsync
public class NewOtpGenerateApplication {
	
	@Bean  
	public ModelMapper modelMapper() {
		//configure modelmapper class as spring bean so that we can use it in the controller.
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(NewOtpGenerateApplication.class, args);
	}
	

}


//logging improve
//try-catch block
//junit- code coverage

//asynchronous email