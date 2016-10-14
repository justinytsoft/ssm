package com.FangBianMian.domain;

import java.util.Date;
import java.util.List;

/**
 * 商品类
 * @author Administrator
 *
 */
public class Product {

	private Integer id;
	private Integer category_id;
	private String name;
	private Float price;
	private Float discount_price;
	private Float freight_price;
	private Integer payment_type;
	private Integer quantity;
	private String detail;
	private Boolean status;
	private Boolean hot;
	private Boolean invisible;
	private Date create_time;
	private List<ProductImg> imgs;
	
	//默认显示图片
	private String default_img;
	
	/**
	 * 已出售数量
	 * @return
	 */
	private Integer saled_num;
	/**
	 * 评分人数
	 */
	private Integer common_num;
	/**
	 * 评分的平均分数
	 */
	private Float score;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Float discount_price) {
		this.discount_price = discount_price;
	}
	public Float getFreight_price() {
		return freight_price;
	}
	public void setFreight_price(Float freight_price) {
		this.freight_price = freight_price;
	}
	public Integer getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(Integer payment_type) {
		this.payment_type = payment_type;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getHot() {
		return hot;
	}
	public void setHot(Boolean hot) {
		this.hot = hot;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public List<ProductImg> getImgs() {
		return imgs;
	}
	public void setImgs(List<ProductImg> imgs) {
		this.imgs = imgs;
	}
	public Boolean getInvisible() {
		return invisible;
	}
	public void setInvisible(Boolean invisible) {
		this.invisible = invisible;
	}
	public Integer getSaled_num() {
		return saled_num;
	}
	public void setSaled_num(Integer saled_num) {
		this.saled_num = saled_num;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getDefault_img() {
		return default_img;
	}
	public void setDefault_img(String default_img) {
		this.default_img = default_img;
	}
	public Integer getCommon_num() {
		return common_num;
	}
	public void setCommon_num(Integer common_num) {
		this.common_num = common_num;
	}
}
