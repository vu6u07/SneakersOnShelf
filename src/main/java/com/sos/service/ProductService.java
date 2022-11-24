package com.sos.service;

import com.sos.dto.ProductCrudDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends CrudService<Product, Integer> {

	Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, Pageable pageable);

	Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, Pageable pageable);

	ProductInfoDTO findProductInfoDTOById(int id);

	Product findProductById(int id);


	ProductInfoDTO findProductInfoDTOByName(String name);

	boolean saveDatabase(ProductCrudDTO product);
}
