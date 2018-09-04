package com.sblox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sblox.business.MailBusiness;
import com.sblox.common.dto.MailDto;
import com.sblox.common.exception.BaseException;
import com.sblox.service.MailService;
import com.sblox.common.util.Defines;

@Service
public class EmailServiceImpl implements MailService, Defines {

	@Autowired
	private MailBusiness mailBusiness;

	@Override
	public List<MailDto> getMailBySuccess(String success) throws BaseException {
		// TODO Auto-generated method stub
		return mailBusiness.getMailBySuccess(success);
	}

	@Override
	public void saveMail(MailDto mailDto) throws BaseException {
		mailBusiness.saveMail(mailDto);
	}

}
