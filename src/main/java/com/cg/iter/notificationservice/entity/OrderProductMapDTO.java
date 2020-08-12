package com.cg.iter.notificationservice.entity;


public class OrderProductMapDTO {


	private String productUIN;
	
	private String orderId;

	private String productId;

	private int productStatus;

	private int giftStatus;
	
	private int quantity;

	public OrderProductMapDTO() {}

	public OrderProductMapDTO(String productUIN, String orderId, String productId, int productStatus, int giftStatus , int quantity) {
		super();
		this.productUIN = productUIN;
		this.orderId = orderId;
		this.productId = productId;
		this.productStatus = productStatus;
		this.giftStatus = giftStatus;
		this.quantity = quantity;
	}

	public String getProductUIN() {
		return productUIN;
	}

	public void setProductUIN(String productUIN) {
		this.productUIN = productUIN;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public int getGiftStatus() {
		return giftStatus;
	}

	public void setGiftStatus(int giftStatus) {
		this.giftStatus = giftStatus;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
}
