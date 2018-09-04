package com.sblox.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.sblox.common.dto.MailDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AuthenticationResponse;
import com.sblox.common.model.UserCredentials;
import com.sblox.common.util.Defines;
import com.sblox.service.UserService;
import com.sblox.common.util.ErrorCodes;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Service("userService")
public class UserServiceImpl implements UserService, ErrorCodes, Defines {

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private MailBusiness mailBusiness;

	@Autowired
	private ConfigurationBusiness configurationBusiness;

	@Autowired
	private OrganizationBusiness organizationBusiness;

	@Autowired
	private PaymentBusiness paymentBusiness;

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	// Objects required for encryption/decryption
	// private SecretKey secretKey;
	// private Base64.Encoder encoder;
	// private Base64.Decoder decoder;

	private String encrypt(String plainText) {
		try {
			byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes());
			return new String(encodedBytes);

		} catch (Exception e) {
			logger.error("Failed to encrypt", e);
		}

		return null;
	}

	// private String encrypt(String plainText) {
	// try {
	//
	// String key = "92AE31A79FEEB2A3"; //llave
	// try {
	// this.secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// this.encoder = Base64.getUrlEncoder();
	// this.decoder = Base64.getUrlDecoder();
	//
	// // Get byte array which has to be encrypted.
	// byte[] plainTextByte = toByteArray(plainText);
	//
	// // Encrypt the bytes using the secret key
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	// byte[] encryptedByte = cipher.doFinal(plainTextByte);
	//
	// // Use Base64 encoder to encode the byte array
	// // into Base 64 representation. Requires Java 8.
	// return encoder.encodeToString(encryptedByte);
	//
	// } catch (Exception e) {
	// logger.error("Failed to encrypt", e);
	// }
	//
	// return null;
	// }

	private byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

	@Transactional(rollbackFor = BaseException.class)
	@Override
	public void addUser(UserDto userDto, OrganizationDto organizationDto) throws BaseException {
		logger.debug("Start UserServiceImpl.addUser()");

		logger.debug("Start hashing the pasword for Netlogic");
		userDto.setHashedPassword(encrypt(userDto.getPassword()));
		logger.debug("Netlogic hashing done successfully");

		logger.debug("Start encrypt the pasword");
		userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		logger.debug("Encryption done successfully");

		userDto.setActive(organizationDto.isActive());
		userDto.setOrganization(organizationDto);

		logger.debug("Get user by email: " + userDto.getEmail());
		UserDto existingUserDto = userBusiness.getUserByEmail(userDto.getEmail());

		logger.debug("Get organization by name: " + organizationDto.getName());

		OrganizationDto existingOrganizationDto = organizationBusiness.getOrganizationByName(organizationDto.getName());
		if (existingUserDto != null) {
			logger.error("Email already exist in the DB");
			throw new BaseException("1", "The Email is already used before");
		}

		if (existingOrganizationDto != null) {
			logger.error("Organization name already exist in the DB");
			throw new BaseException("1", "The Organization name is already used before");
		}

		logger.debug("Email and Organization name not exist before in the DB!!!!");

		organizationDto.getUsers().add(userDto);
		OrganizationDto createdOrganizationDto = organizationBusiness.saveOrganization(organizationDto);
		UserDto createdUser = createdOrganizationDto.getUsers().get(0);

		logger.debug("Fill the MailDTO to send an email to the admin");
		MailDto mailDto = new MailDto();
		mailDto.setUserId(createdUser.getId());
		mailDto.setToken(createdUser.getSecurityToken());
		mailDto.setUserName(createdUser.getFirstName() + " " + createdUser.getLastName());
		mailDto.setMailSubject("Getting Started with S-Blox");
		mailDto.setTemplateName("superAdminTemplate.vm");
		String superAdminMail = configurationBusiness.getConfigurationValue(ADMIN_MAIL);
		mailDto.setMailTo(superAdminMail);
		mailDto.setSuccess("-1");

		logger.debug("TemplateName: superAdminTemplate.vm");
		logger.debug("Admin Email: " + superAdminMail);
		logger.debug("Username: " + mailDto.getUserName());
		logger.debug("MailSubject: Getting Started with S-Blox");

		mailBusiness.saveMail(mailDto);
		logger.debug("MailDto saved successfully in the DB");

		logger.debug("End UserServiceImpl.addUser()");
	}

	@Override
	public UserDto getUserByUsername(String username) throws BaseException {
		return userBusiness.getUserByEmail(username);
	}

	@Override
	public ArrayList<UserDto> getPendingUsers() throws BaseException {
		logger.debug("Start UserServiceImpl.getRejectedUsers()");
		ArrayList<UserDto> pendingUsers = userBusiness.getPendingUsers();
		ArrayList<UserDto> updatedUsers = userBusiness.getUpdatedUsers();

		List<UserDto> result = updatedUsers.stream().map(temp -> {
			temp.setUpdated(true);
			temp.getOrganization().setTotalCost(
					Double.valueOf(new DecimalFormat("##.##").format(temp.getOrganization().getTotalCost())));
			temp.getOrganization()
					.setToPay(Double.valueOf(new DecimalFormat("##.##").format(temp.getOrganization().getToPay())));
			// new DecimalFormat("##.##").format(toPay)
			return temp;
		}).collect(Collectors.toList());

		// System.out.println(result);
		//
		// updatedUsers.stream().map(user ->
		// user.setUpdated(true)).collect(Collectors.toList());

		pendingUsers.addAll(result);
		return pendingUsers;
	}

	@Override
	public ArrayList<UserDto> getApprovedUsers() throws BaseException {
		logger.debug("Start UserServiceImpl.getApprovedUsers()");

		ArrayList<UserDto> userDtos = userBusiness.getApprovedUsers();
		ArrayList<UserDto> result = (ArrayList) userDtos.stream().map(temp -> {
			if (temp.getOrganization().getEndDate() != null) {
				Date date1 = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
				try {
					date1 = formatter.parse(temp.getOrganization().getEndDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar now = Calendar.getInstance();

				int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

				if (diffInDays <= 5) {
					temp.getOrganization().setPaymentStatus("Danger");
					//
				} else {
					temp.getOrganization().setPaymentStatus("Success");
				}
			}

			// temp.setUpdated(true);
			// temp.getOrganization().setTotalCost(
			// Double.valueOf(new
			// DecimalFormat("##.##").format(temp.getOrganization().getTotalCost())));
			// temp.getOrganization()
			// .setToPay(Double.valueOf(new
			// DecimalFormat("##.##").format(temp.getOrganization().getToPay())));
			// new DecimalFormat("##.##").format(toPay)
			return temp;
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public ArrayList<UserDto> getRejectedUsers() throws BaseException {
		logger.debug("Start UserServiceImpl.getRejectedUsers()");
		return userBusiness.getRejectedUsers();
	}

	@Override
	public ArrayList<UserDto> getNewUsers() throws BaseException {
		logger.debug("Start UserServiceImpl.getRejectedUsers()");
		return userBusiness.getNewUsers();
	}

	@Override
	public AuthenticationResponse authenticateUser(UserCredentials userCredentials) throws BaseException {
		logger.debug("Start UserServiceImpl.authenticateUser()");

		logger.debug("Get the hashed password from the DB");
		String dbPassword = userBusiness.getHashedPassword(userCredentials.getUsername());

		if (dbPassword == null) {
			logger.error("This email not exist in the DB");
			throw new BaseException(USER_NOT_EXIST, "User doesn't exist");
		}
		logger.debug("Hashed password retrieved sucessfully");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		logger.debug("Check on the password");
		if (!passwordEncoder.matches(userCredentials.getPassword(), dbPassword)) {
			logger.error("Wrong password");
			throw new BaseException(USER_PASSWORD_INCORRECT, "Username and password don't match");
		}
		logger.debug("Password is correct");

		logger.debug("Check if the organization is active or not");
		logger.debug("Get user by email");
		// Check user's organization is active
		UserDto userDto = userBusiness.getUserByEmail(userCredentials.getUsername());
		logger.debug("User object retrieved from the DB baseed on username");

		if (!userDto.getOrganization().isActive() && !userDto.getOrganization().isAccepted()) {
			logger.error("Organization is not active");
			throw new BaseException(ORGANIZATION_INACTIVE, "Your organization is deactivated");
		}
		logger.debug("The organization is active");
		// Check user is active
		if (!userDto.isActive()) {
			logger.error("User is not active");
			throw new BaseException(USER_INACTIVE, "User is deactivated");
		}
		logger.debug("The user is active");
		// if (mobileLogin && userDto.getFirstLogin() != null &&
		// userDto.getFirstLogin().equals("1")) {
		// throw new BaseException(USER_FIRST_LOGIN, "You need to login from website
		// first");
		// }
		logger.debug("Fill the authenticationResponseDTO");

		if (userDto.getOrganization().getEndDate() != null) {
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
			try {
				date1 = formatter.parse(userDto.getOrganization().getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar now = Calendar.getInstance();

			int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

			if (diffInDays <= 5) {
				userDto.getOrganization().setPaymentStatus("Danger");
				//
			} else {
				userDto.getOrganization().setPaymentStatus("Success");
			}

			if (diffInDays < 0) {
				userDto.getOrganization().setRemainingDays(0);
				//
			} else {
				userDto.getOrganization().setRemainingDays(diffInDays);
			}
		}

		AuthenticationResponse response = new AuthenticationResponse(userDto);

		logger.debug("End UserServiceImpl.authenticateUser()");
		return response;
	}

	@Override
	public AuthenticationResponse getUserData(long organizationId) throws BaseException {
		logger.debug("Start UserServiceImpl.getUsersOrganizationId()");
		ArrayList<UserDto> users = userBusiness.getUsersOrganizationId(organizationId);
		UserDto primaryUser = users.stream()
				.filter(userr -> userr.getPrimaryUser() != null && userr.getPrimaryUser().equalsIgnoreCase("1"))
				.collect(Collectors.toList()).get(0);

		if (primaryUser.getOrganization().getEndDate() != null) {
			Date date1 = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
			try {
				date1 = formatter.parse(primaryUser.getOrganization().getEndDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar now = Calendar.getInstance();

			int diffInDays = (int) ((date1.getTime() - now.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1;

			if (diffInDays <= 5) {
				primaryUser.getOrganization().setPaymentStatus("Danger");
				//
			} else {
				primaryUser.getOrganization().setPaymentStatus("Success");
			}

			if (diffInDays < 0) {
				primaryUser.getOrganization().setRemainingDays(0);
				//
			} else {
				primaryUser.getOrganization().setRemainingDays(diffInDays);
			}
		}

		AuthenticationResponse response = new AuthenticationResponse(primaryUser);
		return response;
	}

	@Override
	public ArrayList<UserDto> getUsersOrganizationId(long organizationId) throws BaseException {
		logger.debug("Start UserServiceImpl.getUsersOrganizationId()");
		return userBusiness.getUsersOrganizationId(organizationId);
	}

	@Override
	public UserDto getUserById(long id) throws BaseException {
		return userBusiness.getUserById(id);
	}

	@Override
	public void updateUser(UserDto user) throws BaseException {

		UserDto updatedUser = userBusiness.getUserById(user.getId());

		updatedUser.setFirstName(user.getFirstName());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setActive(user.isActive());

		// if (validateNoOfUsers) {
		List<UserDto> result = updatedUser.getOrganization().getUsers().stream()
				.filter(userr -> userr.isActive() == true).collect(Collectors.toList());

		if (user.isActive() == true) {
			if (updatedUser.getOrganization().getNumberOfActiveUsers() < result.size()) {
				throw new BaseException("",
						"Maximum Number of active users is: " + updatedUser.getOrganization().getNumberOfActiveUsers()
								+ ", Please contact the system administrator to increase the number of active users");
			}
		}

		userBusiness.saveUser(updatedUser);
	}

	@Override
	public void addAdmin(long mainAdminId, UserDto user) throws BaseException {
		logger.debug("Start UserServiceImpl.addAdmin()");
		logger.debug("Get organization by ID: " + mainAdminId);
		OrganizationDto mainOrganization = organizationBusiness.getOrganizationById(mainAdminId);
		user.setOrganization(mainOrganization);
		user.setActive(true);
		user.setFirstLogin("-1");
		user.setRole(mainOrganization.getUsers().get(0).getRole());

		logger.debug("Get user by email: " + user.getEmail());
		UserDto existingUserDto = userBusiness.getUserByEmail(user.getEmail());
		if (existingUserDto != null) {
			logger.error("The email is used before");
			throw new BaseException("1", "The Email is already used before");
		}
		logger.debug("The email is not used before");
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

		logger.debug("Call saveUser to save the new admin user");
		userBusiness.saveUser(user);
		logger.debug("End UserServiceImpl.addAdmin()");
	}

	@Transactional(rollbackFor = { BaseException.class })
	@Override
	public void updateStatus(ArrayList<UserDto> users, boolean status) throws BaseException {
		try {
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = users.get(i);
				userDto.setActive(status);
				userBusiness.saveUser(userDto);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BaseException(GENERAL_ERROR, e.getMessage(), e);
		}

	}

	@Transactional(rollbackFor = { BaseException.class })
	@Override
	public void deleteUsers(ArrayList<String> users) throws BaseException {
		for (int i = 0; i < users.size(); i++) {
			userBusiness.deleteUserById(Long.valueOf(users.get(i)));
		}
	}

	@Override
	public void deleteUser(long id) throws BaseException {
		userBusiness.deleteUser(id);

	}

	@Override
	public void addSingleUser(UserDto userDto, long orgId) throws BaseException {
		OrganizationDto organizationDto = organizationBusiness.getOrganizationById(orgId);
		UserDto existingUser = userBusiness.getUserByEmail(userDto.getEmail());
		if (existingUser != null) {
			throw new BaseException("1", "The Email is already used before");
		}

		List<UserDto> result = organizationDto.getUsers().stream().filter(userr -> userr.isActive() == true)
				.collect(Collectors.toList());

		if (userDto.isActive() == true) {
			if (organizationDto.getNumberOfActiveUsers() <= result.size()) {
				throw new BaseException(GENERAL_ERROR,
						"Maximum Number of active users is: " + organizationDto.getNumberOfActiveUsers()
								+ ", Please contact the system administrator to increase the number of active users");
			}
		}
		userDto.setOrganization(organizationDto);
		userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		userDto = userBusiness.saveUser(userDto);

	}

	@Override
	public ArrayList<UserDto> getSuperAdmins() throws BaseException {
		logger.debug("Start UserServiceImpl.getSuperAdmins()");
		return userBusiness.getSuperAdmins();
	}

}
