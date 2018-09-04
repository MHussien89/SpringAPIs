package com.sblox.service;

import java.util.List;

import com.sblox.common.dto.MailDto;
import com.sblox.common.exception.BaseException;

public interface MailService {

	public void saveMail(MailDto mailDto) throws BaseException;

	public List<MailDto> getMailBySuccess(String success) throws BaseException;

}
