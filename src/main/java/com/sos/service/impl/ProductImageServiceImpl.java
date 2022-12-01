package com.sos.service.impl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sos.entity.Product;
import com.sos.entity.ProductImage;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.ProductImageRepository;
import com.sos.service.ProductImageService;
import com.sos.service.StorageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private StorageService storageService;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<ProductImage> findByProductId(int id) {
		return productImageRepository.findByProductId(id);
	}

	@Transactional
	@Override
	public ProductImage uploadProductImage(int productId, MultipartFile file) throws IOException {
		String image = storageService.uploadFile(file);
		ProductImage productImage = new ProductImage();
		productImage.setImage(image);
		productImage.setProduct(new Product(productId));
		productImageRepository.save(productImage);
		productImageRepository.setDefaultProductImageIfNull(productId, productImage);
		return productImage;
	}

	@Transactional
	@Override
	public void setDefaultProductImage(int id) {
		int productId = productImageRepository.getProductIdByProductImageId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		productImageRepository.setDefaultProductImage(productId, new ProductImage(id));
	}

	@Override
	public void delete(int id) {
		productImageRepository.deleteById(id);
	}	
}
