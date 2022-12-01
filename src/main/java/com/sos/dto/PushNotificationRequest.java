package com.sos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushNotificationRequest {

	private String title;
	
	private String message;
	
	private String topic;
	
	private String token;

}