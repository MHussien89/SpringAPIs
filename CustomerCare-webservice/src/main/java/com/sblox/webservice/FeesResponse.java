package com.sblox.webservice;

import java.util.ArrayList;
import java.util.List;

import com.sblox.common.dto.UserDto;

public class FeesResponse {

	private String fees;

	public FeesResponse() {
		super();
	}

	public FeesResponse(String fees) {
		super();
		this.fees = fees;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}
	
}
