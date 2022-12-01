package com.sos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import com.sos.common.ApplicationConstant.NotificationTopic;
import com.sos.dto.PushNotificationRequest;
import com.sos.service.NotificationService;

@Service
public class GoogleFCMNotificationService implements NotificationService<PushNotificationRequest> {

	private Logger logger = LoggerFactory.getLogger(GoogleFCMNotificationService.class);

	@Override
	public void sendNotification(Map<String, String> data, PushNotificationRequest request) {
		Builder builder = Message.builder().setNotification(
				Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build());
		if (data != null && data.size() > 0) {
			builder.putAllData(data);
		}
		if (StringUtils.hasText(request.getTopic())) {
			builder.setTopic(request.getTopic());
		}
		if (StringUtils.hasText(request.getToken())) {
			builder.setToken(request.getToken());
		}

		logger.info("Sending notification :[title : {}, message : {}, topic : {}, token : {}]", request.getTitle(),
				request.getMessage(), request.getTopic(), request.getToken());
		FirebaseMessaging.getInstance().sendAsync(builder.build());
	}

	public void subcribeClientToTopic(List<String> token, String topic) throws FirebaseMessagingException {
		TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(token, topic);
		logger.info("Subcribe {} token to topic {}, success : {}, fail : {}", token.size(), topic,
				response.getSuccessCount(), response.getFailureCount());
	}

	public void sendNotificationOnNewOrder(String id) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("link", "/dashboard/orders/" + id);
		data.put("type", "order_placed");
		sendNotification(data,
				new PushNotificationRequest("Đơn hàng mới", "chờ bạn xác nhận", NotificationTopic.ADMIN.getTopic(), null));
	}
}