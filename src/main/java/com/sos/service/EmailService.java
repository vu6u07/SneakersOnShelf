package com.sos.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.sos.dto.EmailRequest;

public interface EmailService {

	void sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException;

}
