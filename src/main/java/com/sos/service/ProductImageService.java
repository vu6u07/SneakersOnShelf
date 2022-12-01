package com.sos.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sos.entity.ProductImage;

public interface ProductImageService {
	
	List<ProductImage> findByProductId(int id);

	ProductImage uploadProductImage(int productId, MultipartFile file) throws IOException;
	
	void delete(int id);
	
	void setDefaultProductImage(int id);
	
}
