package com.sos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.dto.vo.ProductVO;
import com.sos.entity.Product;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.ProductDetailRepository;
import com.sos.repository.ProductImageRepository;
import com.sos.repository.ProductRepository;
import com.sos.repository.RateRepository;
import com.sos.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private RateRepository rateRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Transactional
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
	public ProductInfoDTO findProductInfoDTOById(int id) {
		ProductInfoDTO rs = productRepository.findProductInfoDTOById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
		rs.setProductImages(productImageRepository.findProductImageDTOByProductId(id));
		rs.setProductDetails(productDetailRepository.findByProductId(id, ActiveStatus.ACTIVE));
		rateRepository.findAverageScore(rs.getId()).ifPresent(aggregateData -> {
			if (aggregateData.getCount() > 0 && aggregateData.getSum() > 0) {
				rs.setScore((float) aggregateData.getSum() / aggregateData.getCount());
			}
		});
		return rs;
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable) {
		return productRepository.findCollectionProductDTO(query, brandId, categoryId, productGender, productStatus,
				pageable);
	}

	@Override
	public Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable) {
		return productRepository.findBestSellingProductDTO(query, brandId, categoryId, productGender, productStatus,
				pageable);
	}
	
	@Override
	public Product save(ProductVO productVO) {
		Date date = new Date();
		Product product = new Product();
		product.setName(productVO.getName());
		product.setDescription(productVO.getDescription());
		product.setBrand(productVO.getBrand());
		product.setCategory(productVO.getCategory());
		product.setSellPrice(productVO.getSellPrice());
		product.setOriginalPrice(productVO.getSellPrice());
		product.setProductGender(productVO.getProductGender());
		product.setProductStatus(productVO.getProductStatus());
		product.setCreateDate(date);
		product.setUpdateDate(date);
		return productRepository.save(product);
	}

}
