package com.bilgeadam.teknikservis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.repository.ProductRepository;
import com.bilgeadam.teknikservis.service.ProductService;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
	 private final ProductRepository productRepository;
	    private final ProductService productService;
	    
	public ProductController(ProductRepository productRepository, ProductService productService) {
			super();
			this.productRepository = productRepository;
			this.productService = productService;
		}
	@GetMapping("/getAllProduct")
    public ResponseEntity<List<Product>> getAllProduct(){
        try {
            List<Product> temp = productService.GetAllProduct();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
