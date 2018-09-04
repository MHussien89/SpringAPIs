package com.sblox.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sblox.common.dto.UserDto;
import java.io.Serializable;

/*
 * Class represents the returned user's data after authentication process 
 */
public class UserData implements Serializable, Cloneable, Comparable<UserData> {

	private long userId;
	private long organizationId;
	private boolean active;
	private String primaryUser;
	private String firstName;
	private String lastName;
	private String userName;
	private String organizationName;
	private String netLogicPassword;
	private String phone;
	private String email;
	private String role;
	private boolean accepted;
	private String paymentStatus;
	private int remainingDays;

	private int numberOfCoreAgents;
	private int numberOfEnterpriseAgents;
	private int numberOfRecorderAgents;
	private int numberOfOutboundAgents;
	private int numberOfMonths;

	private String startDate;
	private String endDate;

	private String address;
	private String city;
	private String country;

	private double totalCost;

	@JsonIgnore
	private boolean selected;

	private UserData(UserData userData) {
		userId = userData.getUserId();
		firstName = userData.getFirstName();
		lastName = userData.getLastName();
		phone = userData.getPhone();
		email = userData.getEmail();
		role = userData.getRole();
		selected = userData.isSelected();
	}

	public UserData(UserDto userDto) {
		firstName = userDto.getFirstName();
		lastName = userDto.getLastName();
		email = userDto.getEmail();
		phone = userDto.getPhone();
		role = userDto.getRole().getName();
		userId = userDto.getId();
		primaryUser = userDto.getPrimaryUser();
		organizationId = userDto.getOrganization().getId();
		accepted = userDto.getOrganization().isAccepted();
		active = userDto.getOrganization().isActive();
		numberOfCoreAgents = userDto.getOrganization().getNumberOfCoreAgents();
		numberOfEnterpriseAgents = userDto.getOrganization().getNumberOfEnterpriseAgents();
		numberOfOutboundAgents = userDto.getOrganization().getNumberOfOutboundAgents();
		numberOfRecorderAgents = userDto.getOrganization().getNumberOfRecorderAgents();
		numberOfMonths = userDto.getOrganization().getNumberOfMonths();
		netLogicPassword = userDto.getHashedPassword();
		userName = userDto.getUserName();
		organizationName = userDto.getOrganization().getName();

		startDate = userDto.getOrganization().getStartDate();
		endDate = userDto.getOrganization().getEndDate();

		address = userDto.getOrganization().getAddress();
		city = userDto.getOrganization().getCity();
		country = userDto.getOrganization().getCountry();
		paymentStatus = userDto.getOrganization().getPaymentStatus();
		remainingDays = userDto.getOrganization().getRemainingDays();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(String primaryUser) {
		this.primaryUser = primaryUser;
	}

	@Override
	public UserData clone() throws CloneNotSupportedException {
		return new UserData(this);
	}

	@Override
	public int compareTo(UserData o) {
		return getName().compareTo(o.getName());
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
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

	public int getNumberOfMonths() {
		return numberOfMonths;
	}

	public void setNumberOfMonths(int numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
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

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getNetLogicPassword() {
		return netLogicPassword;
	}

	public void setNetLogicPassword(String netLogicPassword) {
		this.netLogicPassword = netLogicPassword;
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
