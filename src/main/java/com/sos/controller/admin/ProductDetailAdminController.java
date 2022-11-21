package com.sos.controller.admin;

import com.sos.common.SorterConstant;
import com.sos.dto.ProductDetailDTO;
import com.sos.dto.ResponseObject;
import com.sos.entity.ProductDetail;
import com.sos.util.Utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;


@RestController
@RequestMapping(value = "/admin/v1")
public class ProductDetailAdminController extends BaseController{

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/productDetails/findByProduct/{id}",params = "pages")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "pages", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sort", defaultValue = "id_asc") SorterConstant.ProductSorter sorter) {
        return ResponseEntity.ok(productService.findAll(PageRequest.of(page - 1, size, sorter.getSort())));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/productDetails/findByProduct/{id}")
    public ResponseEntity<?> getByIdProduct(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(productDetailService.findProductDetailByProductID(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "productDetail/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(productDetailService.findById(id));
    }
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping(value = "productDetail/create")
    public Mono<?> create(@RequestBody ProductDetailDTO detail) {
        System.out.println("create");
        System.out.println(detail.toString());
        try {
            ResponseObject responseInvalid = validateData.validateProductDetailDTO(detail);
            if (responseInvalid == null) {
               ProductDetail detail1 = productDetailService.CreateProductDetail(detail);
               if(detail1 != null){
                   return Mono.just(Utils.responseSuccess("tạo size"+detail.getSize()+":"+detail.getQuantity()+" thành công"));
               }
                return Mono.just(Utils.responseSuccess("tạo size"+detail.getSize()+":"+detail.getQuantity()+" không thành công"));
            } else {
                return Mono.just(responseInvalid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.just(Utils.responseUnSuccess());
        }
    }
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping(value = "productDetail/delete/{id}")
    public Mono<?> deleteById(@PathVariable(name = "id") int id) {
        try {
            productDetailService.deleteById(id);
            return Mono.just(Utils.responseSuccess("Delete product Detail"));
        }catch (Exception e){
            e.printStackTrace();
            return Mono.just(Utils.responseUnSuccess());
        }

    }
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping(value = "productDetail/update")
    public Mono<?> update(@RequestBody ProductDetail detail) {
        System.out.println("detail.toString()");
        System.out.println(detail.toString());
        ResponseObject responseInvalid = validateData.validateProductDetail(detail);
        try {
            if (responseInvalid == null) {
                productDetailService.updateQuantityById(detail);
                return Mono.just(Utils.responseSuccess("update product Detail"));
            } else {
                return Mono.just(responseInvalid);
            }
        }catch (Exception e){
            return Mono.just(Utils.responseUnSuccess());
        }
    }
}
