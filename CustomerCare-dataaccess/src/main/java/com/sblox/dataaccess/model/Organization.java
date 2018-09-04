package com.sblox.dataaccess.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Organization extends IEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Organization_ID")
	private Long id;

	@NotNull
	private String name;

	private String moxtraId;

	private String address;
	private String city;
	private String country;

	private String phoneNumber;

	private int status;

	private String firstLogin;

	private int numberOfActiveUsers;

	// @ManyToOne
	// private RatePlan ratePlan;

	private Date expiryDate;

	private String timeZone;

	private boolean accepted;
	private boolean updated;
	private boolean dateUpdated;

	private String modeOfPayment;
	private String paymentStatus;

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

	// @OneToOne
	// private Address address;

	// @OneToOne
	// @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "organization", fetch
	// = FetchType.EAGER)
	// private Address address;

	// @OneToOne(targetEntity = Organization.class)
	// @JoinColumn(name = "Address_ID")
	// private Address address;

	//
	// @OneToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
	// @JoinColumn(name = "Address_ID", nullable = true)
	// private Address address;

	@OneToMany(cascade = { CascadeType.PERSIST }, mappedBy = "organization", fetch = FetchType.EAGER)
	private List<User> users;

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

	public String getMoxtraId() {
		return moxtraId;
	}

	public void setMoxtraId(String moxtraId) {
		this.moxtraId = moxtraId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	// public Address getAddress() {
	// return address;
	// }
	//
	// public void setAddress(Address address) {
	// this.address = address;
	// }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// public RatePlan getRatePlan() {
	// return ratePlan;
	// }
	//
	// public void setRatePlan(RatePlan ratePlan) {
	// this.ratePlan = ratePlan;
	// }

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

}
