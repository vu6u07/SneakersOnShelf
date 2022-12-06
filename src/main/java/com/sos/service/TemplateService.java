package com.sos.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface TemplateService {

	// excel
	InputStreamResource getImportStaffTemplate();

	void importStaffTemplate(MultipartFile multipartFile) throws Exception;

}
