package com.FangBianMian.domain;

import java.util.Date;
import java.util.List;

/**
 * 订单类
 * @author Administrator
 *
 */
public class Orders {

	private Integer id;
	private Integer mid;
	private String sn;
	private String express;
	private Float amount;
	private Float amount_paid;
	private Float discount;
	private String receiver;
	private String phone;
	private String address;
	private Integer deliver_type;
	private Integer status;
	private Float freight;
	private Boolean invisible;
	private String message;
	private String postcode;
	private String payment_type;
	private Date delivery_time;
	private Date create_time;
	
	private List<OrdersItem> items;
	private List<OrdersLog> logs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Float getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(Float amount_paid) {
		this.amount_paid = amount_paid;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getDeliver_type() {
		return deliver_type;
	}
	public void setDeliver_type(Integer deliver_type) {
		this.deliver_type = deliver_type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Float getFreight() {
		return freight;
	}
	public void setFreight(Float freight) {
		this.freight = freight;
	}
	public Boolean getInvisible() {
		return invisible;
	}
	public void setInvisible(Boolean invisible) {
		this.invisible = invisible;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public List<OrdersItem> getItems() {
		return items;
	}
	public void setItems(List<OrdersItem> items) {
		this.items = items;
	}
	public List<OrdersLog> getLogs() {
		return logs;
	}
	public void setLogs(List<OrdersLog> logs) {
		this.logs = logs;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public Date getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}
}
