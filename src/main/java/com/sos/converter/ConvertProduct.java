package com.sos.converter;

import com.sos.controller.ProductDTORestController;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Brand;
import com.sos.entity.Category;
import com.sos.entity.Product;
import com.sos.service.BrandService;
import com.sos.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertProduct.class);
    @Autowired
    private BrandService brandService;

    @Autowired
    public CategoryService categoryService;

    public Product convertProductDtoToProductEntity(ProductInfoDTO productInfoDTO){
        Product product = new Product();
        String brandName = productInfoDTO.getBrand().trim();
        String cateName = productInfoDTO.getCategory().trim();
        Brand brand = getBrand(brandName);
        product.setBrand(brand);
        LOGGER.info("brand: -- "+brand);
        Category category = getCategory(cateName);
        product.setCategory(category);
        LOGGER.info("cate: -- "+category);
        product.setProductGender(productInfoDTO.getProductGender());
        product.setDescription(productInfoDTO.getDescription());
        product.setImportPrice(productInfoDTO.getImportPrice());
        product.setSellPrice(productInfoDTO.getSellPrice());
        product.setName(productInfoDTO.getName());
        product.setOriginalPrice(productInfoDTO.getOriginalPrice());
        product.setUpdateDate(productInfoDTO.getUpdateDate());
        return product;
    }

    public Brand getBrand(String brandName){
        if(brandName!= null){
            Brand brand = brandService.findBrandByName(brandName);
            if(brand != null){
                return brand;
            }else{
                Brand b = new Brand();
                b.setName(brandName);
                Brand br = brandService.save(b);
                return brandService.save(br);
            }
        }
        return null;
    }

    public Category getCategory(String categoryName){
        if(categoryName != null){
            Category category = categoryService.findCategoryByName(categoryName);
            if(category != null){
                return category;
            }else{
                Category c = new Category();
                c.setName(categoryName);
                Category ca = categoryService.save(c);
                return ca;
            }
        }
        return null;
    }
}
