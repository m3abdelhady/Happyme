package com.happy.me.dataaccess.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Entity
@Table
public class Merchant extends ModificationAudit implements IEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	
	private String description;
	
	private String backgroundColor;
	
	@Lob
    @Column(columnDefinition="mediumblob")
    private byte[] logo;
	
	@Lob
    @Column(columnDefinition="mediumblob")
    private byte[] background;
	
	private String phone;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "active", columnDefinition="tinyint(1) default 1")
    private boolean active;
	
	@OneToOne(optional=true, cascade=CascadeType.ALL)
	private MerchantRule merchantRule;
	
	@OneToOne
	private User user;
	

    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval=true, mappedBy="merchant")
    @OnDelete(action = OnDeleteAction.CASCADE)
	List<Address> addresses;

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getBackground() {
		return background;
	}

	public void setBackground(byte[] background) {
		this.background = background;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public MerchantRule getMerchantRule() {
		return merchantRule;
	}

	public void setMerchantRule(MerchantRule merchantRule) {
		this.merchantRule = merchantRule;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
	
}
