package com.sos.common;

import com.sos.dto.*;
import com.sos.entity.Product;
import com.sos.entity.ProductDetail;
import com.sos.service.ProductDetailService;
import com.sos.service.ProductService;
import com.sos.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidateData {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService detailService;

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
        if (product.getBrand() == null) {
            ResponseObject responseObject = Utils.responseValidateInputData("Brand");
            return responseObject;
        }
        LOGGER.info(product.getCategory().toString());
        if (product.getCategory() == null) {
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
    public static ResponseObject validateProductDetail(ProductDetail productDetail) {
        LOGGER.info("Start Validate ProductDetail");
        if (Commons.isNullOrEmptyNumber(productDetail.getId())) {
            ResponseObject responseObject = Utils.responseValidateInputData("id update Detail trống");
            return responseObject;
        }
        if (Commons.isNullOrEmpty(productDetail.getSize())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Detail Size trống");
            return responseObject;
        }
        if (Commons.isNullOrEmptyNumber(productDetail.getQuantity())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Detail Size trống");
            return responseObject;
        }
        LOGGER.info("End Validate ProductDetail");
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

    public ResponseObject validateProductCrudDTOObject(ProductCrudDTO product) {
        LOGGER.info("Start Validate Product");
        LOGGER.info(product.getName());
        if (Commons.isNullOrEmpty(product.getName())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Name");
            return responseObject;
        }
        ProductInfoDTO infoDTO = productService.findProductInfoDTOByName(product.getName());
        LOGGER.info(product.getDescription());
        if (infoDTO != null) {
            ResponseObject responseObject = Utils.responseValidateInputData("Name đã tồn tại");
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
        if (product.getBrand()== null ) {
            ResponseObject responseObject = Utils.responseValidateInputData("Brand");
            return responseObject;
        }
        LOGGER.info(product.getCategory().toString());
        if (product.getCategory()== null) {
            ResponseObject responseObject = Utils.responseValidateInputData("Category");
            return responseObject;
        }
        LOGGER.info("ProductImage"+product.getProductImage().size()+"");
        if (product.getProductImage().isEmpty() || product.getProductImage().size() == 0 ) {
            ResponseObject responseObject = Utils.responseValidateInputData("Product Image");
            return responseObject;
        }
        if (product.getProductImage().equals("")) {
            ResponseObject responseObject = Utils.responseValidateInputData("Product Image not null");
            return responseObject;
        }
        LOGGER.info(product.getSize35());
        if (!Commons.isNumeric(product.getSize35())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size35 Erro value ");
            return responseObject;
        }
        LOGGER.info(product.getSize36());
        if (!Commons.isNumeric(product.getSize36())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size36 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize37());
        if (!Commons.isNumeric(product.getSize37())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size37 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize38());
        if (!Commons.isNumeric(product.getSize38())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size38 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize39());
        if (!Commons.isNumeric(product.getSize39())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size39 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize40());
        if (!Commons.isNumeric(product.getSize40())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size40 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize41());
        if (!Commons.isNumeric(product.getSize41())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size41 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize42());
        if (!Commons.isNumeric(product.getSize42())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size42 Erro value");
            return responseObject;
        }
        LOGGER.info(product.getSize43());
        if (!Commons.isNumeric(product.getSize43())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size42 Erro value");
            return responseObject;
        }
        LOGGER.info("End Validate Product");
        return null;
    }

    public  ResponseObject validateProductDetailDTO(ProductDetailDTO detail) {
        LOGGER.info("Start Validate ProductDetail");
        if (Commons.isNullOrEmptyNumber(detail.getIdProduct())) {
            ResponseObject responseObject = Utils.responseValidateInputData("bạn chưa chọn sản phẩm cần thêm size");
            return responseObject;
        }
        Product product = productService.findProductById(detail.getIdProduct());
        if(product == null){
            ResponseObject responseObject = Utils.responseValidateInputData("Sản phẩm không hợp lệ");
            return responseObject;
        }
        if (Commons.isNullOrEmpty(detail.getSize())) {
            ResponseObject responseObject = Utils.responseValidateInputData("bạn chưa nhập size");
            return responseObject;
        }
        if (!Commons.isNumericSize(detail.getSize())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Size phải là số và lớn hơn 35 nhỏ hơn 43");
            return responseObject;
        }

        ProductDetail detail1 = detailService.findSizeAndIdProduct(Integer.parseInt(detail.getSize()),product);
        if(detail1 != null){
            ResponseObject responseObject = Utils.responseValidateInputData("Size đã tồn tại");
            return responseObject;
        }

        if (Commons.isNullOrEmpty(detail.getQuantity())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Bạn chưa nhập số lượng");
            return responseObject;
        }
        if (!Commons.isNumeric(detail.getQuantity())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Số lượng phải là số và lớn hơn 0");
            return responseObject;
        }
        LOGGER.info("End Validate ProductDetail");
        return null;
    }

    public ResponseObject validateProductImageDTO(ProductImageDTO productImage) {
        LOGGER.info("Start Validate Product Image DTO");
        if (Commons.isNullOrEmptyNumber(productImage.getIdproduct())) {
            ResponseObject responseObject = Utils.responseValidateInputData("Bạn chưa chọn sản phẩm cần thêm ảnh");
            return responseObject;
        }

        Product product = productService.findProductById(productImage.getIdproduct());
        if(product == null){
            ResponseObject responseObject = Utils.responseValidateInputData("Sản phẩm không tô tại");
            return responseObject;
        }
        LOGGER.info("Images: "+productImage.getImage());
        if (productImage.getImage().isEmpty()) {
            ResponseObject responseObject = Utils.responseValidateInputData("Bạn chưa chọn ảnh");
            return responseObject;
        }
        LOGGER.info("End Validate Product Image DTO");
        return null;
    }

}
