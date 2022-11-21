package com.sos.controller.admin;

import com.sos.common.ValidateData;
import com.sos.converter.ConvertProduct;
import com.sos.service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    public ProductService productService;

    @Autowired
    public BrandService brandService;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public ProductDetailService productDetailService;

    @Autowired
    public ProductImageService productImageService;

    @Autowired
    public ConvertProduct convertProduct;

    @Autowired
    public ValidateData validateData;

}
