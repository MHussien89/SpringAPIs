package com.sblox.common.model;

import java.io.Serializable;

/*
 * Class represents the returned user's data after authentication process 
 */
public class ConfigurationRequestData implements Serializable, Cloneable, Comparable<ConfigurationRequestData> {

	private double monthlyCore;
	private double dailyCore;

	private double monthlyEnterprise;
	private double dailyEnterprise;

	private double monthlyOutbound;
	private double dailyOutbound;

	private double monthlyRecorder;
	private double dailyRecorder;
	
	

	public ConfigurationRequestData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfigurationRequestData(double monthlyCore, double dailyCore, double monthlyEnterprise,
			double dailyEnterprise, double monthlyOutbound, double dailyOutbound, double monthlyRecorder,
			double dailyRecorder) {
		super();
		this.monthlyCore = monthlyCore;
		this.dailyCore = dailyCore;
		this.monthlyEnterprise = monthlyEnterprise;
		this.dailyEnterprise = dailyEnterprise;
		this.monthlyOutbound = monthlyOutbound;
		this.dailyOutbound = dailyOutbound;
		this.monthlyRecorder = monthlyRecorder;
		this.dailyRecorder = dailyRecorder;
	}

	public double getMonthlyCore() {
		return monthlyCore;
	}

	public void setMonthlyCore(double monthlyCore) {
		this.monthlyCore = monthlyCore;
	}

	public double getDailyCore() {
		return dailyCore;
	}

	public void setDailyCore(double dailyCore) {
		this.dailyCore = dailyCore;
	}

	public double getMonthlyEnterprise() {
		return monthlyEnterprise;
	}

	public void setMonthlyEnterprise(double monthlyEnterprise) {
		this.monthlyEnterprise = monthlyEnterprise;
	}

	public double getDailyEnterprise() {
		return dailyEnterprise;
	}

	public void setDailyEnterprise(double dailyEnterprise) {
		this.dailyEnterprise = dailyEnterprise;
	}

	public double getMonthlyOutbound() {
		return monthlyOutbound;
	}

	public void setMonthlyOutbound(double monthlyOutbound) {
		this.monthlyOutbound = monthlyOutbound;
	}

	public double getDailyOutbound() {
		return dailyOutbound;
	}

	public void setDailyOutbound(double dailyOutbound) {
		this.dailyOutbound = dailyOutbound;
	}

	public double getMonthlyRecorder() {
		return monthlyRecorder;
	}

	public void setMonthlyRecorder(double monthlyRecorder) {
		this.monthlyRecorder = monthlyRecorder;
	}

	public double getDailyRecorder() {
		return dailyRecorder;
	}

	public void setDailyRecorder(double dailyRecorder) {
		this.dailyRecorder = dailyRecorder;
	}

	@Override
	public int compareTo(ConfigurationRequestData o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
