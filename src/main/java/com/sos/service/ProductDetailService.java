package com.sos.service;

import com.sos.dto.CartDTO;
import com.sos.entity.Product;
import com.sos.entity.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductDetailService {
    ProductDetail save(ProductDetail productDetail);

    List<ProductDetail> findProductDetailByProductID(Integer id);

    ProductDetail findSizeAndIdProduct(int size, Product product);
}
