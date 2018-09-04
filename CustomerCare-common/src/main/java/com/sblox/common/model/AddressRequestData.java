package com.sblox.common.model;


import java.io.Serializable;

/*
 * Class represents the returned user's data after authentication process 
 */
public class AddressRequestData implements Serializable, Cloneable, Comparable<AddressRequestData> {

	private String address;
	private String city;
	private String country;
	private String modeOfPayment;

	public AddressRequestData(String address, String city, String country, String modeOfPayment) {
		super();
		this.address = address;
		this.city = city;
		this.country = country;
		this.modeOfPayment = modeOfPayment;
	}

	public AddressRequestData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(AddressRequestData o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

}
