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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import com.sblox.common.util.ErrorCodes;
import com.sblox.service.OrganizationService;
import com.sblox.service.PaymentService;

@RestController
@RequestMapping("/")
public class PaymentController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PaymentService paymentService;

	private Logger logger = Logger.getLogger(PaymentController.class);

	@RequestMapping(path = "/organization/addAddress/{organizationId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrganizationAddress(@RequestBody AddressRequestData addressRequestData,
			@PathVariable("organizationId") Long organizationId) {
		try {
			logger.debug("WebService: Add Address - Start");
			OrganizationDto organizationDto = new OrganizationDto();
			organizationDto.setId(organizationId);
			organizationDto.setAddress(addressRequestData.getAddress());
			organizationDto.setCity(addressRequestData.getCity());
			organizationDto.setCountry(addressRequestData.getCountry());
			organizationDto.setModeOfPayment(addressRequestData.getModeOfPayment());

			logger.debug("Address: " + addressRequestData.getAddress());
			logger.debug("City: " + addressRequestData.getCity());
			logger.debug("Country: " + addressRequestData.getCountry());
			logger.debug("Mode Of Payment: " + addressRequestData.getModeOfPayment());

			organizationService.saveAddress(organizationDto);

			logger.debug("WebService: Add Address - End");
			return ResponseEntity.ok(addressRequestData);
			// return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}
	
	@RequestMapping(path = "/user/addTrialPayment/{orgId}", method = RequestMethod.POST)
	public ResponseEntity<?> addTrialPayment(@RequestBody ManualPaymentRequest manualPaymentRequest,
			@PathVariable("orgId") String orgId) {
		try {
			logger.debug("WebService: Add Trial Payment - Start");
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setNumberOfCoreAgents(manualPaymentRequest.getNumberOfCoreAgents());
			paymentDto.setNumberOfEnterpriseAgents(manualPaymentRequest.getNumberOfEnterpriseAgents());
			paymentDto.setNumberOfOutboundAgents(manualPaymentRequest.getNumberOfOutboundAgents());
			paymentDto.setNumberOfRecorderAgents(manualPaymentRequest.getNumberOfRecorderAgents());
			paymentDto.setNumberOfMonths(manualPaymentRequest.getNumberOfMonths());
			paymentDto.setInterval(manualPaymentRequest.getInterval());
			paymentDto.setStartDate(manualPaymentRequest.getStartDate());
			paymentDto.setEndDate(manualPaymentRequest.getEndDate());
			paymentDto.setProjectName(orgId);
			paymentDto.setSupersCount(manualPaymentRequest.getSupersCount());
			logger.debug("NumberOfCoreAgents: " + manualPaymentRequest.getNumberOfCoreAgents());
			logger.debug("NumberOfEnterpriseAgents: " + manualPaymentRequest.getNumberOfEnterpriseAgents());
			logger.debug("NumberOfOutboundAgents: " + manualPaymentRequest.getNumberOfOutboundAgents());
			logger.debug("NumberOfRecorderAgents: " + manualPaymentRequest.getNumberOfRecorderAgents());
			logger.debug("ProjectName || Organization ID: " + orgId);

			paymentService.addTrialPayment(paymentDto);
			logger.debug("WebService: Add Trial Payment - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/addManualPayment/{orgId}", method = RequestMethod.POST)
	public ResponseEntity<?> addManualPayment(@RequestBody ManualPaymentRequest manualPaymentRequest,
			@PathVariable("orgId") String orgId) {
		try {
			logger.debug("WebService: Add Maual Payment - Start");
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setNumberOfCoreAgents(manualPaymentRequest.getNumberOfCoreAgents());
			paymentDto.setNumberOfEnterpriseAgents(manualPaymentRequest.getNumberOfEnterpriseAgents());
			paymentDto.setNumberOfOutboundAgents(manualPaymentRequest.getNumberOfOutboundAgents());
			paymentDto.setNumberOfRecorderAgents(manualPaymentRequest.getNumberOfRecorderAgents());
			paymentDto.setNumberOfMonths(manualPaymentRequest.getNumberOfMonths());
			paymentDto.setInterval(manualPaymentRequest.getInterval());
			paymentDto.setStartDate(manualPaymentRequest.getStartDate());
			paymentDto.setEndDate(manualPaymentRequest.getEndDate());
			paymentDto.setProjectName(orgId);
			paymentDto.setSupersCount(manualPaymentRequest.getSupersCount());
			logger.debug("NumberOfCoreAgents: " + manualPaymentRequest.getNumberOfCoreAgents());
			logger.debug("NumberOfEnterpriseAgents: " + manualPaymentRequest.getNumberOfEnterpriseAgents());
			logger.debug("NumberOfOutboundAgents: " + manualPaymentRequest.getNumberOfOutboundAgents());
			logger.debug("NumberOfRecorderAgents: " + manualPaymentRequest.getNumberOfRecorderAgents());
			logger.debug("ProjectName || Organization ID: " + orgId);

			paymentService.addManualPayment(paymentDto);
			logger.debug("WebService: Add Maual Payment - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/addCreditPayment/{orgId}", method = RequestMethod.POST)
	public ResponseEntity<?> addCreditPayment(@RequestBody ManualPaymentRequest manualPaymentRequest,
			@PathVariable("orgId") String orgId) {
		try {
			logger.debug("WebService: Add Credit Payment - Start");
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setNumberOfCoreAgents(manualPaymentRequest.getNumberOfCoreAgents());
			paymentDto.setNumberOfEnterpriseAgents(manualPaymentRequest.getNumberOfEnterpriseAgents());
			paymentDto.setNumberOfOutboundAgents(manualPaymentRequest.getNumberOfOutboundAgents());
			paymentDto.setNumberOfRecorderAgents(manualPaymentRequest.getNumberOfRecorderAgents());
			paymentDto.setNumberOfMonths(manualPaymentRequest.getNumberOfMonths());
			paymentDto.setInterval(manualPaymentRequest.getInterval());
			paymentDto.setStartDate(manualPaymentRequest.getStartDate());
			paymentDto.setEndDate(manualPaymentRequest.getEndDate());
			paymentDto.setProjectName(orgId);
			paymentDto.setSupersCount(manualPaymentRequest.getSupersCount());
			logger.debug("NumberOfCoreAgents: " + manualPaymentRequest.getNumberOfCoreAgents());
			logger.debug("NumberOfEnterpriseAgents: " + manualPaymentRequest.getNumberOfEnterpriseAgents());
			logger.debug("NumberOfOutboundAgents: " + manualPaymentRequest.getNumberOfOutboundAgents());
			logger.debug("NumberOfRecorderAgents: " + manualPaymentRequest.getNumberOfRecorderAgents());
			logger.debug("ProjectName || Organization ID: " + orgId);

			paymentService.addCreditPayment(paymentDto);
			logger.debug("WebService: Add Credit Payment - End");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	@RequestMapping(path = "/user/calculateExtraFees/{orgId}", method = RequestMethod.POST)
	public ResponseEntity<?> calculateExtraFees(@RequestBody ManualPaymentRequest manualPaymentRequest,
			@PathVariable("orgId") String orgId) {
		try {
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setNumberOfCoreAgents(manualPaymentRequest.getNumberOfCoreAgents());
			paymentDto.setNumberOfEnterpriseAgents(manualPaymentRequest.getNumberOfEnterpriseAgents());
			paymentDto.setNumberOfOutboundAgents(manualPaymentRequest.getNumberOfOutboundAgents());
			paymentDto.setNumberOfRecorderAgents(manualPaymentRequest.getNumberOfRecorderAgents());
			paymentDto.setNumberOfMonths(manualPaymentRequest.getNumberOfMonths());
			paymentDto.setInterval(manualPaymentRequest.getInterval());
			paymentDto.setProjectName(orgId);
			// Calendar now = Calendar.getInstance();
			// paymentDto.setStartDate(now.getTime().toString());
			// now.add(Calendar.MONTH,
			// Integer.valueOf(manualPaymentRequest.getNumberOfMonths()));
			// paymentDto.setEndDate(now.getTime().toString());
			// paymentDto.setProjectName(orgId);

			String extraFees = paymentService.calculateExtraFees(paymentDto);
			FeesResponse feesResponse = new FeesResponse();
			feesResponse.setFees(extraFees);
			return ResponseEntity.ok(feesResponse);
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
