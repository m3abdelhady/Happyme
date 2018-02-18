package com.happy.me.dataaccess.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table
public class BillHeader extends ModificationAudit implements IEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "merchant")
	private Merchant merchant;

	@Temporal(TemporalType.DATE)
	private Date billDate;
	private double billAmount;
	private double balanceBroughtForward;
	private double totalAmountDue;
	private String invoiceNumber;
	@Temporal(TemporalType.DATE)
	private Date fromDate;
	@Temporal(TemporalType.DATE)
	private Date toDate;
	
	@OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY, orphanRemoval=true, mappedBy="billHeader")
    @OnDelete(action = OnDeleteAction.CASCADE)
	//@LazyCollection(LazyCollectionOption.FALSE)
	List<BillLines> billLines;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getBalanceBroughtForward() {
		return balanceBroughtForward;
	}

	public void setBalanceBroughtForward(double balanceBroughtForward) {
		this.balanceBroughtForward = balanceBroughtForward;
	}

	public double getTotalAmountDue() {
		return totalAmountDue;
	}

	public void setTotalAmountDue(double totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<BillLines> getBillLines() {
		return billLines;
	}

	public void setBillLines(List<BillLines> billLines) {
		this.billLines = billLines;
	}
	
	

}
