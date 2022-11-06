package com.sos.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

	@NotNull(message = "email to is null")
	private String[] to;

	private String[] cc;

	private String[] bcc;

	@NotNull(message = "email subject is null")
	private String subject;

	@NotNull(message = "email body is null")
	private String body;

	private boolean isHtml;
	
}
