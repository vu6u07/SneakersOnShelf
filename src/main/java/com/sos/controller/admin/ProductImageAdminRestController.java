package com.sos.controller.admin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sos.entity.ProductImage;
import com.sos.service.ProductImageService;

@RestController
@RequestMapping(value = "/admin/v1")
public class ProductImageAdminRestController {

	@Autowired
	private ProductImageService productImageService;

	@GetMapping(value = "/products/{id}/product-images")
	public ResponseEntity<?> get(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(productImageService.findByProductId(id));
	}

	@PostMapping(value = "/products/{id}/product-images")
	public ResponseEntity<?> post(@PathVariable(name = "id") int id, @RequestPart(value = "image") MultipartFile file,
			HttpServletRequest request) throws IOException, URISyntaxException {
		ProductImage created = productImageService.uploadProductImage(id, file);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}

	@PutMapping(value = "/product-images/{id}/set-default")
	public ResponseEntity<?> setDefaultProductImage(@PathVariable(name = "id") int id) {
		productImageService.setDefaultProductImage(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/product-images/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		productImageService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
