package com.happy.me.common.dto;

import java.util.ArrayList;
import java.util.List;

public class CardsDto implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Long total;
	
	private List<CardDto> cards;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<CardDto> getCards() {
		return cards;
	}

	public void setCards(List<CardDto> cards) {
		this.cards = cards;
	}
	
	public void addCard(CardDto card) {
		if (cards == null) {
			cards = new ArrayList<CardDto>();
		}
		cards.add(card);
	}
	
	
	
	

}
