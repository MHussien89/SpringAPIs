package com.sblox.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sblox.business.LookupBusiness;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.PaymentDto;
import com.sblox.common.dto.RoleDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AddressRequestData;
import com.sblox.common.model.AuthenticationResponse;
import com.sblox.common.model.ErrorResponse;
import com.sblox.common.model.ManualPaymentRequest;
import com.sblox.common.model.NewUserRequest;
import com.sblox.common.model.OrganizationData;
import com.sblox.common.model.RegisterData;
import com.sblox.common.model.SuperAdminData;
import com.sblox.common.model.UserCredentials;
import com.sblox.common.model.UserRequest;
import com.sblox.common.model.UsersList;
import com.sblox.service.UserService;
//import com.sblox.utils.JwtHelper;
import com.sblox.utils.JwtHelper;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import com.sblox.common.util.ErrorCodes;
import com.sblox.service.OrganizationService;
import com.sblox.service.PaymentService;

@RestController
@RequestMapping("/")
public class AuthenticationController {

	@Autowired
	private UserService userService;

	private Logger logger = Logger.getLogger(AuthenticationController.class);

	@RequestMapping(path = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {
		try {
			logger.debug("WebService: Login - Start");
			logger.debug("Email: " + userCredentials.getUsername());
			
			AuthenticationResponse authenticationResponse = userService.authenticateUser(userCredentials);
			String token = JwtHelper.createJWT(String.valueOf(authenticationResponse.getUserData().getOrganizationId()), "admin", authenticationResponse.getUserData().getEmail(), 180000);
			authenticationResponse.setAccessToken(token);
					
			return ResponseEntity.ok(new Response(authenticationResponse));
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}


	@RequestMapping(path = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody RegisterData registerData) {
		try {
			logger.debug("WebService: Register - Start");
			UserDto userDto = new UserDto();
			OrganizationDto organizationDto = new OrganizationDto();

			userDto.setFirstName(registerData.getFirstName());
			userDto.setLastName(registerData.getLastName());
			userDto.setUserName(registerData.getUserName());
			userDto.setEmail(registerData.getEmail());
			userDto.setPhone(registerData.getPhone());
			userDto.setPassword(registerData.getPassword());
			userDto.setTimeZone(registerData.getTimeZone());

			organizationDto.setAccepted(false);
			organizationDto.setName(registerData.getOrganizationName());
			organizationDto.setTimeZone(registerData.getTimeZone());

			RoleDto roleDto = new RoleDto();
			roleDto.setId(2l);
			userDto.setRole(roleDto);
			userDto.setPrimaryUser("1");
			userDto.setFirstLogin("1");
			userDto.setTimeZone(organizationDto.getTimeZone());
			organizationDto.setFirstLogin("1");
			organizationDto.setActive(false);

			logger.debug("Name: " + registerData.getFirstName() + " " + registerData.getLastName());
			logger.debug("Email: " + registerData.getEmail());
			logger.debug("Phone: " + registerData.getPhone());
			logger.debug("Timezone: " + registerData.getTimeZone());
			logger.debug("Organization Name: " + registerData.getOrganizationName());

			userService.addUser(userDto, organizationDto);
			logger.debug("WebService: Register - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	private ResponseEntity<Response> createFailedResponse(Throwable e) {
		if (e instanceof BaseException) {
			return new ResponseEntity<Response>(new Response(new ErrorResponse((BaseException) e)),
					HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Response>(new Response(new ErrorResponse(ErrorCodes.GENERAL_ERROR, "Unknown Error...Please contact system administrator")),
				HttpStatus.BAD_REQUEST);
	}
}
