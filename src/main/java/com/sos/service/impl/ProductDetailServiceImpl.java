package com.sos.service.impl;

import com.sos.entity.Product;
import com.sos.entity.ProductDetail;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Override
    public ProductDetail save(ProductDetail productDetail) {

        return productDetailRepository.save(productDetail);
    }

    @Override
    public List<ProductDetail> findProductDetailByProductID(Integer id) {
        return productDetailRepository.findByProductId(id);
    }

    @Override
    public ProductDetail findSizeAndIdProduct(int size, Product product) {
        return productDetailRepository.findByProductIdAAndSize(product,String.valueOf(size));
    }

}
