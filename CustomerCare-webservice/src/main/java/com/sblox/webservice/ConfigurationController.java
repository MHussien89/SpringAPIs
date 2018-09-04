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
import com.sblox.common.dto.ConfigurationDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.PaymentDto;
import com.sblox.common.dto.RoleDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AddressRequestData;
import com.sblox.common.model.ConfigurationRequestData;
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
import com.sblox.service.ConfigurationService;
import com.sblox.service.OrganizationService;
import com.sblox.service.PaymentService;

@RestController
@RequestMapping("/")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;


	private Logger logger = Logger.getLogger(ConfigurationController.class);

	@RequestMapping(path = "/admin/rateplan", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrganizationAddress(@RequestBody ConfigurationRequestData configurationRequestData) {
		try {
			ArrayList<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
			ConfigurationDto configurationDto = new ConfigurationDto();
			
			configurationDto.setName("MONTHLY_CORE");
			configurationDto.setValue(String.valueOf(configurationRequestData.getMonthlyCore()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("MONTHLY_ENTERPRISE");
			configurationDto.setValue(String.valueOf(configurationRequestData.getMonthlyEnterprise()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("MONTHLY_OUTBOUND");
			configurationDto.setValue(String.valueOf(configurationRequestData.getMonthlyOutbound()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("MONTHLY_RECORDER");
			configurationDto.setValue(String.valueOf(configurationRequestData.getMonthlyRecorder()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("DAILY_CORE");
			configurationDto.setValue(String.valueOf(configurationRequestData.getDailyCore()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("DAILY_ENTERPRISE");
			configurationDto.setValue(String.valueOf(configurationRequestData.getDailyEnterprise()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("DAILY_OUTBOUND");
			configurationDto.setValue(String.valueOf(configurationRequestData.getDailyOutbound()));
			configurationDtos.add(configurationDto);
			
			configurationDto = new ConfigurationDto();
			configurationDto.setName("DAILY_RECORDER");
			configurationDto.setValue(String.valueOf(configurationRequestData.getDailyRecorder()));
			configurationDtos.add(configurationDto);
			
			configurationService.saveConfigurations(configurationDtos);
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
	
	@RequestMapping(path = "/admin/rateplan", method = RequestMethod.GET)
	public ResponseEntity<?> getPendingRequests() {
		try {
			String monthlyCore = configurationService.getConfigurationValue("MONTHLY_CORE");
			String monthlyEnterprise = configurationService.getConfigurationValue("MONTHLY_ENTERPRISE");
			String monthlyOutbound = configurationService.getConfigurationValue("MONTHLY_OUTBOUND");
			String monthlyRecorder = configurationService.getConfigurationValue("MONTHLY_RECORDER");
			String dailyCore = configurationService.getConfigurationValue("DAILY_CORE");
			String dailyEnterprise = configurationService.getConfigurationValue("DAILY_ENTERPRISE");
			String dailyOutbound = configurationService.getConfigurationValue("DAILY_OUTBOUND");
			String dailyRecorder = configurationService.getConfigurationValue("DAILY_RECORDER");
			
			RatePlanResponse ratePlanResponse = new RatePlanResponse();
			ratePlanResponse.setMonthlyCore(Double.valueOf(monthlyCore));
			ratePlanResponse.setMonthlyEnterprise(Double.valueOf(monthlyEnterprise));
			ratePlanResponse.setMonthlyOutbound(Double.valueOf(monthlyOutbound));
			ratePlanResponse.setMonthlyRecorder(Double.valueOf(monthlyRecorder));
			ratePlanResponse.setDailyCore(Double.valueOf(dailyCore));
			ratePlanResponse.setDailyEnterprise(Double.valueOf(dailyEnterprise));
			ratePlanResponse.setDailyOutbound(Double.valueOf(dailyOutbound));
			ratePlanResponse.setDailyRecorder(Double.valueOf(dailyRecorder));

			return ResponseEntity.ok(ratePlanResponse);
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return createFailedResponse(e);
		}
	}

	// @RequestMapping(path = "/admin/addManualPayment/{orgId}", method =
	// RequestMethod.POST)
	// public ResponseEntity<?> addManualPayment(@RequestBody ManualPaymentRequest
	// manualPaymentRequest,
	// @PathVariable("orgId") String orgId) {
	// try {
	// PaymentDto paymentDto = new PaymentDto();
	// paymentDto.setNumberOfCoreAgents(manualPaymentRequest.getNumberOfCoreAgents());
	// paymentDto.setNumberOfEnterpriseAgents(manualPaymentRequest.getNumberOfEnterpriseAgents());
	// paymentDto.setNumberOfOutboundAgents(manualPaymentRequest.getNumberOfOutboundAgents());
	// paymentDto.setNumberOfRecorderAgents(manualPaymentRequest.getNumberOfRecorderAgents());
	// paymentDto.setNumberOfMonths(manualPaymentRequest.getNumberOfMonths());
	//
	// paymentDto.setProjectName(orgId);
	//// Calendar now = Calendar.getInstance();
	//// paymentDto.setStartDate(now.getTime().toString());
	//// now.add(Calendar.MONTH,
	// Integer.valueOf(manualPaymentRequest.getNumberOfMonths()));
	// // paymentDto.setEndDate(now.getTime().toString());
	// // paymentDto.setProjectName(orgId);
	//
	// paymentService.addManualPayment(paymentDto);
	// return new ResponseEntity<>(HttpStatus.OK);
	// } catch (BaseException e) {
	// logger.error(e.getMessage(), e);
	// return createFailedResponse(e);
	// } catch (Throwable e) {
	// logger.error(e.getMessage(), e);
	// return createFailedResponse(e);
	// }
	// }

	private ResponseEntity<Response> createFailedResponse(Throwable e) {
		if (e instanceof BaseException) {
			return new ResponseEntity<Response>(new Response(new ErrorResponse((BaseException) e)),
					HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Response>(new Response(new ErrorResponse(ErrorCodes.GENERAL_ERROR, e)),
				HttpStatus.BAD_REQUEST);
	}
}
