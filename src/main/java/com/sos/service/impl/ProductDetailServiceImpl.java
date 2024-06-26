package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Product;
import com.sos.entity.ProductDetail;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Override
	public List<ProductDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductDetail> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ProductDetail> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public ProductDetail save(ProductDetail entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
	}

	@Transactional
	@Override
	public ProductDetail addSize(int productId, String size, int quantity) {
		if (quantity < 0) {
			throw new ValidationException("Số lượng không hợp lệ.");
		}
		Optional<ProductDetail> pd = productDetailRepository.findProductDetail(productId, size);
		if (pd.isPresent()) {
			ProductDetail productDetail = pd.get();
			productDetailRepository.increaseProductDetailQuantity(productDetail.getId(), quantity);
			productDetail.setQuantity(productDetail.getQuantity() + quantity);
			return productDetail;
		}
		ProductDetail productDetail = new ProductDetail();
		productDetail.setProduct(new Product(productId));
		productDetail.setActiveStatus(ActiveStatus.ACTIVE);
		productDetail.setSize(size);
		productDetail.setQuantity(quantity);
		return productDetailRepository.save(productDetail);
	}

	@Transactional
	@Override
	public void changeProductDetailQuantity(int id, int quantity) {
		if (quantity < 0) {
			throw new ValidationException("Số lượng không hợp lệ.");
		}
		productDetailRepository.updateProductDetailQuantity(id, quantity);
	}
	
	@Transactional
	@Override
	public void changeProductDetailStatus(int id, ActiveStatus activeStatus) {
		productDetailRepository.updateProductDetailStatus(id, activeStatus);
	}
	
}
