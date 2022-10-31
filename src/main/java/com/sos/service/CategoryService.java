package com.sos.service;

import com.sos.entity.Category;
import org.springframework.data.repository.query.Param;

public interface CategoryService  extends CrudService<Category, Integer> {
        Category findCategoryById(Integer id);

        Category findCategoryByName(String name);

}
