//package com.sblox.dataaccess.model;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//
//@Entity
//public class Address extends IEntity {
//	private static final long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "Address_ID")
//	private long id;
//
//	private String street;
//	private String building;
//	private String city;
//	private String country;
//	private String postcode;
////	private Organization organization;
//	
//    @OneToOne(targetEntity = Organization.class)
//    @JoinColumn(name = "Organization_ID")
//    private Organization organization;
//	
//
//    
////	@OneToOne(mappedBy = "address")
////    private Organization organization;
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public String getBuilding() {
//		return building;
//	}
//
//	public void setBuilding(String building) {
//		this.building = building;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getPostcode() {
//		return postcode;
//	}
//
//	public void setPostcode(String postcode) {
//		this.postcode = postcode;
//	}
//
//	public Organization getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(Organization organization) {
//		this.organization = organization;
//	}
//
//}
