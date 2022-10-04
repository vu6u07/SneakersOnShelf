package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.ProductDetailRepository;
import com.sos.repository.ProductImageRepository;
import com.sos.repository.ProductRepository;
import com.sos.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable) {
		return productRepository.findCollectionProductDTO(pageable);
	}

	@Override
	public ProductInfoDTO findProductInfoDTOById(int id) {
		ProductInfoDTO rs = productRepository.findProductInfoDTOById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
		rs.setProductImages(productImageRepository.findProductImageDTOByProductId(id));
		rs.setProductDetails(productDetailRepository.findByProductId(id));
		return rs;
	}
}
