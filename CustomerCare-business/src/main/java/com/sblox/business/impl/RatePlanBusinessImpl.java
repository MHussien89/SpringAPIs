//package com.sblox.business.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.dozer.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.sblox.business.ConfigurationBusiness;
//import com.sblox.business.RatePlanBusiness;
//import com.sblox.common.dto.OrganizationDto;
//import com.sblox.common.dto.RatePlanDto;
//import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Configuration;
//import com.sblox.dataaccess.model.Organization;
//import com.sblox.dataaccess.model.RatePlan;
//import com.sblox.dataaccess.repository.ConfigurationRepository;
//import com.sblox.dataaccess.repository.RatePlanRepository;
//import com.sblox.common.util.ErrorCodes;
//
//@Service("ratePlanBusiness")
//public class RatePlanBusinessImpl implements RatePlanBusiness, ErrorCodes {
//	@Autowired
//	private RatePlanRepository ratePlanRepository;
//	
//	@Autowired
//	private Mapper mapper;
//
//	@Override
//	public ArrayList<RatePlanDto> getAllRatePlans() throws BaseException {
//		ArrayList<RatePlan> ratePlans = ratePlanRepository.findAll();
//		ArrayList<RatePlanDto> ratePlanDtos = new ArrayList<RatePlanDto>();
//		for (int i = 0; i < ratePlans.size(); i++) {
//			RatePlanDto ratePlanDto = mapper.map(ratePlans.get(i), RatePlanDto.class);
//			ratePlanDtos.add(ratePlanDto);
//		}
//		return ratePlanDtos;
//	}
//}
