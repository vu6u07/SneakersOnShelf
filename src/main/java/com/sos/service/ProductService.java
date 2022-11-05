package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends CrudService<Product, Integer> {

	Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable);



	Page<CollectionProductDTO> findCollectionProductDTOByBrandId(int brandId, Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTO(ProductGender productGender, Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTOByCategoryId(int categoryId, Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, ProductGender productGender, Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, ProductGender productGender,
			Pageable pageable);

	Page<CollectionProductDTO> findCollectionProductDTOByCategoryIdAndProductGender(int categoryId,
			ProductGender productGender, Pageable pageable);

	ProductInfoDTO findProductInfoDTOById(int id);


	ProductInfoDTO findProductInfoDTOByName(String name);



}
