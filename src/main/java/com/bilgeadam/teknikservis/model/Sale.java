package com.bilgeadam.teknikservis.model;

public class Sale 
{
	private long id;
	private String note;
	private int price;
	private long product_id;
	
	
	public Sale(long id, String note, int price, long product_id) 
	{
		
		this.id = id;
		this.note = note;
		this.price = price;
		this.product_id = product_id;
	}


	public Sale() 
	{
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}


	@Override
	public String toString() {
		return "Sale [id=" + id + ", note=" + note + ", price=" + price + ", product_id=" + product_id + "]";
	}
	
	
	

}
