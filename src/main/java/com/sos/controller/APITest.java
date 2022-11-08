package com.sos.controller;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.sos.common.ApplicationConstant;
import com.sos.dto.NewEmployee;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Account;
import com.sos.service.AccountService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class APITest {

    public static void main(String[] args) {

        String[] c = "100000.0".split("\\.");
        Long b = Long.parseLong(c[0]);
        System.out.println(b);
    }
}
