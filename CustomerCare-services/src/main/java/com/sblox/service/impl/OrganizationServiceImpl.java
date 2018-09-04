package com.sblox.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sblox.business.MailBusiness;
import com.sblox.business.OrganizationBusiness;

import com.sblox.business.UserBusiness;
import com.sblox.common.dto.ActDto;
import com.sblox.common.dto.MailDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.OrganizationsExpiryDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.OrganizationData;
import com.sblox.service.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	private Logger logger = Logger.getLogger(OrganizationServiceImpl.class);

	@Autowired
	private OrganizationBusiness organizationBusiness;
	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private MailBusiness mailBusiness;

	// @Transactional(rollbackFor = { BaseException.class })
	@Override
	public int updateStatus(ArrayList<String> usersIds, boolean status) throws BaseException {
		logger.debug("Start OrganizationServiceImpl.updateStatus");
		int finishedUsers = 0;
		try {
			// TODO Auto-generated method stub
			for (int i = 0; i < usersIds.size(); i++) {
				UserDto userDto = userBusiness.getUserById(Long.valueOf(usersIds.get(i)));
				userDto.setActive(status);
				userDto.getOrganization().setActive(status);
				userDto.getOrganization().setFirstLogin("-1");
				logger.debug("Call the saveUser function inside userBusiness");
				userBusiness.saveUser(userDto);
				logger.debug("Call the saveOrganization function inside organizationBusiness");
				organizationBusiness.saveOrganization(userDto.getOrganization());
				logger.debug("OrganizationServiceImpl.updateStatus finished successfully");
				finishedUsers = finishedUsers + 1;
			}
		} catch (BaseException e) {
			logger.error(e.getMessage());
		}
		return finishedUsers;

	}

	// public static void main(String[]args) {
	// String da = "Thu Feb 26 19:47:52 EET 2018";
	//
	// SimpleDateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss zzz
	// yyyy");
	// Date date = new Date();
	// try {
	// date=formatter.parse(da);
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// System.out.println(date);
	// }

	@Transactional(rollbackFor = { BaseException.class })
	@Override
	public int approveAgents(ArrayList<String> usersIds, boolean status) throws BaseException {
		logger.debug("Start OrganizationServiceImpl.approveAgents");

		int finishedUsers = 0;
		try {

			logger.debug("Loop on the list of agents to approve them");
			for (int i = 0; i < usersIds.size(); i++) {

				logger.debug("Get user by id: " + Long.valueOf(usersIds.get(i)));
				UserDto userDto = userBusiness.getUserById(Long.valueOf(usersIds.get(i)));
				logger.debug("User retrieved successfully from the DB");
				logger.debug("Update the status of the user and organization to be active");
				userDto.setActive(status);
				userDto.getOrganization().setActive(status);
				userDto.getOrganization().setFirstLogin("-1");
				userDto.getOrganization().setToPay(0);
				logger.debug("Save the updated user with the new status in the DB");
				userBusiness.saveUser(userDto);
				logger.debug("User updated successfully");

				OrganizationDto org = userDto.getOrganization();

				SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
				Calendar nowEnd = Calendar.getInstance();
				ActDto actDto = new ActDto();

				if (userDto.getOrganization().isUpdated() == true)
					actDto.setEditFlag(true);

				if (org.getStartDate() == null && org.getEndDate() == null) {
					userDto.getOrganization().setStartDate(nowEnd.getTime().toString());

					nowEnd.add(Calendar.MONTH, Integer.valueOf(org.getNumberOfMonths()));
					userDto.getOrganization().setEndDate(nowEnd.getTime().toString());
				}

				if (userDto.getOrganization().isDateUpdated() == true) {
					nowEnd.add(Calendar.MONTH, Integer.valueOf(org.getNumberOfMonths()));
					userDto.getOrganization().setEndDate(nowEnd.getTime().toString());
				}
				// else {
				// Date startDate = new Date();
				// try {
				// startDate = formatter.parse(org.getStartDate());
				//
				// Calendar startCal = Calendar.getInstance();
				// startCal.setTime(startDate);
				// startCal.add(Calendar.MONTH, Integer.valueOf(org.getNumberOfMonths()));
				// userDto.getOrganization().setEndDate(startCal.getTime().toString());
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }

				userDto.getOrganization().setUpdated(false);
				userDto.getOrganization().setDateUpdated(false);

				String DATE_FORMAT = "E MMM dd HH:mm:ss zzz yyyy";
				ZoneId singaporeZoneId = ZoneId.of(userDto.getOrganization().getTimeZone());
				LocalDateTime start = LocalDateTime.parse(userDto.getOrganization().getStartDate(),
						DateTimeFormatter.ofPattern(DATE_FORMAT));
				LocalDateTime end = LocalDateTime.parse(userDto.getOrganization().getEndDate(),
						DateTimeFormatter.ofPattern(DATE_FORMAT));
				ZonedDateTime customeZoneStart = start.atZone(singaporeZoneId);
				ZonedDateTime customeZoneEnd = end.atZone(singaporeZoneId);
				DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);

				userDto.getOrganization().setStartDate(format.format(customeZoneStart));
				userDto.getOrganization().setEndDate(format.format(customeZoneEnd));

				organizationBusiness.saveOrganization(userDto.getOrganization());

				logger.debug("Prepare the Netlogic record");

				actDto.setId(org.getId());
				actDto.setAdminUserName(userDto.getUserName());
				actDto.setChat(1);
				actDto.setEmail(1);
				actDto.setCCEnterprise(org.getNumberOfEnterpriseAgents());
				actDto.setInbound(org.getNumberOfCoreAgents());
				actDto.setOutBound(org.getNumberOfOutboundAgents());
				actDto.setPassword(userDto.getHashedPassword());
				Calendar nowUpdate = Calendar.getInstance();

				actDto.setLastUpdate(nowUpdate.getTime());

				Calendar nowStart = Calendar.getInstance();
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
					date = formatter.parse(org.getEndDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				actDto.setEndDate(date);

				actDto.setProjectName(org.getName());
				actDto.setRecording(org.getNumberOfRecorderAgents());
				actDto.setSocialMedia(0);
				int mainAgents = org.getNumberOfEnterpriseAgents() + org.getNumberOfCoreAgents()
						+ org.getNumberOfOutboundAgents();
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
				finishedUsers = finishedUsers + 1;

				logger.debug("Save agents in Netlogic DB");
				logger.debug("Fill the MailDTO to send an email to the user");

				MailDto mailDto = new MailDto();
				mailDto.setUserId(userDto.getId());
				mailDto.setToken(userDto.getSecurityToken());
				mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
				mailDto.setMailSubject("Getting Started with S-Blox");
				mailDto.setTemplateName("emailOwnerServiceTemplate.vm");
				mailDto.setMailTo(userDto.getEmail());
				mailDto.setSuccess("-1");
				// mailBusiness.saveMail(mailDto);

				logger.debug("TemplateName: emailOwnerServiceTemplate.vm");
				logger.debug("Admin Email: " + userDto.getEmail());
				logger.debug("Username: " + mailDto.getUserName());
				logger.debug("MailSubject: Getting Started with S-Blox");

				mailBusiness.saveMail(mailDto);
				logger.debug("MailDto saved successfully in the DB");

				OrganizationsExpiryDto organizationsExpiryDto = new OrganizationsExpiryDto();
				organizationsExpiryDto.setOrganizationId(org.getId());
				organizationsExpiryDto.setUsername(userDto.getUserName());
				organizationsExpiryDto.setEmail(userDto.getEmail());
				organizationsExpiryDto.setActive(true);
				organizationsExpiryDto.setEndDate(org.getEndDate());

				organizationBusiness.saveOrganizationExpiry(organizationsExpiryDto);

			}
		} catch (BaseException e) {
			logger.error(e.getMessage());
			throw e;
		}
		logger.debug("End OrganizationServiceImpl.approveAgents");
		return finishedUsers;

	}

	@Transactional(rollbackFor = { BaseException.class })
	@Override
	public int suspendAgents(ArrayList<String> usersIds) throws BaseException {
		logger.debug("Start OrganizationServiceImpl.approveAgents");

		int finishedUsers = 0;
		try {

			logger.debug("Loop on the list of agents to suspend them");
			for (int i = 0; i < usersIds.size(); i++) {

				logger.debug("Get user by id: " + Long.valueOf(usersIds.get(i)));
				UserDto userDto = userBusiness.getUserById(Long.valueOf(usersIds.get(i)));
				logger.debug("User retrieved successfully from the DB");
				// logger.debug("Update the status of the user and organization to be active");
				// userDto.setActive(false);
				// userDto.getOrganization().setActive(false);
				//
				// logger.debug("Save the updated user with the new status in the DB");
				// userBusiness.saveUser(userDto);
				// logger.debug("User updated successfully");

				OrganizationDto org = userDto.getOrganization();

				ActDto actDto = organizationBusiness.getNetLogicOrganizationById(org.getId());

				// organizationBusiness.saveOrganization(userDto.getOrganization());

				logger.debug("Prepare the Netlogic record");

				actDto.setStatus(0);

				logger.debug("Update agents in Netlogic DB");
				organizationBusiness.suspendAgents(actDto);
				logger.debug("Agents suspended successfully in Netlogic DB");
				// finishedUsers = finishedUsers + 1;

				// logger.debug("Save agents in Netlogic DB");
				// logger.debug("Fill the MailDTO to send an email to the user");

				// MailDto mailDto = new MailDto();
				// mailDto.setUserId(userDto.getId());
				// mailDto.setToken(userDto.getSecurityToken());
				// mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
				// mailDto.setMailSubject("Getting Started with S-Blox");
				// mailDto.setTemplateName("emailOwnerServiceTemplate.vm");
				// mailDto.setMailTo(userDto.getEmail());
				// mailDto.setSuccess("-1");
				// // mailBusiness.saveMail(mailDto);
				//
				// logger.debug("TemplateName: emailOwnerServiceTemplate.vm");
				// logger.debug("Admin Email: " + userDto.getEmail());
				// logger.debug("Username: " + mailDto.getUserName());
				// logger.debug("MailSubject: Getting Started with S-Blox");
				//
				// mailBusiness.saveMail(mailDto);
				// logger.debug("MailDto saved successfully in the DB");

			}
		} catch (BaseException e) {
			logger.error(e.getMessage());
			throw e;
		}
		logger.debug("End OrganizationServiceImpl.approveAgents");
		return finishedUsers;

	}

	@Transactional(rollbackFor = { BaseException.class })
	@Override
	public void deleteOrganizations(ArrayList<String> organizationIds) throws BaseException {
		for (int i = 0; i < organizationIds.size(); i++) {
			OrganizationDto organizationDto = organizationBusiness
					.getOrganizationById(Long.valueOf(organizationIds.get(i)));
			ArrayList<UserDto> userDtos = (ArrayList<UserDto>) organizationDto.getUsers();
			for (int x = 0; x < userDtos.size(); x++) {
				UserDto userDto = userDtos.get(x);
				userBusiness.deleteUserById(userDto.getId());
			}
			organizationBusiness.deleteOrganization(organizationDto.getId());
		}

	}

	@Override
	public void updateOrganization(OrganizationData organizationData) throws BaseException {

		OrganizationDto organization = organizationBusiness.getOrganizationById(organizationData.getId());
		organization.setName(organizationData.getName());
		organization.setNumberOfActiveUsers(organizationData.getNumberOfActiveUsers());
		organization.setActive(organizationData.isActive());
		organization.setFirstLogin("-1");
		OrganizationDto existingOrganizationDto = organizationBusiness
				.getOrganizationByName(organizationData.getName());

		if (existingOrganizationDto != null && existingOrganizationDto.getId() != organizationData.getId()) {
			throw new BaseException("1", "The Organization name is already used before");
		}

		if (organizationData.getNumberOfActiveUsers() != organization.getNumberOfActiveUsers()) {
			List<UserDto> result = organization.getUsers().stream().filter(userr -> userr.isActive() == true)
					.collect(Collectors.toList());

			if (organizationData.getNumberOfActiveUsers() < result.size()) {
				throw new BaseException("", "Minimum number of active users you can change to is: " + result.size());
			}
		}

		UserDto primaryUser = organization.getUsers().stream()
				.filter(userr -> userr.getPrimaryUser() != null && userr.getPrimaryUser().equalsIgnoreCase("1"))
				.collect(Collectors.toList()).get(0);

		primaryUser.setActive(organizationData.isActive());
		userBusiness.saveUser(primaryUser);
		// userDto.setFirstLogin("-1");

		organizationBusiness.saveOrganization(organization);
	}

	@Override
	public void acceptOrganization(ArrayList<String> usersIds) throws BaseException {

		for (int i = 0; i < usersIds.size(); i++) {
			OrganizationDto organization = organizationBusiness.getOrganizationById(Long.valueOf(usersIds.get(i)));
			organization.setAccepted(true);

			List<UserDto> result = organization.getUsers().stream()
					.filter(userr -> userr.getPrimaryUser() != null && userr.getPrimaryUser().equalsIgnoreCase("1"))
					.collect(Collectors.toList());
			UserDto userDto = result.get(0);
			userDto.setActive(true);
			userBusiness.saveUser(userDto);
			organizationBusiness.saveOrganization(organization);

			MailDto mailDto = new MailDto();
			mailDto.setUserId(userDto.getId());
			mailDto.setToken(userDto.getSecurityToken());
			mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			mailDto.setMailSubject("Getting Started with S-Blox");
			mailDto.setTemplateName("emailOwnerWelcomeTemplate.vm");
			mailDto.setMailTo(userDto.getEmail());
			mailDto.setSuccess("-1");
			mailBusiness.saveMail(mailDto);

		}

	}

	@Override
	public void submitOrder(OrganizationDto organizationDto) throws BaseException {
		// call payment gateway first and after that send an email
		organizationBusiness.saveOrganization(organizationDto);
		List<UserDto> result = organizationDto.getUsers().stream()
				.filter(userr -> userr.getPrimaryUser() != null && userr.getPrimaryUser().equalsIgnoreCase("1"))
				.collect(Collectors.toList());
		UserDto userDto = result.get(0);

		MailDto mailDto = new MailDto();
		mailDto.setUserId(userDto.getId());
		mailDto.setToken(userDto.getSecurityToken());
		mailDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		mailDto.setMailSubject("Getting Started with S-Blox");
		mailDto.setTemplateName("emailOwnerServiceTemplate.vm");
		mailDto.setMailTo(userDto.getEmail());
		mailDto.setSuccess("-1");
		mailBusiness.saveMail(mailDto);

	}

	@Override
	public void saveAddress(OrganizationDto organizationDto) throws BaseException {
		logger.debug("Start OrganizationServiceImpl.saveAddress()");
		logger.debug("Get organization by ID: " + organizationDto.getId());
		OrganizationDto updatedOrganizationDto = organizationBusiness.getOrganizationById(organizationDto.getId());
		updatedOrganizationDto.setAddress(organizationDto.getAddress());
		updatedOrganizationDto.setCity(organizationDto.getCity());
		updatedOrganizationDto.setCountry(organizationDto.getCountry());
		updatedOrganizationDto.setModeOfPayment(organizationDto.getModeOfPayment());
		logger.debug("Save the updated organization");
		organizationBusiness.saveOrganization(updatedOrganizationDto);
		logger.debug("End OrganizationServiceImpl.saveAddress()");
	}

	@Override
	public OrganizationDto getOrganizationById(long id) throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void suspendOrganization(OrganizationDto organizationDto) throws BaseException {
		ActDto actDto = new ActDto();
		actDto.setId(organizationDto.getId());
		actDto.setStatus(-1);
		organizationBusiness.suspendAgents(actDto);
	}

	@Override
	public int renewAgents(ArrayList<String> usersIds) throws BaseException {
		logger.debug("Start OrganizationServiceImpl.renewAgents");

		int finishedUsers = 0;
		try {

			logger.debug("Loop on the list of agents to approve them");
			for (int i = 0; i < usersIds.size(); i++) {

				logger.debug("Get user by id: " + Long.valueOf(usersIds.get(i)));
				UserDto userDto = userBusiness.getUserById(Long.valueOf(usersIds.get(i)));
				logger.debug("User retrieved successfully from the DB");

				OrganizationDto org = userDto.getOrganization();


				SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
//
				Date date = new Date();
				try {
					date = formatter.parse(org.getEndDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar nowEnd = Calendar.getInstance();
				nowEnd.setTime(date);
				nowEnd.add(Calendar.MONTH, Integer.valueOf(org.getNumberOfMonths()));
				userDto.getOrganization().setEndDate(nowEnd.getTime().toString());

				String DATE_FORMAT = "E MMM dd HH:mm:ss zzz yyyy";
				ZoneId zoneId = ZoneId.of(org.getTimeZone());

				LocalDateTime end = LocalDateTime.parse(org.getEndDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
				ZonedDateTime customeZoneEnd = end.atZone(zoneId);
				DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);

				org.setEndDate(format.format(customeZoneEnd));

				organizationBusiness.saveOrganization(org);

				OrganizationsExpiryDto organizationsExpiryDto = new OrganizationsExpiryDto();
				organizationsExpiryDto.setOrganizationId(org.getId());
				organizationsExpiryDto.setUsername(userDto.getUserName());
				organizationsExpiryDto.setEmail(userDto.getEmail());
				organizationsExpiryDto.setActive(true);
				organizationsExpiryDto.setEndDate(org.getEndDate());

				organizationBusiness.saveOrganizationExpiry(organizationsExpiryDto);

			}
		} catch (BaseException e) {
			logger.error(e.getMessage());
			throw e;
		}
		logger.debug("End OrganizationServiceImpl.renewAgents");
		return finishedUsers;

	}

}
