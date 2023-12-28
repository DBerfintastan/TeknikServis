package com.bilgeadam.teknikservis.model;

import org.springframework.data.annotation.Id;

public class SaleDTO {
@Id
	private int id;
    private String price;
    private String note;
    private String product;
	public SaleDTO(int id, String price, String note, String product) {
		this.id = id;
		this.price = price;
		this.note = note;
		this.product = product;
	}
	
    
}
