package com.sblox.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.OrganizationData;

import java.util.List;

public interface OrganizationService {
	
	public int updateStatus(ArrayList<String> usersIds, boolean status)
			throws BaseException;

	public int approveAgents(ArrayList<String> usersIds, boolean status)
			throws BaseException;
	
	public int renewAgents(ArrayList<String> usersIds)
			throws BaseException;
	
	public int suspendAgents(ArrayList<String> usersIds)
			throws BaseException;

	public OrganizationDto getOrganizationById(long id) throws BaseException;

//	public void deleteOrganization(ArrayList<String> organizationsIds) throws BaseException;

	public void deleteOrganizations(ArrayList<String> organizationIds) throws IOException, BaseException;

	public void updateOrganization(OrganizationData organizationData) throws BaseException;

	public void acceptOrganization(ArrayList<String> usersIds) throws BaseException;
	
	public void suspendOrganization(OrganizationDto organizationDto) throws BaseException;

	public void saveAddress(OrganizationDto organizationDto) throws BaseException;

	public void submitOrder(OrganizationDto organizationDto) throws BaseException;
        
}
