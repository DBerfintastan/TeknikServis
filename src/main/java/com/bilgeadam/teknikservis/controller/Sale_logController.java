package com.bilgeadam.teknikservis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.teknikservis.model.Sale_log;
import com.bilgeadam.teknikservis.repository.Sale_logRepository;
import java.util.Date;
@RestController()
@RequestMapping("/buy")
public class Sale_logController{
	private final Sale_logRepository saleLogRepository;

    @Autowired
    public Sale_logController(Sale_logRepository saleLogRepository) {
        this.saleLogRepository = saleLogRepository;
    }

    @PostMapping("/product")
    public ResponseEntity<String> buyProduct(@RequestBody Sale_log saleLog) {
        boolean result = saleLogRepository.save(saleLog);
        if (result)
            return ResponseEntity.ok("Buy Order Success");
        else
            return ResponseEntity.internalServerError().build();
    }
}
