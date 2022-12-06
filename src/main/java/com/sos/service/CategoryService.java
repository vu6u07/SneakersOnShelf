package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant;
import com.sos.dto.CategoryRequest;
import com.sos.entity.Category;

public interface CategoryService extends CrudService<Category, Integer>{
    Page<Category> findAll(String query, Pageable pageable);

    Category save(CategoryRequest categoryRequest);

    Page<Category> findAll(String query, ApplicationConstant.ActiveStatus activeStatus, Pageable pageable);
}
