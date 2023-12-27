package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.model.Service;
import com.bilgeadam.teknikservis.repository.ProductRepository;
import com.bilgeadam.teknikservis.repository.SaleRepository;
import com.bilgeadam.teknikservis.service.ProductService;
import com.bilgeadam.teknikservis.service.SaleService;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(path = "/sale")
public class SaleController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final MessageSource messageSource;

    public SaleController(SaleService saleService, SaleRepository saleRepository, ProductRepository productRepository, ProductService productService, MessageSource messageSource) {
        this.saleService = saleService;
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
		this.productService = productService;
        this.messageSource = messageSource;
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
    
    public ResponseEntity<String> deleteById(Locale locale, @PathVariable(name = "id") long id){
        Object[] params = new Object[1];
        params[0] = id;
        try{
            boolean result = saleService.deleteById(id);
            if(result){
                return ResponseEntity.ok(messageSource.getMessage("sale.delete.success", params, locale));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("sale.delete.notfound", params, locale));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.delete.error", params, locale));
        }
    }

    /*
    Sale tablosuna yeni bir kayıt ekler. Sadece adminlerin yetkisi vardır.
     */
    
    @PostMapping("/admin/save")
    
    public ResponseEntity<String> save(Locale locale,@RequestBody Sale sale){
        try{
            boolean result = saleService.save(sale);
            if(result){
                return ResponseEntity.ok(messageSource.getMessage("sale.save.success", null , locale));
            }else {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.save.error", null , locale));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.save.error", null , locale));
        }
    }
    @GetMapping("/get/{product_name}")
//    localhost:8080/sale/get/RAM
    public ResponseEntity<List<Sale>> getAll(@PathVariable String product_name) {
		return ResponseEntity.ok(saleRepository.getSaleByProductName(product_name));
   
}
    
}