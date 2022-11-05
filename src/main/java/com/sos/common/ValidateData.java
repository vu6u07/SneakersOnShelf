package com.sos.common;

import com.sos.dto.ResponseObject;
import com.sos.entity.Product;
import com.sos.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidateData {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateData.class);
    public static ResponseObject validateDataObject(Product product) {
        LOGGER.info("Start Validate Product");
        LOGGER.info(product.getName());
        if (Commons.isNullOrEmpty(product.getName())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Name");
            return responseObject;
        }
        LOGGER.info(product.getDescription());
        if (Commons.isNullOrEmpty(product.getDescription())) {
            ResponseObject responseObject = Utils.responseValidateInputData( "Description");
            return responseObject;
        }
        LOGGER.info(String.valueOf(product.getProductGender()));
        if (Commons.isNullOrEmpty(String.valueOf(product.getProductGender()))) {
            ResponseObject responseObject = Utils.responseValidateInputData("Gender");
            return responseObject;
        }
        LOGGER.info(String.valueOf(product.getImportPrice()));
        if (Commons.isNullOrEmptyNumberTypeLong(product.getImportPrice())) {
            ResponseObject responseObject = Utils.responseValidateInputData( "ImportPrice null");
            return responseObject;
        }
        LOGGER.info(String.valueOf(product.getOriginalPrice()));
        if (Commons.isNullOrEmptyNumberTypeLong(product.getOriginalPrice())) {
            ResponseObject responseObject = Utils.responseValidateInputData("OriginalPrice null");
            return responseObject;
        }
        LOGGER.info(String.valueOf(product.getSellPrice()));
        if (Commons.isNullOrEmptyNumberTypeLong(product.getSellPrice())) {
            ResponseObject responseObject = Utils.responseValidateInputData("SellPrice null");
            return responseObject;
        }
        LOGGER.info(product.getBrand().toString());
        if (product.getBrand()== null) {
            ResponseObject responseObject = Utils.responseValidateInputData("Brand");
            return responseObject;
        }
        LOGGER.info(product.getCategory().toString());
        if (product.getCategory()== null) {
            ResponseObject responseObject = Utils.responseValidateInputData("Category");
            return responseObject;
        }
        LOGGER.info("End Validate Product");
        return null;
    }

    public static ResponseObject validateDeleteProduct(Integer id) {
        LOGGER.info("Start Validate Product detete");
        LOGGER.info("Product datete by id: "+id);
        if (Commons.isNullOrEmptyNumber(id)) {
            ResponseObject responseObject = Utils.responseValidateInputData("id trống");
            return responseObject;
        }
        LOGGER.info("End Validate Product");
        return null;
    }

    public static ResponseObject validateFileDataImportProduct(MultipartFile[] files) {
        LOGGER.info("Start Validate File Data Import Product");
        if (Commons.isNullOrEmptyFile(files)) {
            ResponseObject responseObject = Utils.responseValidateInputData("File data không có dữ liệu");
            return responseObject;
        }
        LOGGER.info("End Validate Product");
        return null;
    }
}
