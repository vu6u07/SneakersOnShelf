package com.sos.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class GoogleFirebaseCloudMessageConfig {

	private static Logger logger = LoggerFactory.getLogger(GoogleFirebaseCloudMessageConfig.class);

	@Value("${google.firebase.cloud-message.config.file.path}")
	private String firebaseConfigFilePath;

	@PostConstruct
	private void initialize() {
		try {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigFilePath).getInputStream()))
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				logger.info("firebase application has been initialized");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
