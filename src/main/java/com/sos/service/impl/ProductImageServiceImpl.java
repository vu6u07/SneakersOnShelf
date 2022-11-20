package com.sos.service.impl;

import com.sos.dto.ProductImageDTO;
import com.sos.entity.Product;
import com.sos.entity.ProductImage;
import com.sos.repository.ProductImageRepository;
import com.sos.service.ProductImageService;
import com.sos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductService productService;

    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public boolean saveListImages(ProductImageDTO productImage) {
        try {
            Product product = productService.findProductById(productImage.getIdproduct());
            List<String> images = productImage.getImage();
            List<ProductImage> productImages = new ArrayList<>();
            System.out.println("size"+images.size());
            if(product != null && !images.isEmpty()){
                for (String img :images) {
                    System.out.println("img"+img);
                    ProductImage image = new ProductImage();
                    image.setImage(img);
                    image.setProduct(product);
                    productImages.add(image);
                }
                productImageRepository.saveAll(productImages);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<ProductImage> findProductImageByProduct(Product product) {
        return productImageRepository.findProductImageByProduct(product);
    }

    @Override
    public ProductImage findById(int id) {
        return productImageRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        productImageRepository.deleteById(id);
    }
}
