package com.sblox.business;

import com.sblox.common.dto.ActDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.OrganizationsExpiryDto;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Organization;
import java.util.List;

public interface OrganizationBusiness {

	public long count();

	public OrganizationDto saveOrganization(OrganizationDto organizationDto) throws BaseException;

	public OrganizationDto getOrganizationByName(String name) throws BaseException;

	// public ArrayList<OrganizationDto> getPendingOrganizations() throws
	// BaseException;

	public OrganizationDto getOrganizationById(long id) throws BaseException;

	public void deleteOrganization(long id) throws BaseException;

	public List<OrganizationDto> getPaymentDueOrganizations() throws BaseException;

	public void approveAgents(ActDto actDto) throws BaseException;

	public void suspendAgents(ActDto actDto) throws BaseException;

	public ActDto getNetLogicOrganizationById(long id) throws BaseException;

	public OrganizationsExpiryDto saveOrganizationExpiry(OrganizationsExpiryDto organizationsExpiryDto)
			throws BaseException;

}
