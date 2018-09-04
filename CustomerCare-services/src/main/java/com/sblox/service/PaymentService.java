package com.sblox.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.PaymentDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AuthenticationResponse;
import com.sblox.common.model.UserCredentials;
import com.sblox.common.model.UserData;
import java.util.List;

public interface PaymentService {

	public void addManualPayment(PaymentDto paymentDto) throws BaseException;
	
	public void addCreditPayment(PaymentDto paymentDto) throws BaseException;
	
	public void addTrialPayment(PaymentDto paymentDto) throws BaseException;
	
	public String calculateExtraFees(PaymentDto paymentDto) throws BaseException;
	
	public ArrayList<PaymentDto> getProjectAgents(String projectName) throws BaseException;

}
