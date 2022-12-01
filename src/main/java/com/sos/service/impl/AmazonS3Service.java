package com.sos.service.impl;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sos.service.StorageService;

@Service
public class AmazonS3Service implements StorageService {

	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${amazon.s3.bucket.name}")
	private String bucketName;

	@Override
	public String uploadFile(MultipartFile multipartFile) throws IOException {
		String fileName = new Date().getTime() + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getSize());
		amazonS3Client
				.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata)
						.withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}

}
