package com.sblox.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblox.business.ConfigurationBusiness;
import com.sblox.business.MailBusiness;
import com.sblox.business.OrganizationBusiness;
import com.sblox.business.PaymentBusiness;
import com.sblox.business.UserBusiness;
import com.sblox.common.dto.ActDto;
import com.sblox.common.dto.MailDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.OrganizationsExpiryDto;
import com.sblox.common.dto.PaymentDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AuthenticationResponse;
import com.sblox.common.model.UserCredentials;
import com.sblox.common.util.Defines;
import com.sblox.service.PaymentService;
import com.sblox.service.UserService;

import com.sblox.common.util.ErrorCodes;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService, ErrorCodes, Defines {

	@Autowired
	private PaymentBusiness paymentBusiness;

	@Autowired
	private ConfigurationBusiness configurationBusiness;

	@Autowired
	private OrganizationBusiness organizationBusiness;

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private MailBusiness mailBusiness;

	private Logger logger = Logger.getLogger(PaymentServiceImpl.class);

	@Transactional(rollbackFor = BaseException.class)
	@Override
	public void addCreditPayment(PaymentDto paymentDto) throws BaseException {

		logger.debug("Start PaymentServiceImpl.addCreditPayment()");

		logger.debug("Get organization by organization id: " + paymentDto.getProjectName());
		OrganizationDto organization = organizationBusiness
				.getOrganizationById(Long.valueOf(paymentDto.getProjectName()));

		int addedCoreUsers = 0;
		int addedEnterpriseUsers = 0;
		int addedOutboundUsers = 0;
		int addedRecorderUsers = 0;

		int totalAgents = 0;
		double totalCost = 0;
		double toPay = 0;

		double coreCost = 0;
		double enterpriseCost = 0;
		double outboundCost = 0;
		double recorderCost = 0;

		totalAgents = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
				+ Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
				+ Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
				+ Integer.valueOf(paymentDto.getNumberOfRecorderAgents());

		logger.debug("Total number of agents: " + totalAgents);

		String monthlyCore = configurationBusiness.getConfigurationValue("MONTHLY_CORE");
		String monthlyEnterprise = configurationBusiness.getConfigurationValue("MONTHLY_ENTERPRISE");
		String monthlyOutbound = configurationBusiness.getConfigurationValue("MONTHLY_OUTBOUND");
		String monthlyRecorder = configurationBusiness.getConfigurationValue("MONTHLY_RECORDER");

		logger.debug("monthlyCore Rate: " + monthlyCore);
		logger.debug("monthlyEnterprise Rate: " + monthlyEnterprise);
		logger.debug("monthlyOutbound Rate: " + monthlyOutbound);
		logger.debug("monthlyRecorder Rate: " + monthlyRecorder);

		if (organization.getEndDate() != null) {
			logger.debug("Update current subcription");
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
			try {
				date1 = formatter.parse(organization.getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar now = Calendar.getInstance();

			int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

			if (organization.getNumberOfCoreAgents() != paymentDto.getNumberOfCoreAgents()) {
				addedCoreUsers = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
						- Integer.valueOf(organization.getNumberOfCoreAgents());
				String dailyCore = configurationBusiness.getConfigurationValue("DAILY_CORE");
				coreCost = addedCoreUsers * Double.valueOf(dailyCore) * diffInDays;

			}
			if (organization.getNumberOfEnterpriseAgents() != paymentDto.getNumberOfEnterpriseAgents()) {
				addedEnterpriseUsers = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
						- Integer.valueOf(organization.getNumberOfEnterpriseAgents());
				String dailyEnterprise = configurationBusiness.getConfigurationValue("DAILY_ENTERPRISE");
				enterpriseCost = addedEnterpriseUsers * Double.valueOf(dailyEnterprise) * diffInDays;
			}
			if (organization.getNumberOfOutboundAgents() != paymentDto.getNumberOfOutboundAgents()) {
				addedOutboundUsers = Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
						- Integer.valueOf(organization.getNumberOfOutboundAgents());
				String dailyOutbound = configurationBusiness.getConfigurationValue("DAILY_OUTBOUND");
				outboundCost = addedOutboundUsers * Double.valueOf(dailyOutbound) * diffInDays;
			}
			if (organization.getNumberOfRecorderAgents() != paymentDto.getNumberOfRecorderAgents()) {
				addedRecorderUsers = Integer.valueOf(paymentDto.getNumberOfRecorderAgents())
						- Integer.valueOf(organization.getNumberOfRecorderAgents());
				String dailyRecorder = configurationBusiness.getConfigurationValue("DAILY_RECORDER");
				recorderCost = addedRecorderUsers * Double.valueOf(dailyRecorder) * diffInDays;
			}
			int newNumberOfMonths = 0;
			if (paymentDto.getNumberOfMonths() != organization.getNumberOfMonths()) {
				newNumberOfMonths = paymentDto.getNumberOfMonths() - organization.getNumberOfMonths();

				coreCost = coreCost
						+ (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore) * newNumberOfMonths);
				enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
						* Double.valueOf(monthlyEnterprise) * newNumberOfMonths);
				outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() * Double.valueOf(monthlyOutbound)
						* newNumberOfMonths);
				recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() * Double.valueOf(monthlyRecorder)
						* newNumberOfMonths);

			}

			toPay = coreCost + enterpriseCost + outboundCost + recorderCost;
			totalCost = organization.getTotalCost() + toPay;

			System.out.println(diffInDays);
			organization.setUpdated(true);

		} else {
			logger.debug("New subcription");
			coreCost = Integer.valueOf(paymentDto.getNumberOfCoreAgents()) * Double.valueOf(monthlyCore)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Core Cost: " + coreCost);

			enterpriseCost = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
					* Double.valueOf(monthlyEnterprise) * Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Enterprise Cost: " + enterpriseCost);

			outboundCost = Integer.valueOf(paymentDto.getNumberOfOutboundAgents()) * Double.valueOf(monthlyOutbound)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Outbound Cost: " + outboundCost);

			recorderCost = Integer.valueOf(paymentDto.getNumberOfRecorderAgents()) * Double.valueOf(monthlyRecorder)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Recorder Cost: " + recorderCost);

			totalCost = coreCost + enterpriseCost + outboundCost + recorderCost;

			logger.debug("Total Cost and toPay value is: " + totalCost);

			toPay = totalCost;
		}

		// double totalCost = totalAgents * 15;
		paymentDto.setTotalCost(totalCost);
		organization.setToPay(toPay);
		organization.setTotalNumberOfAgents(totalAgents);
		organization.setNumberOfCoreAgents(paymentDto.getNumberOfCoreAgents());
		organization.setNumberOfEnterpriseAgents(paymentDto.getNumberOfEnterpriseAgents());
		organization.setNumberOfOutboundAgents(paymentDto.getNumberOfOutboundAgents());
		organization.setNumberOfRecorderAgents(paymentDto.getNumberOfRecorderAgents());
		organization.setSupersCount(paymentDto.getSupersCount());

		UserDto userDto = organization.getUsers().stream().filter(user -> user.getPrimaryUser().equalsIgnoreCase("1"))
				.findFirst().orElse(null);

		userDto.setActive(true);

		logger.debug("Save the updated user with the new status in the DB");
		userBusiness.saveUser(userDto);
		logger.debug("User updated successfully");

		organization.setActive(true);
		organization.setFirstLogin("-1");

		Calendar nowEnd = Calendar.getInstance();
		Calendar nowUpdate = Calendar.getInstance();

		Calendar nowStart = Calendar.getInstance();

		nowEnd.add(Calendar.MONTH, Integer.valueOf(paymentDto.getNumberOfMonths()));
		organization.setEndDate(nowEnd.getTime().toString());

		organization.setStartDate(nowStart.getTime().toString());

		// organization.setEndDate(paymentDto.getEndDate());
		organization.setNumberOfMonths(paymentDto.getNumberOfMonths());
		organization.setIntervalType(paymentDto.getInterval());
		organization.setTotalCost(totalCost);

		logger.debug("Update the organization with the subscription details");
		organizationBusiness.saveOrganization(organization);

		ActDto actDto = new ActDto();

		actDto.setId(organization.getId());
		actDto.setAdminUserName(userDto.getUserName());
		actDto.setChat(1);
		actDto.setEmail(1);
		actDto.setCCEnterprise(paymentDto.getNumberOfEnterpriseAgents());
		actDto.setInbound(paymentDto.getNumberOfCoreAgents());
		actDto.setOutBound(paymentDto.getNumberOfOutboundAgents());
		actDto.setPassword(userDto.getHashedPassword());

		actDto.setLastUpdate(nowUpdate.getTime());

		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
		Date date1 = new Date();
		try {
			date1 = formatter.parse(nowStart.getTime().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actDto.setStartDate(date1);

		Date date = new Date();
		try {
			date = formatter.parse(organization.getEndDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actDto.setEndDate(date);

		// actDto.setEndDate(date1);
		actDto.setProjectName(organization.getName());
		actDto.setRecording(paymentDto.getNumberOfRecorderAgents());
		actDto.setSocialMedia(0);
		int mainAgents = paymentDto.getNumberOfEnterpriseAgents() + paymentDto.getNumberOfCoreAgents()
				+ paymentDto.getNumberOfOutboundAgents();
		if (mainAgents < 10) {
			actDto.setSupersCount(0);
		} else {
			double half = ((double) mainAgents / 10);
			String firstPart = String.valueOf(half);
			if (firstPart.indexOf('.') != -1) {
				firstPart = String.valueOf(half).substring(0, String.valueOf(half).indexOf('.'));
				String secondPart = String.valueOf(half).substring(String.valueOf(half).indexOf('.') + 1);
				if (Integer.valueOf(secondPart) > 5)
					firstPart = String.valueOf(Integer.valueOf(firstPart) + 1);
			}

			actDto.setSupersCount(Integer.valueOf(firstPart));
		}

		actDto.setChat(0);
		actDto.setStatus(1);

		logger.debug("Save agents in Netlogic DB");
		organizationBusiness.approveAgents(actDto);
		logger.debug("Agents approved and added successfully in Netlogic DB");

		logger.debug("Fill the MailDTO to send an email to the user");

		MailDto mailDto = new MailDto();
		mailDto.setUserId(userDto.getId());
		mailDto.setToken(userDto.getSecurityToken());
		mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		mailDto.setMailSubject("Getting Started with S-Blox");
		mailDto.setTemplateName("emailOwnerServiceTemplate.vm");
		mailDto.setMailTo(userDto.getEmail());
		mailDto.setSuccess("-1");

		logger.debug("TemplateName: emailOwnerServiceTemplate.vm");
		logger.debug("Admin Email: " + userDto.getEmail());
		logger.debug("Username: " + mailDto.getUserName());
		logger.debug("MailSubject: Getting Started with S-Blox");

		mailBusiness.saveMail(mailDto);

		logger.debug("MailDto saved successfully in the DB");

		OrganizationsExpiryDto organizationsExpiryDto = new OrganizationsExpiryDto();
		organizationsExpiryDto.setOrganizationId(organization.getId());
		organizationsExpiryDto.setUsername(userDto.getUserName());
		organizationsExpiryDto.setEmail(userDto.getEmail());
		organizationsExpiryDto.setActive(true);
		organizationsExpiryDto.setEndDate(organization.getEndDate());

		organizationBusiness.saveOrganizationExpiry(organizationsExpiryDto);

		logger.debug("End PaymentServiceImpl.addCreditPayment()");
	}

	@Transactional(rollbackFor = BaseException.class)
	@Override
	public void addTrialPayment(PaymentDto paymentDto) throws BaseException {

		logger.debug("Start PaymentServiceImpl.addTrialPayment()");

		logger.debug("Get organization by organization id: " + paymentDto.getProjectName());
		OrganizationDto organization = organizationBusiness
				.getOrganizationById(Long.valueOf(paymentDto.getProjectName()));

		int totalAgents = 0;
		double totalCost = 0;
		double toPay = 0;

		double coreCost = 0;
		double enterpriseCost = 0;
		double outboundCost = 0;
		double recorderCost = 0;

		totalAgents = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
				+ Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
				+ Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
				+ Integer.valueOf(paymentDto.getNumberOfRecorderAgents());

		logger.debug("Total number of agents: " + totalAgents);

		String monthlyCore = configurationBusiness.getConfigurationValue("MONTHLY_CORE");
		String monthlyEnterprise = configurationBusiness.getConfigurationValue("MONTHLY_ENTERPRISE");
		String monthlyOutbound = configurationBusiness.getConfigurationValue("MONTHLY_OUTBOUND");
		String monthlyRecorder = configurationBusiness.getConfigurationValue("MONTHLY_RECORDER");

		logger.debug("monthlyCore Rate: " + monthlyCore);
		logger.debug("monthlyEnterprise Rate: " + monthlyEnterprise);
		logger.debug("monthlyOutbound Rate: " + monthlyOutbound);
		logger.debug("monthlyRecorder Rate: " + monthlyRecorder);

		logger.debug("New subcription");
		coreCost = Integer.valueOf(paymentDto.getNumberOfCoreAgents()) * Double.valueOf(monthlyCore)
				* Integer.valueOf(paymentDto.getNumberOfMonths());

		logger.debug("Core Cost: " + coreCost);

		enterpriseCost = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents()) * Double.valueOf(monthlyEnterprise)
				* Integer.valueOf(paymentDto.getNumberOfMonths());

		logger.debug("Enterprise Cost: " + enterpriseCost);

		outboundCost = Integer.valueOf(paymentDto.getNumberOfOutboundAgents()) * Double.valueOf(monthlyOutbound)
				* Integer.valueOf(paymentDto.getNumberOfMonths());

		logger.debug("Outbound Cost: " + outboundCost);

		recorderCost = Integer.valueOf(paymentDto.getNumberOfRecorderAgents()) * Double.valueOf(monthlyRecorder)
				* Integer.valueOf(paymentDto.getNumberOfMonths());

		logger.debug("Recorder Cost: " + recorderCost);

		totalCost = coreCost + enterpriseCost + outboundCost + recorderCost;

		logger.debug("Total Cost and toPay value is: " + totalCost);

		toPay = totalCost;

		// double totalCost = totalAgents * 15;
		paymentDto.setTotalCost(totalCost);
		organization.setToPay(toPay);
		organization.setTotalNumberOfAgents(totalAgents);
		organization.setNumberOfCoreAgents(paymentDto.getNumberOfCoreAgents());
		organization.setNumberOfEnterpriseAgents(paymentDto.getNumberOfEnterpriseAgents());
		organization.setNumberOfOutboundAgents(paymentDto.getNumberOfOutboundAgents());
		organization.setNumberOfRecorderAgents(paymentDto.getNumberOfRecorderAgents());
		organization.setSupersCount(paymentDto.getSupersCount());

		UserDto userDto = organization.getUsers().stream().filter(user -> user.getPrimaryUser().equalsIgnoreCase("1"))
				.findFirst().orElse(null);

		userDto.setActive(true);

		logger.debug("Save the updated user with the new status in the DB");
		userBusiness.saveUser(userDto);
		logger.debug("User updated successfully");

		organization.setActive(true);
		organization.setFirstLogin("-1");

		Calendar nowEnd = Calendar.getInstance();
		Calendar nowUpdate = Calendar.getInstance();

		Calendar nowStart = Calendar.getInstance();

		nowEnd.add(Calendar.MONTH, Integer.valueOf(paymentDto.getNumberOfMonths()));
		organization.setEndDate(nowEnd.getTime().toString());

		organization.setStartDate(nowStart.getTime().toString());

		// organization.setEndDate(paymentDto.getEndDate());
		organization.setNumberOfMonths(paymentDto.getNumberOfMonths());
		organization.setIntervalType(paymentDto.getInterval());
		organization.setTotalCost(totalCost);

		logger.debug("Update the organization with the subscription details");
		organizationBusiness.saveOrganization(organization);

		ActDto actDto = new ActDto();

		actDto.setId(organization.getId());
		actDto.setAdminUserName(userDto.getUserName());
		actDto.setChat(1);
		actDto.setEmail(1);
		actDto.setCCEnterprise(paymentDto.getNumberOfEnterpriseAgents());
		actDto.setInbound(paymentDto.getNumberOfCoreAgents());
		actDto.setOutBound(paymentDto.getNumberOfOutboundAgents());
		actDto.setPassword(userDto.getHashedPassword());

		actDto.setLastUpdate(nowUpdate.getTime());

		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
		Date date1 = new Date();
		try {
			date1 = formatter.parse(nowStart.getTime().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actDto.setStartDate(date1);

		Date date = new Date();
		try {
			date = formatter.parse(organization.getEndDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actDto.setEndDate(date);

		// actDto.setEndDate(date1);
		actDto.setProjectName(organization.getName());
		actDto.setRecording(paymentDto.getNumberOfRecorderAgents());
		actDto.setSocialMedia(0);
		int mainAgents = paymentDto.getNumberOfEnterpriseAgents() + paymentDto.getNumberOfCoreAgents()
				+ paymentDto.getNumberOfOutboundAgents();
		if (mainAgents < 10) {
			actDto.setSupersCount(0);
		} else {
			double half = ((double) mainAgents / 10);
			String firstPart = String.valueOf(half);
			if (firstPart.indexOf('.') != -1) {
				firstPart = String.valueOf(half).substring(0, String.valueOf(half).indexOf('.'));
				String secondPart = String.valueOf(half).substring(String.valueOf(half).indexOf('.') + 1);
				if (Integer.valueOf(secondPart) > 5)
					firstPart = String.valueOf(Integer.valueOf(firstPart) + 1);
			}

			actDto.setSupersCount(Integer.valueOf(firstPart));
		}

		actDto.setChat(0);
		actDto.setStatus(1);

		logger.debug("Save agents in Netlogic DB");
		organizationBusiness.approveAgents(actDto);
		logger.debug("Agents approved and added successfully in Netlogic DB");

		logger.debug("Fill the MailDTO to send an email to the user");

		MailDto mailDto = new MailDto();
		mailDto.setUserId(userDto.getId());
		mailDto.setToken(userDto.getSecurityToken());
		mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		mailDto.setMailSubject("Getting Started with S-Blox");
		mailDto.setTemplateName("emailOwnerServiceTemplate.vm");
		mailDto.setMailTo(userDto.getEmail());
		mailDto.setSuccess("-1");

		logger.debug("TemplateName: emailOwnerServiceTemplate.vm");
		logger.debug("Admin Email: " + userDto.getEmail());
		logger.debug("Username: " + mailDto.getUserName());
		logger.debug("MailSubject: Getting Started with S-Blox");

		mailBusiness.saveMail(mailDto);

		logger.debug("MailDto saved successfully in the DB");

		OrganizationsExpiryDto organizationsExpiryDto = new OrganizationsExpiryDto();
		organizationsExpiryDto.setOrganizationId(organization.getId());
		organizationsExpiryDto.setUsername(userDto.getUserName());
		organizationsExpiryDto.setEmail(userDto.getEmail());
		organizationsExpiryDto.setActive(true);
		organizationsExpiryDto.setEndDate(organization.getEndDate());

		organizationBusiness.saveOrganizationExpiry(organizationsExpiryDto);

		logger.debug("End PaymentServiceImpl.addTrialPayment()");
	}

	@Transactional(rollbackFor = BaseException.class)
	@Override
	public void addManualPayment(PaymentDto paymentDto) throws BaseException {

		logger.debug("Start PaymentServiceImpl.addManualPayment()");

		logger.debug("Get organization by organization id: " + paymentDto.getProjectName());
		OrganizationDto organization = organizationBusiness
				.getOrganizationById(Long.valueOf(paymentDto.getProjectName()));

		int addedCoreUsers = 0;
		int addedEnterpriseUsers = 0;
		int addedOutboundUsers = 0;
		int addedRecorderUsers = 0;

		int totalAgents = 0;
		double totalCost = 0;
		double toPay = 0;

		double coreCost = 0;
		double enterpriseCost = 0;
		double outboundCost = 0;
		double recorderCost = 0;

		totalAgents = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
				+ Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
				+ Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
				+ Integer.valueOf(paymentDto.getNumberOfRecorderAgents());

		logger.debug("Total number of agents: " + totalAgents);

		String monthlyCore = configurationBusiness.getConfigurationValue("MONTHLY_CORE");
		String monthlyEnterprise = configurationBusiness.getConfigurationValue("MONTHLY_ENTERPRISE");
		String monthlyOutbound = configurationBusiness.getConfigurationValue("MONTHLY_OUTBOUND");
		String monthlyRecorder = configurationBusiness.getConfigurationValue("MONTHLY_RECORDER");

		logger.debug("monthlyCore Rate: " + monthlyCore);
		logger.debug("monthlyEnterprise Rate: " + monthlyEnterprise);
		logger.debug("monthlyOutbound Rate: " + monthlyOutbound);
		logger.debug("monthlyRecorder Rate: " + monthlyRecorder);

		if (organization.getEndDate() != null) {
			logger.debug("Update current subcription");
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
			try {
				date1 = formatter.parse(organization.getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar now = Calendar.getInstance();

			int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

			if (diffInDays > 0) {
				if (organization.getNumberOfCoreAgents() != paymentDto.getNumberOfCoreAgents()) {
					addedCoreUsers = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
							- Integer.valueOf(organization.getNumberOfCoreAgents());
					String dailyCore = configurationBusiness.getConfigurationValue("DAILY_CORE");
					coreCost = addedCoreUsers * Double.valueOf(dailyCore) * diffInDays;

				}
				if (organization.getNumberOfEnterpriseAgents() != paymentDto.getNumberOfEnterpriseAgents()) {
					addedEnterpriseUsers = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
							- Integer.valueOf(organization.getNumberOfEnterpriseAgents());
					String dailyEnterprise = configurationBusiness.getConfigurationValue("DAILY_ENTERPRISE");
					enterpriseCost = addedEnterpriseUsers * Double.valueOf(dailyEnterprise) * diffInDays;
				}
				if (organization.getNumberOfOutboundAgents() != paymentDto.getNumberOfOutboundAgents()) {
					addedOutboundUsers = Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
							- Integer.valueOf(organization.getNumberOfOutboundAgents());
					String dailyOutbound = configurationBusiness.getConfigurationValue("DAILY_OUTBOUND");
					outboundCost = addedOutboundUsers * Double.valueOf(dailyOutbound) * diffInDays;
				}
				if (organization.getNumberOfRecorderAgents() != paymentDto.getNumberOfRecorderAgents()) {
					addedRecorderUsers = Integer.valueOf(paymentDto.getNumberOfRecorderAgents())
							- Integer.valueOf(organization.getNumberOfRecorderAgents());
					String dailyRecorder = configurationBusiness.getConfigurationValue("DAILY_RECORDER");
					recorderCost = addedRecorderUsers * Double.valueOf(dailyRecorder) * diffInDays;
				}
			}

			int newNumberOfMonths = 0;
			if (paymentDto.getNumberOfMonths() != organization.getNumberOfMonths() && diffInDays > 0) {
				organization.setDateUpdated(true);
				newNumberOfMonths = paymentDto.getNumberOfMonths() - organization.getNumberOfMonths();

				coreCost = coreCost
						+ (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore) * newNumberOfMonths);
				enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
						* Double.valueOf(monthlyEnterprise) * newNumberOfMonths);
				outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() * Double.valueOf(monthlyOutbound)
						* newNumberOfMonths);
				recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() * Double.valueOf(monthlyRecorder)
						* newNumberOfMonths);

			} else if (diffInDays <= 0) {
				coreCost = coreCost + (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore)
						* paymentDto.getNumberOfMonths());
				enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
						* Double.valueOf(monthlyEnterprise) * paymentDto.getNumberOfMonths());
				outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() * Double.valueOf(monthlyOutbound)
						* paymentDto.getNumberOfMonths());
				recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() * Double.valueOf(monthlyRecorder)
						* paymentDto.getNumberOfMonths());
			}

			toPay = coreCost + enterpriseCost + outboundCost + recorderCost + organization.getToPay();
			totalCost = organization.getTotalCost() + toPay;

			System.out.println(diffInDays);
			organization.setUpdated(true);

		} else {
			logger.debug("New subcription");
			coreCost = Integer.valueOf(paymentDto.getNumberOfCoreAgents()) * Double.valueOf(monthlyCore)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Core Cost: " + coreCost);

			enterpriseCost = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
					* Double.valueOf(monthlyEnterprise) * Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Enterprise Cost: " + enterpriseCost);

			outboundCost = Integer.valueOf(paymentDto.getNumberOfOutboundAgents()) * Double.valueOf(monthlyOutbound)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Outbound Cost: " + outboundCost);

			recorderCost = Integer.valueOf(paymentDto.getNumberOfRecorderAgents()) * Double.valueOf(monthlyRecorder)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Recorder Cost: " + recorderCost);

			totalCost = coreCost + enterpriseCost + outboundCost + recorderCost;

			logger.debug("Total Cost and toPay value is: " + totalCost);

			toPay = totalCost;
		}

		// double totalCost = totalAgents * 15;
		paymentDto.setTotalCost(totalCost);
		organization.setToPay(toPay);
		organization.setTotalNumberOfAgents(totalAgents);
		organization.setNumberOfCoreAgents(paymentDto.getNumberOfCoreAgents());
		organization.setNumberOfEnterpriseAgents(paymentDto.getNumberOfEnterpriseAgents());
		organization.setNumberOfOutboundAgents(paymentDto.getNumberOfOutboundAgents());
		organization.setNumberOfRecorderAgents(paymentDto.getNumberOfRecorderAgents());
		organization.setSupersCount(paymentDto.getSupersCount());
		// organization.setStartDate(paymentDto.getStartDate());
		// organization.setEndDate(paymentDto.getEndDate());
		organization.setNumberOfMonths(paymentDto.getNumberOfMonths());
		organization.setIntervalType(paymentDto.getInterval());
		organization.setTotalCost(totalCost);

		logger.debug("Update the organization with the subscription details");
		organizationBusiness.saveOrganization(organization);
		logger.debug("The organization updated successfully");
		logger.debug("End PaymentServiceImpl.addManualPayment()");
	}

	@Override
	public ArrayList<PaymentDto> getProjectAgents(String projectName) throws BaseException {
		return paymentBusiness.getProjectAgents(projectName);
	}

	@Override
	public String calculateExtraFees(PaymentDto paymentDto) throws BaseException {
		logger.debug("Start PaymentServiceImpl.calculateExtraFees()");

		logger.debug("Get organization by organization id: " + paymentDto.getProjectName());

		OrganizationDto organization = organizationBusiness
				.getOrganizationById(Long.valueOf(paymentDto.getProjectName()));

		int addedCoreUsers = 0;
		int addedEnterpriseUsers = 0;
		int addedOutboundUsers = 0;
		int addedRecorderUsers = 0;

		int totalAgents = 0;
		double totalCost = 0;
		double toPay = 0;

		double coreCost = 0;
		double enterpriseCost = 0;
		double outboundCost = 0;
		double recorderCost = 0;

		totalAgents = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
				+ Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
				+ Integer.valueOf(paymentDto.getNumberOfOutboundAgents());

		logger.debug("Total number of agents: " + totalAgents);

		String monthlyCore = configurationBusiness.getConfigurationValue("MONTHLY_CORE");
		String monthlyEnterprise = configurationBusiness.getConfigurationValue("MONTHLY_ENTERPRISE");
		String monthlyOutbound = configurationBusiness.getConfigurationValue("MONTHLY_OUTBOUND");
		String monthlyRecorder = configurationBusiness.getConfigurationValue("MONTHLY_RECORDER");

		logger.debug("monthlyCore Rate: " + monthlyCore);
		logger.debug("monthlyEnterprise Rate: " + monthlyEnterprise);
		logger.debug("monthlyOutbound Rate: " + monthlyOutbound);
		logger.debug("monthlyRecorder Rate: " + monthlyRecorder);

		if (organization.getEndDate() != null) {
			logger.debug("Update current subcription");
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
			try {
				date1 = formatter.parse(organization.getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar now = Calendar.getInstance();

			int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

			if (diffInDays > 0) {				
				if (organization.getNumberOfCoreAgents() != paymentDto.getNumberOfCoreAgents()) {
					addedCoreUsers = Integer.valueOf(paymentDto.getNumberOfCoreAgents())
							- Integer.valueOf(organization.getNumberOfCoreAgents());
					String dailyCore = configurationBusiness.getConfigurationValue("DAILY_CORE");
					coreCost = addedCoreUsers * Double.valueOf(dailyCore) * diffInDays;
					
				}
				if (organization.getNumberOfEnterpriseAgents() != paymentDto.getNumberOfEnterpriseAgents()) {
					addedEnterpriseUsers = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
							- Integer.valueOf(organization.getNumberOfEnterpriseAgents());
					String dailyEnterprise = configurationBusiness.getConfigurationValue("DAILY_ENTERPRISE");
					enterpriseCost = addedEnterpriseUsers * Double.valueOf(dailyEnterprise) * diffInDays;
				}
				if (organization.getNumberOfOutboundAgents() != paymentDto.getNumberOfOutboundAgents()) {
					addedOutboundUsers = Integer.valueOf(paymentDto.getNumberOfOutboundAgents())
							- Integer.valueOf(organization.getNumberOfOutboundAgents());
					String dailyOutbound = configurationBusiness.getConfigurationValue("DAILY_OUTBOUND");
					outboundCost = addedOutboundUsers * Double.valueOf(dailyOutbound) * diffInDays;
				}
				if (organization.getNumberOfRecorderAgents() != paymentDto.getNumberOfRecorderAgents()) {
					addedRecorderUsers = Integer.valueOf(paymentDto.getNumberOfRecorderAgents())
							- Integer.valueOf(organization.getNumberOfRecorderAgents());
					String dailyRecorder = configurationBusiness.getConfigurationValue("DAILY_RECORDER");
					recorderCost = addedRecorderUsers * Double.valueOf(dailyRecorder) * diffInDays;
				}
			}
			int newNumberOfMonths = 0;
			
			if (paymentDto.getNumberOfMonths() != organization.getNumberOfMonths() && diffInDays > 0) {
				organization.setDateUpdated(true);
				newNumberOfMonths = paymentDto.getNumberOfMonths() - organization.getNumberOfMonths();

				coreCost = coreCost
						+ (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore) * newNumberOfMonths);
				enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
						* Double.valueOf(monthlyEnterprise) * newNumberOfMonths);
				outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() * Double.valueOf(monthlyOutbound)
						* newNumberOfMonths);
				recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() * Double.valueOf(monthlyRecorder)
						* newNumberOfMonths);

			} else if (diffInDays <= 0) {
				coreCost = coreCost + (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore)
						* paymentDto.getNumberOfMonths());
				enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
						* Double.valueOf(monthlyEnterprise) * paymentDto.getNumberOfMonths());
				outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() * Double.valueOf(monthlyOutbound)
						* paymentDto.getNumberOfMonths());
				recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() * Double.valueOf(monthlyRecorder)
						* paymentDto.getNumberOfMonths());
			}
			
			
			// if (paymentDto.getNumberOfMonths() != organization.getNumberOfMonths()) {
			// newNumberOfMonths = paymentDto.getNumberOfMonths() -
			// organization.getNumberOfMonths();
			//
			// coreCost = coreCost
			// + (paymentDto.getNumberOfCoreAgents() * Double.valueOf(monthlyCore) *
			// newNumberOfMonths);
			// enterpriseCost = enterpriseCost + (paymentDto.getNumberOfEnterpriseAgents()
			// * Double.valueOf(monthlyEnterprise) * newNumberOfMonths);
			// outboundCost = outboundCost + (paymentDto.getNumberOfOutboundAgents() *
			// Double.valueOf(monthlyOutbound)
			// * newNumberOfMonths);
			// recorderCost = recorderCost + (paymentDto.getNumberOfRecorderAgents() *
			// Double.valueOf(monthlyRecorder)
			// * newNumberOfMonths);
			//
			// }

			toPay = coreCost + enterpriseCost + outboundCost + recorderCost + organization.getToPay();
			totalCost = organization.getTotalCost() + toPay;

			System.out.println(diffInDays);
			organization.setUpdated(true);

		} else {
			logger.debug("New subcription");
			coreCost = Integer.valueOf(paymentDto.getNumberOfCoreAgents()) * Double.valueOf(monthlyCore)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Core Cost: " + coreCost);

			enterpriseCost = Integer.valueOf(paymentDto.getNumberOfEnterpriseAgents())
					* Double.valueOf(monthlyEnterprise) * Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Enterprise Cost: " + enterpriseCost);

			outboundCost = Integer.valueOf(paymentDto.getNumberOfOutboundAgents()) * Double.valueOf(monthlyOutbound)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Outbound Cost: " + outboundCost);

			recorderCost = Integer.valueOf(paymentDto.getNumberOfRecorderAgents()) * Double.valueOf(monthlyRecorder)
					* Integer.valueOf(paymentDto.getNumberOfMonths());

			logger.debug("Recorder Cost: " + recorderCost);

			totalCost = coreCost + enterpriseCost + outboundCost + recorderCost;

			logger.debug("Total Cost and toPay value is: " + totalCost);

			toPay = totalCost;
		}
		logger.debug("End PaymentServiceImpl.calculateExtraFees()");
		return new DecimalFormat("##.##").format(toPay);
	}
}
