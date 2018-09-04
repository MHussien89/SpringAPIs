package com.sblox.business.impl;

import java.util.ArrayList;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sblox.business.PaymentBusiness;
import com.sblox.business.UserBusiness;
import com.sblox.common.dto.OrganizationStatus;
import com.sblox.common.dto.PaymentDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.dataaccess.model.Payment;
//import com.sblox.dataaccess.model.PasswordResetTrial;
import com.sblox.dataaccess.model.User;
import com.sblox.dataaccess.repository.PaymentRepository;
//import com.sblox.dataaccess.repository.PasswordResetRepository;
import com.sblox.dataaccess.repository.UserRepository;

@Service("paymentBusiness")
public class PaymentBusinessImpl implements PaymentBusiness {

	@Autowired
	private Mapper mapper;

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public PaymentDto saveManualPayment(PaymentDto paymentDto) throws BaseException {
		try {
			Payment payment = mapper.map(paymentDto, Payment.class);
			payment = paymentRepository.save(payment);
			return mapper.map(payment, PaymentDto.class);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}
	
	

	@Override
	public ArrayList<PaymentDto> getProjectAgents(String projectName) throws BaseException {
		ArrayList<Payment> payments = paymentRepository.findByProjectName(projectName);

		ArrayList<PaymentDto> paymentDtos = new ArrayList<PaymentDto>();
		for (int i = 0; i < payments.size(); i++) {
			PaymentDto paymentDto = mapper.map(payments.get(i), PaymentDto.class);
			paymentDtos.add(paymentDto);
		}
		return paymentDtos;
	}

}
