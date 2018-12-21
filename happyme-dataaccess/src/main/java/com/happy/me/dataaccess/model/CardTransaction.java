package com.happy.me.dataaccess.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.happy.me.common.enums.TransactionType;

@Entity
@Table(name = "CardTransaction")
public class CardTransaction extends ModificationAudit implements IEntity{


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long point;
	
	private String description;
	
	private String amount;
	
	@Temporal(TemporalType.DATE)
	private Date transactionDate;
	
	@ManyToOne(targetEntity = UserCard.class, fetch = FetchType.EAGER)
	private UserCard userCard;
	
	@Enumerated(EnumType.STRING)
    private TransactionType transactionType;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User agent;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public UserCard getUserCard() {
		return userCard;
	}

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public User getAgent() {
		return agent;
	}

	public void setAgent(User agent) {
		this.agent = agent;
	}
	
	
}