package com.sos.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sos.dto.EmailRequest;
import com.sos.service.EmailService;

@Service
public class GoogleSMTPEmailServiceImpl implements EmailService {

	private static Logger logger = LoggerFactory.getLogger(GoogleSMTPEmailServiceImpl.class);

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Validator validator;
	
	@Value("${service.smtp.email.address}")
	private String emailAddress;
	
	@Value("${service.smtp.email.name}")
	private String emailName;
	

	@Override
	public void sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
		Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);
		if (!violations.isEmpty()) {
			String errorsMsg = violations.stream().map(ConstraintViolation<EmailRequest>::getMessage)
					.collect(Collectors.joining(","));
			throw new ValidationException(errorsMsg);
		}

		Session session = Session.getInstance(new Properties(System.getProperties()));
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setSubject(emailRequest.getSubject(), "UTF-8");
		mimeMessage.setFrom(new InternetAddress(emailAddress, emailName));

		Address[] addresses = new Address[emailRequest.getTo().length];
		for (int i = 0; i < emailRequest.getTo().length; i++) {
			addresses[i] = new InternetAddress(emailRequest.getTo()[i]);
		}
		mimeMessage.setRecipients(RecipientType.TO, addresses);

		if (emailRequest.getCc() != null) {
			addresses = new Address[emailRequest.getCc().length];
			for (int i = 0; i < emailRequest.getCc().length; i++) {
				addresses[i] = new InternetAddress(emailRequest.getCc()[i]);
			}
			mimeMessage.setRecipients(RecipientType.CC, addresses);
		}

		if (emailRequest.getBcc() != null) {
			addresses = new Address[emailRequest.getBcc().length];
			for (int i = 0; i < emailRequest.getBcc().length; i++) {
				addresses[i] = new InternetAddress(emailRequest.getBcc()[i]);
			}
			mimeMessage.setRecipients(RecipientType.BCC, addresses);
		}

		MimeMultipart mp = new MimeMultipart();
		BodyPart part = new MimeBodyPart();
		if (emailRequest.isHtml()) {
			part.setContent(emailRequest.getBody(), "text/html; charset=utf-8");
		} else {
			part.setText(emailRequest.getBody());
		}
		mp.addBodyPart(part);
		mimeMessage.setContent(mp);
		emailSender.send(mimeMessage);
		logger.info("Sent email {}", emailRequest.getSubject());
	}

}
