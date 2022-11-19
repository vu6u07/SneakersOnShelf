package com.sos.controller;

import com.sos.common.Commons;
import com.sos.common.ValidateData;
import com.sos.converter.ConvertProduct;
import com.sos.dto.ProductCrudDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.dto.ResponseObject;
import com.sos.entity.Product;
import com.sos.service.*;
import com.sos.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sos.common.SorterConstant.ProductSorter;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Pattern;
import java.io.*;
import com.sos.common.ApplicationConstant;
import com.sos.entity.*;
import reactor.core.publisher.Mono;
import java.util.ArrayList;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/content/v1/products")
public class ProductDTORestController {

	@Autowired
	ProductService productService;

	@Autowired
	BrandService brandService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ConvertProduct convertProduct;
	@Autowired
	ProductDetailService productDetailService;

	@Autowired
	ProductImageService productImageService;

	@Autowired
	ValidateData validateData;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDTORestController.class);

	@GetMapping(params = "pages")
	public ResponseEntity<?> getAll(
			@RequestParam(name = "pages", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity.ok(productService.findAll(PageRequest.of(page - 1, size, sorter.getSort())));
	}

	// @formatter:off
	@GetMapping(params = "page")
	public ResponseEntity<?> get(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity.ok(productService.findCollectionProductDTO(PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(productService.findProductInfoDTOById(id));

	}

	@GetMapping(value = "/getName/{name}")
	public ResponseEntity<?> getByName(@PathVariable(name = "name") String name) {
		System.out.println("getByName");
		return ResponseEntity.ok(productService.findProductInfoDTOByName(name));

	}

	@PostMapping(value = "/save")
	public Mono<?> save(@RequestBody ProductCrudDTO product) {
		try {
			LOGGER.info("Save product");
			LOGGER.info(product.toString());
			ResponseObject responseInvalid = validateData.validateProductCrudDTOObject(product);
			if (responseInvalid == null) {
				productService.saveDatabase(product);
				return Mono.just(Utils.responseSuccess("save product"));
			} else {
				return Mono.just(responseInvalid);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Mono.just(Utils.responseUnSuccess());
		}

	}

	@PostMapping(value = "/updated")
	public Mono<?> UpdateProduct(@RequestBody Product product) {
		try {
			LOGGER.info("updated product");
			ResponseObject responseInvalid = validateData.validateDataObject(product);
			if (responseInvalid == null) {
				product.setUpdateDate(new Date());
				productService.save(product);
				return Mono.just(Utils.responseSuccess("Update product"));
			} else {
				return Mono.just(responseInvalid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Mono.just(Utils.responseUnSuccess());

		}

	}

	@DeleteMapping(value = "delete/{id}")
	public Mono<?> delete(@PathVariable(name = "id") int id) {

		try {
			LOGGER.info("Updated product");
			ResponseObject responseInvalid = validateData.validateDeleteProduct(id);
			if (responseInvalid == null) {
				productService.deleteById(id);
				return Mono.just(Utils.responseSuccess("Delete product"));
			} else {
				return Mono.just(responseInvalid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Mono.just(Utils.responseUnSuccess());

		}
	}

	@PostMapping(value = "update")
	public ResponseEntity<?> update(@RequestBody Product product) {
		productService.save(product);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(value = {MultipartException.class})
	@PostMapping(value = "/saveList")
	public Mono<?> save(@RequestParam("files") MultipartFile[] files) {
		LOGGER.info("Start saveList product: ");
		LOGGER.info("files: " + files.length);
		ResponseObject responseInvalid = validateData.validateFileDataImportProduct(files);
		try {
			if (responseInvalid == null) {
				File filePro = convertToFile(files);
				getValuesFileSaveDatabase(filePro);
				return Mono.just(Utils.responseSuccess("save List "));
			} else {
				return Mono.just(responseInvalid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Mono.just(Utils.responseUnSuccess());
		}
	}

	public File convertToFile(MultipartFile[] files) {
		MultipartFile multipartFile = files[0];
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("config/teamplate_insert_product_list.xlsx").getFile());
		try (OutputStream os = new FileOutputStream(file)) {
			os.write(multipartFile.getBytes());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	@GetMapping(value = "dowloadTeamplateFile")
	public ResponseEntity<Resource> download() throws IOException {
		String decisionReport = Utils.getFilePath("config/teamplate_insert_product_list.xlsx");
		File file = new File(decisionReport);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teamplate_insert_product_list.xlsx");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}

	public void getValuesFileSaveDatabase(File filePro) throws Exception {
		try (FileInputStream file = new FileInputStream(filePro)) {
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		ArrayList<ProductInfoDTO> productInfoDTOS = new ArrayList<>();
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
			ProductInfoDTO productInfO = new ProductInfoDTO();
			Product product = new Product();
			Row ro = sheet.getRow(i);
			for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
				Cell ce = ro.getCell(j);
				switch (j) {
					case 0:
						// Name
						if (ce != null) {
							productInfO.setName(String.valueOf(ce));
						}
						break;
					case 1:
						// gender
						if (ce != null) {
							if (String.valueOf(ce).trim().toUpperCase().equals("MEN")) {
								productInfO.setProductGender(ApplicationConstant.ProductGender.MEN);
							} else if (String.valueOf(ce).trim().toUpperCase().equals("WOMAN")) {
								productInfO.setProductGender(ApplicationConstant.ProductGender.WOMAN);
							} else if (String.valueOf(ce).trim().toUpperCase().equals("UNISEX")) {
								productInfO.setProductGender(ApplicationConstant.ProductGender.WOMAN);
							}
						}
						break;
					case 2:
						// Brand
						if (ce != null) {
							productInfO.setBrand(String.valueOf(ce));
						}
						break;
					case 3:
						// Category
						if (ce != null) {
							productInfO.setCategory(String.valueOf(ce));
						}
						break;
					case 4:
						// SellPrice
						if (ce != null) {
							productInfO.setSellPrice(Long.parseLong(
									Commons.convertStringToTypeLong(String.valueOf(ce.getNumericCellValue()))));
						}
						break;
					case 5:
						// OriginalPrice
						if (ce != null) {
							productInfO.setOriginalPrice(Long.parseLong(
									Commons.convertStringToTypeLong(String.valueOf(ce.getNumericCellValue()))));
						}
						break;
					case 6:
						// Description
						if (ce != null) {
							productInfO.setDescription(String.valueOf(ce));
						}
						break;
					case 7:
						// ImportPrice
						if (ce != null) {
							productInfO.setImportPrice(Long.parseLong(
									Commons.convertStringToTypeLong(String.valueOf(ce.getNumericCellValue()))));
							LOGGER.info("Cate save" + productInfO.getCategory());
							product = productService.save(convertProduct.convertProductDtoToProductEntity(productInfO));
						}
						break;
					case 8:
						// size 35
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(35));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(35, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 9:
						// size 36
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(36));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(36, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 10:
						// size 37
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(37));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(37, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 11:
						// size 38
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(38));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(38, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 12:
						// size 39
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(39));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(39, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 13:
						// size 40
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(40));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(40, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 14:
						// size 41
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(41));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(41, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}

						break;
					case 15:
						// size 42
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(42));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(42, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 16:
						// size 43
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductDetail productDetail = new ProductDetail();
								productDetail.setSize(String.valueOf(43));
								String[] size = String.valueOf(ce).split(Pattern.quote("."));
								productDetail.setQuantity(Integer.parseInt(size[0]));
								productDetail.setProduct(product);
								ProductDetail detail = productDetailService.findSizeAndIdProduct(43, product);
								if (detail != null) {
									productDetail.setId(detail.getId());
									productDetail.setQuantity(Integer.parseInt(size[0]) + detail.getQuantity());
									productDetailService.save(productDetail);
								} else {
									productDetailService.save(productDetail);

								}
							}
						}
						break;
					case 17:
						// img 1
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductImage productImage = new ProductImage();
								productImage.setImage(String.valueOf(ce));
								productImage.setProduct(product);
								productImageService.save(productImage);
							}
						}
						break;
					case 18:
						// img 1
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductImage productImage = new ProductImage();
								productImage.setImage(String.valueOf(ce));
								productImage.setProduct(product);
								productImageService.save(productImage);
							}
						}
						break;
					case 19:
						// img 1
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductImage productImage = new ProductImage();
								productImage.setImage(String.valueOf(ce));
								productImage.setProduct(product);
								productImageService.save(productImage);
							}
						}
						break;
					case 20:
						// img 1
						if (ce != null) {
							if (!String.valueOf(ce).isEmpty()) {
								ProductImage productImage = new ProductImage();
								productImage.setImage(String.valueOf(ce));
								productImage.setProduct(product);
								productImageService.save(productImage);
								product.setProductImage(productImage);
								productService.save(product);
							}
						}
						break;
				}
			}

		}
	}catch (Exception e){
			e.printStackTrace();

		}
	}
	}
