package com.sos.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sos.service.TemplateService;

@RestController
@RequestMapping(value = "/api/v1/templates")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@GetMapping(value = "/staff/import-template")
	public ResponseEntity<?> getImportTemplate() throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx");
		InputStreamResource resource = templateService.getImportStaffTemplate();
		return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/staff/import-template")
	public ResponseEntity<?> postImportStaffData(@RequestPart(value = "data") MultipartFile file) throws Exception {
		templateService.importStaffTemplate(file);
		return ResponseEntity.noContent().build();
	}
}
