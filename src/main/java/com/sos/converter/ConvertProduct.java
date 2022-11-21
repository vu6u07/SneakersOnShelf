package com.sos.converter;

import com.sos.common.ApplicationConstant;
import com.sos.dto.ProductCrudDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.*;
import com.sos.service.BrandService;
import com.sos.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

   public List<ProductDetail> getProductDetail(ProductCrudDTO productDTO, Product product){
       List<ProductDetail> details = new ArrayList<>();
       details.add(getValuesProductDetail("35", Integer.valueOf(productDTO.getSize35()),product));
       details.add(getValuesProductDetail("36", Integer.valueOf(productDTO.getSize36()),product));
       details.add(getValuesProductDetail("37", Integer.valueOf(productDTO.getSize37()),product));
       details.add(getValuesProductDetail("38", Integer.valueOf(productDTO.getSize38()),product));
       details.add(getValuesProductDetail("39", Integer.valueOf(productDTO.getSize39()),product));
       details.add(getValuesProductDetail("40", Integer.valueOf(productDTO.getSize40()),product));
       details.add(getValuesProductDetail("41", Integer.valueOf(productDTO.getSize41()),product));
       details.add(getValuesProductDetail("42", Integer.valueOf(productDTO.getSize42()),product));
       details.add(getValuesProductDetail("43", Integer.valueOf(productDTO.getSize43()),product));
       return details;
   }
    public List<ProductImage> getProductImage(ProductCrudDTO productDTO,Product product){
        List<?> list = productDTO.getProductImage();
        List<ProductImage> images = new ArrayList<>();
        for ( Object s :list) {
            ProductImage image = new ProductImage();
            image.setImage(String.valueOf(s));
            image.setProduct(product);
            images.add(image);
        }
        return images;
    }

    public ProductImage geOneImage(ProductCrudDTO productDTO){
        List<?> list = productDTO.getProductImage();
        List<ProductImage> images = new ArrayList<>();
        for ( Object s :list) {
            ProductImage image = new ProductImage();
            image.setImage(String.valueOf(s));
            images.add(image);
        }
        return images.get(0);
    }
    public Product getProductCRUD(ProductCrudDTO productDTO,ProductImage productImage){
        Product product = new Product();
        product.setName(productDTO.getName());
        if(productDTO.getProductGender().equals("MEN")){
            product.setProductGender(ApplicationConstant.ProductGender.MEN);
        }else if(productDTO.getProductGender().equals("UNISEX")){
            product.setProductGender(ApplicationConstant.ProductGender.UNISEX);
        }else if(productDTO.getProductGender().equals("WOMAN")){
            product.setProductGender(ApplicationConstant.ProductGender.WOMAN);
        }
        product.setBrand(getBrand(productDTO.getBrand()));
        product.setCategory(getCategory(productDTO.getCategory()));
        product.setCreateDate(new Date());
        product.setDescription(productDTO.getDescription());
        product.setSellPrice(productDTO.getSellPrice());
        product.setOriginalPrice(productDTO.getOriginalPrice());
        product.setProductImage(productImage);
        return product;
    }
    public ProductDetail getValuesProductDetail(String size, Integer quantity,Product product){
        ProductDetail detail = new ProductDetail();
        detail.setSize(size);
        detail.setQuantity(quantity);
        detail.setProduct(product);
        return detail;
    }

}
