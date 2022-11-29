package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.dto.vo.ProductVO;
import com.sos.entity.Product;

public interface ProductService extends CrudService<Product, Integer> {

	Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable);
	
	Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable);
	
	ProductInfoDTO findProductInfoDTOById(int id);

	Product save(ProductVO productVO);
	
}
