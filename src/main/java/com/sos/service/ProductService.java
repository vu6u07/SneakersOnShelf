package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;

import java.util.Optional;

public interface ProductService extends CrudService<Product, Integer> {

	Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable);

	ProductInfoDTO findProductInfoDTOById(int id);


	ProductInfoDTO findProductInfoDTOByName(String name);

}
