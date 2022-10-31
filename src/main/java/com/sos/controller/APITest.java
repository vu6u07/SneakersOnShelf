package com.sos.controller;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.sos.common.ApplicationConstant;
import com.sos.dto.NewEmployee;
import com.sos.dto.ProductInfoDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class APITest {
    public static void main(String[] args)
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\admin\\Desktop\\Bien-ban-hop\\PRODUCT-list1.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            ArrayList<ProductInfoDTO> productInfoDTOS = new ArrayList<>();
            //I've Header and I'm ignoring header for that I've +1 in loop
            for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                ProductInfoDTO productInfO = new ProductInfoDTO();
                Row ro = sheet.getRow(i);
                for(int j= ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                    Cell ce = ro.getCell(j);
                    switch ( j ) {
                        case 0:
                            productInfO.setName(String.valueOf(ce));
                            break;
                        case 1:
                            productInfO.setProductGender(ApplicationConstant.ProductGender.valueOf(String.valueOf(ce)));
                            break;
                        case 2:
                            productInfO.setBrand(String.valueOf(ce));
                            break;
                        case 3:
                            productInfO.setCategory(String.valueOf(ce));
                            break;
                        case 4:
                            productInfO.setSellPrice((long) ce.getNumericCellValue());
                            break;
                        case 5:
                            productInfO.setOriginalPrice((long) ce.getNumericCellValue());
                            break;
                        case 6:
                            productInfO.setDescription(String.valueOf(ce));
                            break;
                        case 7:
                            productInfO.setImportPrice((long) ce.getNumericCellValue());
                            break;

                    }
                }
                productInfoDTOS.add(productInfO);

            }
            for(ProductInfoDTO pro: productInfoDTOS){
                System.out.println(pro.toString());
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    private static void putValuesProduct(Product product, Cell cell, String stringCellValue) {
//        if(cell.getStringCellValue().equals("Name")){
//            product.setName(stringCellValue);
//        } else if (cell.getStringCellValue().equals("ProductGender")) {
//            product.setProductGender(ApplicationConstant.ProductGender.valueOf(stringCellValue));
//        }else if (cell.getStringCellValue().equals("SellPrice")) {
//            product.setSellPrice(Long.parseLong(stringCellValue));
//        }else if (cell.getStringCellValue().equals("OriginalPrice")) {
//            product.setOriginalPrice(Long.parseLong(stringCellValue));
//        }else if (cell.getStringCellValue().equals("Description")) {
//            product.setDescription(String.valueOf(stringCellValue));
//        }else if (cell.getStringCellValue().equals("ImportPrice")) {
//            product.setImportPrice(Long.parseLong(stringCellValue));
//        }
//    }

}
