package com.rapipay.newOTPGenerate.dto;

import lombok.Data;

//dto class- data transfer object
//it comes in between the client and the controller.
@Data
public class ResponseDto {
	private String user_id;
	private long order_id;
	private String channelName;
	private long timeStamp;

}
