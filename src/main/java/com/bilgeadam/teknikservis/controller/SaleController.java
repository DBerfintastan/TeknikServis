package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
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
}