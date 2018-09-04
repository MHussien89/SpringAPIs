package com.sblox.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mail.sblox.dataaccess.model.Mail;
import com.mail.sblox.dataaccess.repository.MailRepository;
import com.sblox.business.MailBusiness;
import com.sblox.common.dto.MailDto;
import com.sblox.common.exception.BaseException;


@Service("mailBusiness")
public class MailBusinessImpl implements MailBusiness {

	@Autowired
	private Mapper mapper;

	@Autowired
	private MailRepository mailRepository;

	@Override
	public MailDto saveMail(MailDto mailDto) throws BaseException {
		try {
			Mail mail = mapper.map(mailDto, Mail.class);
			mail = mailRepository.save(mail);
			return mapper.map(mail, MailDto.class);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public List<MailDto> getMailBySuccess(String success) throws BaseException {
		try {
			ArrayList<Mail> mails = (ArrayList<Mail>) mailRepository.findBySuccess(success);

			ArrayList<MailDto> mailDtos = new ArrayList<MailDto>();
			for (int i = 0; i < mails.size(); i++) {
				MailDto mailDto = mapper.map(mails.get(i), MailDto.class);
				mailDtos.add(mailDto);
			}
			return mailDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}

	}

}
