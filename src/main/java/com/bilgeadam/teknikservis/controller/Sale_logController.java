package com.bilgeadam.teknikservis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.teknikservis.model.Sale_log;
import com.bilgeadam.teknikservis.repository.Sale_logRepository;
import java.util.Date;
import java.util.Locale;

@RestController()
@RequestMapping("/buy")
public class Sale_logController{
	private final Sale_logRepository saleLogRepository;
    private final MessageSource messageSource;

    @Autowired
    public Sale_logController(Sale_logRepository saleLogRepository, MessageSource messageSource) {
        this.saleLogRepository = saleLogRepository;
        this.messageSource = messageSource;
    }

    @PostMapping("/product")
    public ResponseEntity<String> buyProduct(Locale locale, @RequestBody Sale_log saleLog) {
        boolean result = saleLogRepository.save(saleLog);
        if (result)
            return ResponseEntity.ok(messageSource.getMessage("saleLog.buyProduct.success", null , locale));
        else
            return ResponseEntity.internalServerError().build();
    }
}
