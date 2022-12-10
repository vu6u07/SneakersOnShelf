package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

public interface ProductService extends CrudService<Product, Integer> {

	Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			Integer colorId, Integer soleId, Integer materialId, ShoeHeight shoeHeight, Benefit benefit,
			ShoeFeel shoeFeel, Surface surface, ProductGender productGender, ProductStatus productStatus,
			Pageable pageable);

	Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			Integer colorId, Integer soleId, Integer materialId, ShoeHeight shoeHeight, Benefit benefit,
			ShoeFeel shoeFeel, Surface surface, ProductGender productGender, ProductStatus productStatus,
			Pageable pageable);

	// Collection
	Page<CollectionProductDTO> findCollectionProductDTO(String query, Long minPrice, Long maxPrice, String sizeName, String brandId, String categoryId,
			String colorId, String soleId, String materialId, String shoeHeight, String benefit,
			String shoeFeel, String surface, String productGender, ProductStatus productStatus,
			Pageable pageable);
	
	Page<CollectionProductDTO> findBestSellingProductDTO(String query, Long minPrice, Long maxPrice, String sizeName, String brandId, String categoryId,
			String colorId, String soleId, String materialId, String shoeHeight, String benefit,
			String shoeFeel, String surface, String productGender, ProductStatus productStatus,
			Pageable pageable);

	ProductInfoDTO findProductInfoDTOById(int id);

	Product saveOrUpdate(ProductVO productVO);

}
