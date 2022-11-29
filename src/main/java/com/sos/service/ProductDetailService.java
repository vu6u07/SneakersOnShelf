package com.sos.service;

import com.sos.entity.ProductDetail;

public interface ProductDetailService extends CrudService<ProductDetail, Integer> {

	ProductDetail addSize(int productId, String size, int quantity);

}
