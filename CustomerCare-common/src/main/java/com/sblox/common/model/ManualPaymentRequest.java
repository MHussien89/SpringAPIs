package com.sblox.common.model;

import java.io.Serializable;
import java.util.Calendar;

/*
 * Class represents the returned user's data after authentication process 
 */
public class ManualPaymentRequest implements Serializable, Cloneable, Comparable<ManualPaymentRequest> {

	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 2);
		System.out.println(now.getTime());
	}

	private int numberOfCoreAgents;
	private int numberOfEnterpriseAgents;
	private int numberOfRecorderAgents;
	private int numberOfOutboundAgents;
	private int supersCount;
	private int numberOfMonths;
	private String interval;
	private String startDate;
	private String endDate;

	public ManualPaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(ManualPaymentRequest o) {
		// TODO Auto-generated method stub
		return 0;
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
