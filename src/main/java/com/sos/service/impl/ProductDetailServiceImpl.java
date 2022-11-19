package com.sos.service.impl;

import com.sos.dto.ProductDetailDTO;
import com.sos.entity.Product;
import com.sos.entity.ProductDetail;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.ProductDetailService;
import com.sos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ProductService productService;

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail CreateProductDetail(ProductDetailDTO detailDTO) {
        try {
            Product product = productService.findProductById(detailDTO.getIdProduct());
            ProductDetail detail = new ProductDetail();
            detail.setProduct(product);
            detail.setQuantity(Integer.parseInt(detailDTO.getQuantity()));
            detail.setSize(detailDTO.getSize());
            productDetailRepository.save(detail);
            return detail;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public int updateQuantityById(ProductDetail detail) {
        return productDetailRepository.updateQuantityById(detail.getQuantity(),detail.getId());
    }

    @Override
    public List<ProductDetail> findProductDetailByProductID(Integer id) {
        return productDetailRepository.findByProductId(id);
    }

    @Override
    public ProductDetail findSizeAndIdProduct(int size, Product product) {
        return productDetailRepository.findByProductIdAAndSize(product,String.valueOf(size));
    }

    @Override
    public ProductDetail findById(int id) {
        return productDetailRepository.findById(id);
    }
    @Transactional
    @Override
    public void deleteById(int id) {
        productDetailRepository.deleteProductDetailById(id);
    }


}
