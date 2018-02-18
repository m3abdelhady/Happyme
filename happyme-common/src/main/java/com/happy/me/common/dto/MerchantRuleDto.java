package com.happy.me.common.dto;

import java.util.Date;

public class MerchantRuleDto implements BaseDto{


	private static final long serialVersionUID = 1L;


	private Long id;

	private Long star5;
	
	private Long star4;
	
	private Long star3;
	
	private Long star2;
	
	private Long star1;

	private Long max;
	
	private Long min;
	
	private Double point;
	
	private Double pound;
	
	private Long expiry;
	
	private Date created;
	
	private String description;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStar5() {
		return star5;
	}

	public void setStar5(Long star5) {
		this.star5 = star5;
	}

	public Long getStar4() {
		return star4;
	}

	public void setStar4(Long star4) {
		this.star4 = star4;
	}

	public Long getStar3() {
		return star3;
	}

	public void setStar3(Long star3) {
		this.star3 = star3;
	}

	public Long getStar2() {
		return star2;
	}

	public void setStar2(Long star2) {
		this.star2 = star2;
	}

	public Long getStar1() {
		return star1;
	}

	public void setStar1(Long star1) {
		this.star1 = star1;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Double getPound() {
		return pound;
	}

	public void setPound(Double pound) {
		this.pound = pound;
	}

	public Long getExpiry() {
		return expiry;
	}

	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
