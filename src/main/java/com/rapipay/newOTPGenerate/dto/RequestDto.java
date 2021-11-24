package com.rapipay.newOTPGenerate.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

//dto class- data transfer object
//it comes in between the client and the controller.
@Data
public class RequestDto {
	@NotBlank
	private String user_id;
	@NotBlank
	private long order_id;
	@NotBlank
	private String channelName;

}
