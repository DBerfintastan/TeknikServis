package com.bilgeadam.teknikservis.model;

public class Proposal {

	private long id;
	private String note;
	private int price;
	private long user_id;
	private long product_id;
	private boolean status;
	
	
	public Proposal(long id, String note, int price, long user_id, long product_id, boolean status) {
		
		this.id = id;
		this.note = note;
		this.price = price;
		this.user_id = user_id;
		this.product_id = product_id;
		this.status = status;
	}

	public Proposal()
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
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", note=" + note + ", price=" + price + ", user_id=" + user_id + ", product_id="
				+ product_id + ", status=" + status + "]";
	}
	

}
