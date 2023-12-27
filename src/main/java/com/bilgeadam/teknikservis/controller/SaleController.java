package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.model.Service;
import com.bilgeadam.teknikservis.repository.ProductRepository;
import com.bilgeadam.teknikservis.repository.SaleRepository;
import com.bilgeadam.teknikservis.service.ProductService;
import com.bilgeadam.teknikservis.service.SaleService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/sale")
public class SaleController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public SaleController(SaleService saleService, SaleRepository saleRepository, ProductRepository productRepository, ProductService productService) {
        this.saleService = saleService;
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
		this.productService = productService;
    }

    /*
    Sale tablosundaki tüm kayıtları getirir
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Sale>> getAll(){
        try {
            List<Sale> temp = saleService.getAll();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
    Sale tablosundaki belirli bir kaydı siler. Sadece adminlerin yetkisi vardır.
     */
    @DeleteMapping("/admin/deleteById/{id}")
    
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") long id){
        try{
            boolean result = saleService.deleteById(id);
            if(result){
                return ResponseEntity.ok(id + " nolu kayıt silindi.");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " nolu kayıt bulunamadı.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(id + " nolu kayıt silinemedi.");
        }
    }

    /*
    Sale tablosuna yeni bir kayıt ekler. Sadece adminlerin yetkisi vardır.
     */
    
    @PostMapping("/admin/save")
    
    public ResponseEntity<String> save(@RequestBody Sale sale){
        try{
            boolean result = saleService.save(sale);
            if(result){
                return ResponseEntity.ok("Başarıyla kaydedildi.");
            }else {
                return ResponseEntity.internalServerError().body("Kaydedilemedi. Server error.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Kaydedilemedi. Lütfen tekrar deneyiniz.");
        }
    }
    @GetMapping("/get/{product_name}")
//    localhost:8080/sale/get/RAM
    public ResponseEntity<List<Sale>> getAll(@PathVariable String product_name) {
		return ResponseEntity.ok(saleRepository.getSaleByProductName(product_name));
   
}
    
}