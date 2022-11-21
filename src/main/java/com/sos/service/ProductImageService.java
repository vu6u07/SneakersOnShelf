package com.sos.service;

import com.sos.dto.ProductImageDTO;
import com.sos.entity.Product;
import com.sos.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    ProductImage save(ProductImage productImage);
    boolean saveListImages(ProductImageDTO productImage);
    List<ProductImage> findProductImageByProduct(Product product);

    ProductImage findById(int id);

    void deleteById(int id);
}
