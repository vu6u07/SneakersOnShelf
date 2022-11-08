package com.sos.converter;

import com.sos.controller.ProductDTORestController;
import com.sos.dto.ProductDTO;
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

import java.util.Date;

@Service
public class ConvertProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertProduct.class);
    @Autowired
    private BrandService brandService;

    @Autowired
    public CategoryService categoryService;

    public Product convertProductDtoToProductEntity(ProductInfoDTO productInfoDTO){
        if(productInfoDTO != null){
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
        return null;
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

    public Product getProductEntity(ProductDTO product){
        System.out.println(product.getName());
        Product product1 = new Product();
        product1.setId(product.getId());
        product1.setProductGender(product.getProductGender());
        product1.setProductImage(product.getProductImage());

        product1.setCategory(product.getCategory());
        product1.setBrand(product.getBrand());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setImportPrice(product.getImportPrice());
        product1.setSellPrice(product.getSellPrice());
        product1.setOriginalPrice(product.getOriginalPrice());
        product1.setCreateDate(new Date());
        product1.setUpdateDate(null);
        return product1;
    }

}
