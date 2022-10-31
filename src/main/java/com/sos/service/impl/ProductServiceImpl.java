package com.sos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sos.entity.ProductDetail;
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
import org.springframework.util.CollectionUtils;

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
		ProductInfoDTO productInfoDTO = findProductInfoDTOByName(entity.getName());
		if(productInfoDTO != null){
			System.out.println("Update product");
			entity.setId(productInfoDTO.getId());
			return productRepository.save(entity);
		}
		System.out.println("Save product");
		return productRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
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

	@Override
	public ProductInfoDTO findProductInfoDTOByName(String name) {
		Product rs = productRepository.findProductByName(name);
		if(rs != null){
			ProductInfoDTO dto = new ProductInfoDTO();
			dto.setId(rs.getId());
			dto.setName(rs.getName());
			dto.setProductGender(rs.getProductGender());
			dto.setDescription(rs.getDescription());
			dto.setProductImages(productImageRepository.findProductImageDTOByProductId(rs.getId()));
			dto.setProductDetails(productDetailRepository.findByProductId(rs.getId()));
			dto.setBrand(rs.getBrand().getName());
			dto.setCategory(rs.getCategory().getName());
			dto.setImportPrice(rs.getImportPrice());
			dto.setOriginalPrice(rs.getOriginalPrice());
			dto.setSellPrice(rs.getSellPrice());
			dto.setCreateDate(rs.getCreateDate());
			dto.setUpdateDate(rs.getUpdateDate());
			return dto;
		}
		return null;
	}
}
