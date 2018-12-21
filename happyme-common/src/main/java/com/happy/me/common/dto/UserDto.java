package com.happy.me.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.happy.me.common.enums.RoleKey;

@JsonInclude(Include.NON_NULL)
public class UserDto implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String firstName;
	
	private String lastName;

	private String phone;

	private String email;

	private String password;
	
	private String facebookId;
	
    private RoleKey roleKey;
	
    private boolean active;
    
    private String uid;
    
    private String cpr;
	
	private String nationality;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
    private Date dateOfBirth;
	
	private UserDto createdBy;
	
	private MerchantDto merchantDto;
	
	private String jwt;
	
	public UserDto() {
		this.active = true;
	}

	public UserDto(Long id) {
		super();
		this.id = id;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		if (email == null) {
			return email;
		}
		return email.toLowerCase();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public RoleKey getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(RoleKey roleKey) {
		this.roleKey = roleKey;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserDto getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDto createdBy) {
		this.createdBy = createdBy;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public MerchantDto getMerchantDto() {
		return merchantDto;
	}

	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "UserDto: {id:" + id + ", firstName:" + firstName + ", lastName:" + lastName + ", phone:" + phone
				+ ", email:" + email + ", password:" + password + ", facebookId:" + facebookId + ", roleKey:" + roleKey
				+ ", active:" + active + ", uid:" + uid + ", cpr:" + cpr + ", nationality:" + nationality
				+ ", dateOfBirth:" + dateOfBirth + ", createdBy:" + createdBy + ", merchantDto:" + merchantDto + "}";
	}
	
	
	

}
