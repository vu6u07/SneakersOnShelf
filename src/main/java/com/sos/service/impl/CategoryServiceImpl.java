package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import com.sos.common.ApplicationConstant;
import com.sos.dto.CategoryRequest;
import com.sos.entity.Brand;
import com.sos.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.entity.Category;
import com.sos.repository.CategoryRepository;
import com.sos.service.CategoryService;
import org.springframework.util.StringUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) throw new ResourceNotFoundException("Category not found");
		category.setActiveStatus(ApplicationConstant.ActiveStatus.INACTIVE);
		categoryRepository.save(category);
	}

	@Override
	public Page<Category> findAll(String query, Pageable pageable) {
		if(query != null) return categoryRepository.findCategoriesByNameContaining(query, pageable);
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Category save(CategoryRequest categoryRequest) {
		Category category = new Category();
		category.setId(categoryRequest.getId());
		category.setName(categoryRequest.getName());
		if(category.getId() > 0){
			category.setActiveStatus(categoryRequest.getActiveStatus());
		} else category.setActiveStatus(ApplicationConstant.ActiveStatus.ACTIVE);
		return categoryRepository.save(category);
	}

	@Override
	public Page<Category> findAll(String query, ApplicationConstant.ActiveStatus activeStatus, Pageable pageable) {
		return categoryRepository.findAll(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, activeStatus, pageable);
	}
}
