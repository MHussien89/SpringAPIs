package com.sblox.business;

import java.util.ArrayList;
import java.util.List;

import com.sblox.common.dto.MailDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Mail;
//import com.sblox.dataaccess.model.PasswordResetTrial;

public interface MailBusiness {


    public MailDto saveMail(MailDto mailDto) throws BaseException;

    public List<MailDto> getMailBySuccess(String success) throws BaseException;

   

}
