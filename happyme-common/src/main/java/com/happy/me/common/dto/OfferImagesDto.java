package com.happy.me.common.dto;

public class OfferImagesDto implements BaseDto{


	private static final long serialVersionUID = 1L;

	private Long id;

    private byte[] image;
	
    private OfferDto offerDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public OfferDto getOfferDto() {
		return offerDto;
	}

	public void setOfferDto(OfferDto offerDto) {
		this.offerDto = offerDto;
	}
	
}
