package com.sblox.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrganizationDto implements Serializable {

	private Long id;
	private int status;

	private String name;

	private String moxtraId;

	// private AddressDto address = new AddressDto();
	private String address;
	private String city;
	private String country;

	private String phoneNumber;

	private int numberOfActiveUsers;

	private String firstLogin;

	private boolean checkbox;

	private List<UserDto> users = new ArrayList<UserDto>();

	private RatePlanDto ratePlan;

	private Date expiryDate;

	private String timeZone;

	private boolean accepted;
	private boolean updated;
	private boolean dateUpdated;

	private String modeOfPayment;
	private String paymentStatus;
	private int remainingDays;

	private int numberOfCoreAgents;
	private int numberOfEnterpriseAgents;
	private int numberOfRecorderAgents;
	private int numberOfOutboundAgents;
	private int supersCount;
	private int totalNumberOfAgents;
	private int numberOfMonths;
	private String intervalType;
	private String startDate;
	private String endDate;
	private double totalCost;
	private double toPay;

	public OrganizationDto() {
		super();
	}

	public OrganizationDto(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isActive() {
		return status == OrganizationStatus.ACTIVE.getId();
	}

	public void setActive(boolean active) {
		if (active) {
			status = OrganizationStatus.ACTIVE.getId();
		} else {
			status = OrganizationStatus.DEACTIVE.getId();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoxtraId() {
		return moxtraId;
	}

	public void setMoxtraId(String moxtraId) {
		this.moxtraId = moxtraId;
	}

	@JsonIgnore
	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public int getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}

	public void setNumberOfActiveUsers(int numberOfActiveUsers) {
		this.numberOfActiveUsers = numberOfActiveUsers;
	}

	public String getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}

	// public AddressDto getAddress() {
	// return address;
	// }
	//
	// public void setAddress(AddressDto address) {
	// this.address = address;
	// }
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public RatePlanDto getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(RatePlanDto ratePlan) {
		this.ratePlan = ratePlan;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(boolean dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfCoreAgents() {
		return numberOfCoreAgents;
	}

	public void setNumberOfCoreAgents(int numberOfCoreAgents) {
		this.numberOfCoreAgents = numberOfCoreAgents;
	}

	public int getNumberOfEnterpriseAgents() {
		return numberOfEnterpriseAgents;
	}

	public void setNumberOfEnterpriseAgents(int numberOfEnterpriseAgents) {
		this.numberOfEnterpriseAgents = numberOfEnterpriseAgents;
	}

	public int getNumberOfRecorderAgents() {
		return numberOfRecorderAgents;
	}

	public void setNumberOfRecorderAgents(int numberOfRecorderAgents) {
		this.numberOfRecorderAgents = numberOfRecorderAgents;
	}

	public int getNumberOfOutboundAgents() {
		return numberOfOutboundAgents;
	}

	public void setNumberOfOutboundAgents(int numberOfOutboundAgents) {
		this.numberOfOutboundAgents = numberOfOutboundAgents;
	}

	public int getTotalNumberOfAgents() {
		return totalNumberOfAgents;
	}

	public void setTotalNumberOfAgents(int totalNumberOfAgents) {
		this.totalNumberOfAgents = totalNumberOfAgents;
	}

	public int getNumberOfMonths() {
		return numberOfMonths;
	}

	public void setNumberOfMonths(int numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getToPay() {
		return toPay;
	}

	public void setToPay(double toPay) {
		this.toPay = toPay;
	}

	public int getSupersCount() {
		return supersCount;
	}

	public void setSupersCount(int supersCount) {
		this.supersCount = supersCount;
	}

	public String getIntervalType() {
		return intervalType;
	}

	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getRemainingDays() {
		return remainingDays;
	}

	public void setRemainingDays(int remainingDays) {
		this.remainingDays = remainingDays;
	}

}
