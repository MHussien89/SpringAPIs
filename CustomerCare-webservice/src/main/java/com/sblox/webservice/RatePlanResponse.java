package com.sblox.webservice;

import java.util.ArrayList;
import java.util.List;

import com.sblox.common.dto.UserDto;

public class RatePlanResponse {

	private double monthlyCore;
	private double monthlyEnterprise;
	private double monthlyOutbound;
	private double monthlyRecorder;

	private double dailyCore;
	private double dailyEnterprise;
	private double dailyOutbound;
	private double dailyRecorder;

	public RatePlanResponse() {
		super();
	}

	public RatePlanResponse(double monthlyCore, double monthlyEnterprise, double monthlyOutbound, double monthlyRecorder,
			double dailyCore, double dailyEnterprise, double dailyOutbound, double dailyRecorder) {
		super();
		this.monthlyCore = monthlyCore;
		this.monthlyEnterprise = monthlyEnterprise;
		this.monthlyOutbound = monthlyOutbound;
		this.monthlyRecorder = monthlyRecorder;
		this.dailyCore = dailyCore;
		this.dailyEnterprise = dailyEnterprise;
		this.dailyOutbound = dailyOutbound;
		this.dailyRecorder = dailyRecorder;
	}

	public double getMonthlyCore() {
		return monthlyCore;
	}

	public void setMonthlyCore(double monthlyCore) {
		this.monthlyCore = monthlyCore;
	}

	public double getMonthlyEnterprise() {
		return monthlyEnterprise;
	}

	public void setMonthlyEnterprise(double monthlyEnterprise) {
		this.monthlyEnterprise = monthlyEnterprise;
	}

	public double getMonthlyOutbound() {
		return monthlyOutbound;
	}

	public void setMonthlyOutbound(double monthlyOutbound) {
		this.monthlyOutbound = monthlyOutbound;
	}

	public double getMonthlyRecorder() {
		return monthlyRecorder;
	}

	public void setMonthlyRecorder(double monthlyRecorder) {
		this.monthlyRecorder = monthlyRecorder;
	}

	public double getDailyCore() {
		return dailyCore;
	}

	public void setDailyCore(double dailyCore) {
		this.dailyCore = dailyCore;
	}

	public double getDailyEnterprise() {
		return dailyEnterprise;
	}

	public void setDailyEnterprise(double dailyEnterprise) {
		this.dailyEnterprise = dailyEnterprise;
	}

	public double getDailyOutbound() {
		return dailyOutbound;
	}

	public void setDailyOutbound(double dailyOutbound) {
		this.dailyOutbound = dailyOutbound;
	}

	public double getDailyRecorder() {
		return dailyRecorder;
	}

	public void setDailyRecorder(double dailyRecorder) {
		this.dailyRecorder = dailyRecorder;
	}

}
