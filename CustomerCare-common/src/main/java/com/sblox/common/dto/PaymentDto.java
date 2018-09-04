package com.sblox.common.dto;

import java.io.Serializable;
import java.security.Principal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PaymentDto implements Serializable {

	private Long id;

	private int numberOfCoreAgents;
	private int numberOfEnterpriseAgents;
	private int numberOfRecorderAgents;
	private int numberOfOutboundAgents;
	private int numberOfMonths;
	private String interval;
	private int supersCount;
	private String startDate;
	private String endDate;
	private String projectName;
	private double totalCost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int getSupersCount() {
		return supersCount;
	}

	public void setSupersCount(int supersCount) {
		this.supersCount = supersCount;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

}
