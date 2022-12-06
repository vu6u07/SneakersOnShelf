package com.sos.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sos.dto.CreateAccountRequestDTO;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.AuthenticationService;
import com.sos.service.TemplateService;
import com.sos.service.util.ValidationUtil;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Validator validator;

	// Staff File
	@Override
	public InputStreamResource getImportStaffTemplate() {
		InputStream is = null;
		try {
			is = new ClassPathResource("excel-template/IMPORT_STAFF_TEMPLATE.xlsx").getInputStream();
		} catch (IOException e) {
			throw new ResourceNotFoundException("Template not found");
		}
		InputStreamResource resource = new InputStreamResource(is);
		return resource;
	}

	@SuppressWarnings("resource")
	@Override
	public void importStaffTemplate(MultipartFile multipartFile) throws Exception {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
		} catch (IOException e) {
			throw new ValidationException("File tải lên không hợp lệ.");
		}
		List<CreateAccountRequestDTO> accounts = new ArrayList<CreateAccountRequestDTO>();

		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			CreateAccountRequestDTO car = new CreateAccountRequestDTO(row.getCell(0).getStringCellValue(),
					row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), true);
			Set<ConstraintViolation<CreateAccountRequestDTO>> violations = validator.validate(car);
			if (!violations.isEmpty()) {
				String errorsMsg = violations.stream().map(ConstraintViolation<CreateAccountRequestDTO>::getMessage)
						.collect(Collectors.joining(", "));
				throw new ValidationException(String.format("STT : %s, Lỗi : %s", row.getRowNum(), errorsMsg));
			}
			ValidationUtil.validateUsername(car.getUsername());
			ValidationUtil.validateEmail(car.getEmail());
			accounts.add(car);
		}
		authenticationService.signup(accounts);
	}
}
