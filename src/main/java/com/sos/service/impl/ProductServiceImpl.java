package com.sos.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.common.ApplicationConstant.Benefit;
import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.common.ApplicationConstant.ShoeFeel;
import com.sos.common.ApplicationConstant.ShoeHeight;
import com.sos.common.ApplicationConstant.Surface;
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

	// for singleton param
	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			Integer colorId, Integer soleId, Integer materialId, ShoeHeight shoeHeight, Benefit benefit,
			ShoeFeel shoeFeel, Surface surface, ProductGender productGender, ProductStatus productStatus,
			Pageable pageable) {
		return productRepository.findCollectionProductDTO(
				StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, colorId, soleId,
				materialId, shoeHeight, benefit, shoeFeel, surface, productGender, productStatus, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			Integer colorId, Integer soleId, Integer materialId, ShoeHeight shoeHeight, Benefit benefit,
			ShoeFeel shoeFeel, Surface surface, ProductGender productGender, ProductStatus productStatus,
			Pageable pageable) {
		return productRepository.findBestSellingProductDTO(
				StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, colorId, soleId,
				materialId, shoeHeight, benefit, shoeFeel, surface, productGender, productStatus, pageable);
	}

	// for collection param
	// @formatter:off
	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(String query, Long minPrice, Long maxPrice, String sizeName, String brandId, String categoryId,
			String colorId, String soleId, String materialId, String shoeHeight, String benefit,
			String shoeFeel, String surface, String productGender, ProductStatus productStatus,
			Pageable pageable) {
		return productRepository.findCollectionProductDTO(
						StringUtils.hasText(query) ? "%".concat(query).concat("%") : null,
						minPrice, maxPrice,
						StringUtils.hasText(sizeName) ? Arrays.stream(sizeName.split(",")).collect(Collectors.toList()) : null,
						getCollectionIntegerParams(brandId),
						getCollectionIntegerParams(categoryId),
						getCollectionIntegerParams(colorId),
						getCollectionIntegerParams(soleId),
						getCollectionIntegerParams(materialId),
						StringUtils.hasText(shoeHeight) ? Arrays.stream(shoeHeight.split(",")).map(ShoeHeight::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(benefit) ? Arrays.stream(benefit.split(",")).map(Benefit::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(shoeFeel) ? Arrays.stream(shoeFeel.split(",")).map(ShoeFeel::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(surface) ? Arrays.stream(surface.split(",")).map(Surface::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(productGender) ? Arrays.stream(productGender.split(",")).map(ProductGender::valueOf).collect(Collectors.toList()) : null,
						productStatus, pageable);
	}
	// @formatter:on

	// @formatter:off
	@Override
	public Page<CollectionProductDTO> findBestSellingProductDTO(String query, Long minPrice, Long maxPrice, String sizeName, String brandId, String categoryId,
			String colorId, String soleId, String materialId, String shoeHeight, String benefit,
			String shoeFeel, String surface, String productGender, ProductStatus productStatus,
			Pageable pageable) {
		return productRepository.findCollectionProductDTO(
						StringUtils.hasText(query) ? "%".concat(query).concat("%") : null,
						minPrice, maxPrice,
						StringUtils.hasText(sizeName) ? Arrays.stream(sizeName.split(",")).collect(Collectors.toList()) : null,
						getCollectionIntegerParams(brandId),
						getCollectionIntegerParams(categoryId),
						getCollectionIntegerParams(colorId),
						getCollectionIntegerParams(soleId),
						getCollectionIntegerParams(materialId),
						StringUtils.hasText(shoeHeight) ? Arrays.stream(shoeHeight.split(",")).map(ShoeHeight::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(benefit) ? Arrays.stream(benefit.split(",")).map(Benefit::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(shoeFeel) ? Arrays.stream(shoeFeel.split(",")).map(ShoeFeel::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(surface) ? Arrays.stream(surface.split(",")).map(Surface::valueOf).collect(Collectors.toList()) : null,
						StringUtils.hasText(productGender) ? Arrays.stream(productGender.split(",")).map(ProductGender::valueOf).collect(Collectors.toList()) : null,
						productStatus, pageable);
	}
	// @formatter:on

	@Transactional
	@Override
	public Product saveOrUpdate(ProductVO productVO) {
		if (productVO.getId() > 0) {
			return updateProduct(productVO.getId(), productVO);
		}
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
		product.setColor(productVO.getColor());
		product.setMaterial(productVO.getMaterial());
		product.setSole(productVO.getSole());
		product.setShoeHeight(productVO.getShoeHeight());
		product.setBenefit(productVO.getBenefit());
		product.setShoeFeel(productVO.getShoeFeel());
		product.setSurface(productVO.getSurface());
		product.setCreateDate(date);
		product.setUpdateDate(date);
		return productRepository.save(product);
	}

	private Product updateProduct(int id, ProductVO productVO) {
		Date date = new Date();
		productRepository.updateProduct(id, productVO.getName(), productVO.getProductGender(),
				productVO.getProductStatus(), productVO.getBrand(), productVO.getCategory(), productVO.getColor(),
				productVO.getSole(), productVO.getMaterial(), productVO.getShoeHeight(), productVO.getBenefit(),
				productVO.getShoeFeel(), productVO.getSurface(), productVO.getSellPrice(), productVO.getDescription(),
				date);
		return new Product(id);
	}

	private Collection<Integer> getCollectionIntegerParams(String source) {
		return StringUtils.hasText(source) ? Arrays.stream(source.split(",")).mapToInt(num -> Integer.parseInt(num))
				.boxed().collect(Collectors.toList()) : null;
	}
}
