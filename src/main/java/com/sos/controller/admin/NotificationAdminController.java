package com.sos.controller.admin;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sos.common.ApplicationConstant.NotificationTopic;
import com.sos.service.impl.GoogleFCMNotificationService;

@RestController
@RequestMapping(value = "/admin/v1")
public class NotificationAdminController {

	@Autowired
	private GoogleFCMNotificationService notificationService;
	
	@PostMapping(value = "/notifications/subscribe")
	public ResponseEntity<?> subscribe(@RequestBody String token) throws FirebaseMessagingException {
		notificationService.subcribeClientToTopic(Collections.singletonList(token), NotificationTopic.ADMIN.getTopic());
		return ResponseEntity.noContent().build();
	}

}
