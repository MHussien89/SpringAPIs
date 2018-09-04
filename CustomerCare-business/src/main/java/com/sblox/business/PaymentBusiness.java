package com.sblox.business;

import java.util.ArrayList;

import com.sblox.common.dto.PaymentDto;
import com.sblox.common.exception.BaseException;

public interface PaymentBusiness {

	public PaymentDto saveManualPayment(PaymentDto paymentDto) throws BaseException;
	
	public ArrayList<PaymentDto> getProjectAgents(String projectName) throws BaseException;

}
