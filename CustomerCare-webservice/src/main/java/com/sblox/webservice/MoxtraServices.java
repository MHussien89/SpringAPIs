package com.sblox.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.List;

import org.apache.log4j.Logger;
import com.sblox.common.util.ErrorCodes;
import com.sblox.service.OrganizationService;
import com.sblox.service.PaymentService;

@RestController
@RequestMapping("/")
public class MoxtraServices {

	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private LookupBusiness lookupBusiness;

	private Logger logger = Logger.getLogger(MoxtraServices.class);

	@RequestMapping(path = "/organization/update/{organizationId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrganization(@RequestBody OrganizationData organizationData,
			@PathVariable("organizationId") Long organizationId) {
		try {
			organizationData.setd(organizationId);

			organizationService.updateOrganization(organizationData);
			return ResponseEntity.ok(organizationData);
			// return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/update/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest, @PathVariable("userId") Long userId) {
		try {
			UserDto userDto = new UserDto();
			userDto.setId(userId);
			userDto.setFirstName(userRequest.getFirstName());
			userDto.setLastName(userRequest.getLastName());
			userDto.setEmail(userRequest.getEmail());
			userDto.setActive(userRequest.isActive());
			userService.updateUser(userDto);
			return ResponseEntity.ok(userDto);
			// return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/addUser/{mainAdminId}", method = RequestMethod.POST)
	public ResponseEntity<?> addAdminUser(@RequestBody SuperAdminData superAdminData,
			@PathVariable("mainAdminId") Long mainAdminId) {
		try {
			logger.debug("WebService: Add Admin - Start");
			UserDto userDto = new UserDto();
			userDto.setFirstName(superAdminData.getFirstName());
			userDto.setLastName(superAdminData.getLastName());
			userDto.setEmail(superAdminData.getEmail());
			userDto.setPassword(superAdminData.getPassword());

			logger.debug("Name: " + superAdminData.getFirstName() + " " + superAdminData.getLastName());
			logger.debug("Email: " + superAdminData.getEmail());

			userService.addAdmin(mainAdminId, userDto);
			logger.debug("WebService: Add Admin - End");
			return ResponseEntity.ok(superAdminData);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/addUser/{orgId}", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@RequestBody NewUserRequest newUserRequest, @PathVariable("orgId") Long orgId) {
		try {
			UserDto userDto = new UserDto();
			// userDto.setId(userId);
			userDto.setFirstName(newUserRequest.getFirstName());
			userDto.setLastName(newUserRequest.getLastName());
			userDto.setEmail(newUserRequest.getEmail());
			userDto.setActive(newUserRequest.isActive());
			userDto.setPassword(newUserRequest.getPassword());

			RoleDto roleDto = new RoleDto();
			roleDto.setId(3l);
			userDto.setRole(roleDto);
			userService.addSingleUser(userDto, orgId);
			return ResponseEntity.ok(newUserRequest);
			// return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/approve", method = RequestMethod.POST)
	public ResponseEntity<?> approveUsers(@RequestBody UsersList usersList) {
		try {
			logger.debug("WebService: Approve users - Start");
			logger.debug("Number of users to approve is: " + usersList.getUsersIds().size());
			organizationService.approveAgents(usersList.getUsersIds(), true);
			logger.debug("WebService: Approve users - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/renew", method = RequestMethod.POST)
	public ResponseEntity<?> renewUsers(@RequestBody UsersList usersList) {
		try {
			logger.debug("WebService: Renew users - Start");
			logger.debug("Number of users to renew is: " + usersList.getUsersIds().size());
			organizationService.renewAgents(usersList.getUsersIds());
			logger.debug("WebService: Renew users - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/suspend", method = RequestMethod.POST)
	public ResponseEntity<?> suspendUsers(@RequestBody UsersList usersList) {
		try {
			logger.debug("WebService: Suspend users - Start");
			logger.debug("Number of users to suspend is: " + usersList.getUsersIds().size());
			organizationService.suspendAgents(usersList.getUsersIds());
			logger.debug("WebService: Suspend users - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/organization/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrganization(@RequestBody UsersList usersList) {
		try {

			organizationService.deleteOrganizations(usersList.getUsersIds());
			return new ResponseEntity<>(HttpStatus.OK);
			// return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/accept", method = RequestMethod.POST)
	public ResponseEntity<?> acceptUsers(@RequestBody UsersList usersList) {
		try {
			organizationService.acceptOrganization(usersList.getUsersIds());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/reject", method = RequestMethod.POST)
	public ResponseEntity<?> rejectUsers(@RequestBody UsersList usersList) {
		try {
			organizationService.updateStatus(usersList.getUsersIds(), false);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/users/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUsers(@RequestBody UsersList usersList) {
		try {
			userService.deleteUsers(usersList.getUsersIds());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/data", method = RequestMethod.GET)
	public ResponseEntity<?> getUserData() {
		try {
			// Long.valueOf((String)
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			AuthenticationResponse authenticationResponse = userService.getUserData(
					Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
			return ResponseEntity.ok(new Response(authenticationResponse));
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/pendingRequests", method = RequestMethod.GET)
	public ResponseEntity<?> getPendingRequests() {
		try {
			List<UserDto> users = userService.getPendingUsers();
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/newRequests", method = RequestMethod.GET)
	public ResponseEntity<?> getNewRequests() {
		try {
			List<UserDto> users = userService.getNewUsers();
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/timezone", method = RequestMethod.GET)
	public ResponseEntity<?> getTimezone() {
		try {
			return ResponseEntity.ok(lookupBusiness.getAllTimeZone());
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/adminList", method = RequestMethod.GET)
	public ResponseEntity<?> getAdminList() {
		try {
			List<UserDto> users = userService.getSuperAdmins();
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/approvedRequests", method = RequestMethod.GET)
	public ResponseEntity<?> getApprovedRequests() {
		try {
			List<UserDto> users = userService.getApprovedUsers();
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/rejectedRequests", method = RequestMethod.GET)
	public ResponseEntity<?> getRejectedRequests() {
		try {
			List<UserDto> users = userService.getRejectedUsers();
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/admin/usersList/{organizationId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUsersList(@PathVariable("organizationId") Long organizationId) {
		try {
			List<UserDto> users = userService.getUsersOrganizationId(organizationId);
			UserResponse userResponse = new UserResponse(users);
			return ResponseEntity.ok(userResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/userData/{organizationId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserData(@PathVariable("organizationId") Long organizationId) {
		try {
			logger.debug("WebService: getUserData - Start");
			logger.debug("Organization Id: " + organizationId);
			return ResponseEntity.ok(new Response(userService.getUserData(organizationId)));
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
		return new ResponseEntity<Response>(new Response(
				new ErrorResponse(ErrorCodes.GENERAL_ERROR, "Unknown Error...Please contact system administrator")),
				HttpStatus.BAD_REQUEST);
	}
}
