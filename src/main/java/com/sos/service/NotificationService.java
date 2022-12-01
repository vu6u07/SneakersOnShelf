package com.sos.service;

import java.util.Map;

public interface NotificationService<T> {

	void sendNotification(Map<String, String> data, T message);
	
}
