package com.sos.service.impl;

import com.sos.entity.ProductImage;
import com.sos.repository.ProductImageRepository;
import com.sos.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }
}
