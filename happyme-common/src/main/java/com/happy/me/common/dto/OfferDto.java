package com.happy.me.common.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


public class OfferDto implements BaseDto{


	private static final long serialVersionUID = 1L;


	private Long id;

	private String title;
	private String description;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date start;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date end;


    private byte[] image;
	
	List<OfferImagesDto> images;
    

    private MerchantDto merchantDto;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public List<OfferImagesDto> getImages() {
		return images;
	}


	public void setImages(List<OfferImagesDto> images) {
		this.images = images;
	}


	public MerchantDto getMerchantDto() {
		return merchantDto;
	}


	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}
	
	
}
