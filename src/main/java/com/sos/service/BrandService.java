package com.sos.service;

import com.sos.entity.Brand;

public interface BrandService extends CrudService<Brand, Integer> {
    Brand findBrandById(Integer id);

    Brand findBrandByName(String name);
}
