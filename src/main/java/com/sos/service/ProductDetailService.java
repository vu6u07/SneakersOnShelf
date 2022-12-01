package com.sos.service;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.ProductDetail;

public interface ProductDetailService extends CrudService<ProductDetail, Integer> {

	ProductDetail addSize(int productId, String size, int quantity);
	
	void changeProductDetailQuantity(int id, int quantity);

	void changeProductDetailStatus(int id, ActiveStatus activeStatus);
	
}
