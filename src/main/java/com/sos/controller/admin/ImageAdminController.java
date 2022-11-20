package com.sos.controller.admin;

import com.sos.dto.ProductImageDTO;
import com.sos.dto.ResponseObject;
import com.sos.entity.Product;
import com.sos.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/admin/v1/images")
public class ImageAdminController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(BrandRestController.class);

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(productImageService.findById(id));
    }

  //  @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "findByProductId/{id}")
    public ResponseEntity<?> getImageByProduct(@PathVariable(name = "id") int id) {
        try {
            Product product = productService.findProductById(id);
            if(product == null){
                return ResponseEntity.ok(Utils.responseValidateInputData("Product Trông tồn tại"));
            }
            return ResponseEntity.ok(productImageService.findProductImageByProduct(product));
        }catch (Exception e){
            return ResponseEntity.ok(Utils.responseValidateInputData("Erro"));
        }
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") int id) {
        logger.info("delete images By Id "+id);
        try {
            productImageService.deleteById(id);
            return ResponseEntity.ok(Utils.responseSuccess("delete "));
        }catch (Exception e){
            return ResponseEntity.ok(Utils.responseUnSuccess());
        }
    }

    @PostMapping(value = "/add")
    public Mono<?> add(@RequestBody ProductImageDTO productImage) {
        logger.info("add images ");
        try {
            logger.info("Id product images "+productImage.getIdproduct());
            ResponseObject responseInvalid = validateData.validateProductImageDTO(productImage);
            if(responseInvalid == null){
                boolean save = productImageService.saveListImages(productImage);
                if(save){
                    return Mono.just(Utils.responseSuccess("thêm ảnh thành công "));
                }
            }
            return Mono.just(responseInvalid);
        }catch (Exception e){
            e.printStackTrace();
            return Mono.just(Utils.responseUnSuccess());
        }
    }

}
