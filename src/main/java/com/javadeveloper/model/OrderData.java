package com.javadeveloper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderdata")
public class OrderData {
	 public OrderData() {

	  }
	 
	  public OrderData(long id, float amount, String currency, String comment) {
		super();
		this.id = id;
		this.amount = amount;
		this.currency = currency;
		this.comment = comment;
	}

	public OrderData(Integer orderId, float amount, String currency, String comment, String filename, int line,
			String result) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.comment = comment;
		this.filename = filename;
		this.line = line;
		this.result = result;
	}

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	  
	  @Column(name = "orderId")
	  private Integer orderId;

	  @Column(name = "amount")
	  private Float amount;

	  @Column(name = "currency")
	  private String currency;

	  @Column(name = "comment")
	  private String comment;
	  
	  @Column(name = "filename")
	  private String filename;

	  @Column(name = "line")
	  private Integer line;
	  
	  @Column(name = "result")
	  private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "OrderData [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", currency=" + currency
				+ ", comment=" + comment + ", filename=" + filename + ", line=" + line + ", result=" + result + "]";
	}
  
	  
}
