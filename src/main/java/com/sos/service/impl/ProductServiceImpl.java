package com.sos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sos.converter.ConvertProduct;
import com.sos.dto.ProductCrudDTO;
import com.sos.entity.ProductDetail;
import com.sos.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.ProductDetailRepository;
import com.sos.repository.ProductImageRepository;
import com.sos.repository.ProductRepository;
import com.sos.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ConvertProduct convertProduct;
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
		ProductImage productImage = new ProductImage();
		productImage.setImage("https://bizweb.dktcdn.net/thumb/1024x1024/100/413/756/products/dq2514-100-01-1661750899934.png?v=1661750906003");
		ProductImage img = productImageRepository.save(productImage);
		if(productInfoDTO != null){
			System.out.println("Update product");
			entity.setId(productInfoDTO.getId());
			return productRepository.save(entity);
		}
		entity.setProductImage(img);
		System.out.println("Save product");
		return productRepository.save(entity);
	}
	@Transactional
	@Override
	public void deleteById(Integer id) {
			System.out.println("id :   "+productRepository.findProductByID(id).getId());
			Product product = productRepository.findProductByID(id);
			productRepository.setProductImageNullById(product.getId());
			System.out.println("Id: "+product.getId());
			productDetailRepository.deleteProductDetailByProduct(product);
			productImageRepository.deleteProductImageByProduct(product);
			productRepository.deleteById(product.getId());
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
	public Product findProductById(int id) {
		return productRepository.findProductByID(id);
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
			dto.setOriginalPrice(rs.getOriginalPrice());
			dto.setSellPrice(rs.getSellPrice());
			dto.setCreateDate(rs.getCreateDate());
			dto.setUpdateDate(rs.getUpdateDate());
			return dto;
		}
		return null;
	}
	@Transactional
	@Override
	public boolean saveDatabase(ProductCrudDTO productDTO) {
		try {
			Product product = convertProduct.getProductCRUD(productDTO,null);
			List<ProductImage> Images = convertProduct.getProductImage(productDTO,product);
			product.setProductImage(Images.get(0));
			List<ProductDetail> details = convertProduct.getProductDetail(productDTO,product);
			productRepository.save(product);
			productImageRepository.saveAll(Images);
			productDetailRepository.saveAll(details);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable) {
		return productRepository.findCollectionProductDTO(pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTOByBrandId(int brandId, Pageable pageable) {
		return productRepository.findCollectionProductDTOByBrandId(brandId, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(ProductGender productGender, Pageable pageable) {
		return productRepository.findCollectionProductDTO(productGender, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTOByCategoryId(int categoryId, Pageable pageable) {
		return productRepository.findCollectionProductDTOByCategoryId(categoryId, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(int brandId, ProductGender productGender,
			Pageable pageable) {
		return productRepository.findCollectionProductDTO(brandId, productGender, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, Pageable pageable) {
		return productRepository.findCollectionProductDTO(brandId, categoryId, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, ProductGender productGender,
			Pageable pageable) {
		return productRepository.findCollectionProductDTO(brandId, categoryId, productGender, pageable);
	}

	@Override
	public Page<CollectionProductDTO> findCollectionProductDTOByCategoryIdAndProductGender(int categoryId,
			ProductGender productGender, Pageable pageable) {
		return productRepository.findCollectionProductDTOByCategoryIdAndProductGender(categoryId, productGender,
				pageable);
	}
}
