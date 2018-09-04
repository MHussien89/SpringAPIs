package com.sblox.business.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mail.sblox.dataaccess.model.OrganizationsExpiry;
import com.mail.sblox.dataaccess.repository.MailRepository;
import com.mail.sblox.dataaccess.repository.OrganizationsExpiryRepository;
import com.netlogic.dataaccess.model.Act;
import com.netlogic.dataaccess.repository.ActRepository;
import com.sblox.business.OrganizationBusiness;
import com.sblox.common.dto.ActDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.OrganizationStatus;
import com.sblox.common.dto.OrganizationsExpiryDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.dataaccess.model.Organization;
import com.sblox.dataaccess.model.User;
import com.sblox.dataaccess.repository.OrganizationRepository;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service("organizationBusiness")
public class OrganizationBusinessImpl implements OrganizationBusiness {

	@Autowired
	private Mapper mapper;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private ActRepository actRepository;

	@Autowired
	private OrganizationsExpiryRepository organizationsExpiryRepository;

	// @Override
	// public long count() {
	// return usersRepository.count();
	// }
	// @Override
	// public void saveUser(UserDto userDto) {
	// User user = mapper.map(userDto, User.class);
	// usersRepository.save(user);
	// }
	// @Override
	// public UserDto getUserByEmail(String email) {
	// User user = usersRepository.findByEmailIgnoreCase(email);
	// return mapper.map(user, UserDto.class);
	// }
	//
	// @Override
	// public String getHashedPassword(String email) {
	// User user = usersRepository.findByEmailIgnoreCase(email);
	// return user.getPassword();
	// }
	@Override
	public OrganizationDto saveOrganization(OrganizationDto organizationDto) throws BaseException {
		// TODO Auto-generated method stub
		try {
			Organization organization = mapper.map(organizationDto, Organization.class,
					"OrganizationDtoVsOrganization");
			organization = organizationRepository.save(organization);

			organizationDto = mapper.map(organization, OrganizationDto.class);
			return organizationDto;
		} catch (DataAccessException ex) {
			throw new BaseException(ex.getMessage());
		}

	}

	@Override
	public OrganizationDto getOrganizationByName(String name) {
		Organization organization = organizationRepository.findByNameIgnoreCase(name);

		if (organization != null) {
			return mapper.map(organization, OrganizationDto.class);
		} else {
			return null;
		}
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	// @Override
	// public ArrayList<OrganizationDto> getPendingOrganizations() throws
	// BaseException {
	// try {
	// ArrayList<Organization> organizations =
	// organizationRepository.findByStatusAndFirstLogin("false", "1");
	// ArrayList<OrganizationDto> organizationDtos = new
	// ArrayList<OrganizationDto>();
	// for (int i = 0; i < organizations.size(); i++) {
	// OrganizationDto organizationDto = mapper.map(organizations.get(i),
	// OrganizationDto.class, "OrganizationDtoVsOrganization");
	// organizationDtos.add(organizationDto);
	// }
	// return organizationDtos;
	// } catch (DataAccessException ex) {
	// throw new BaseException();
	// }
	// }
	@Override
	public OrganizationDto getOrganizationById(long id) throws BaseException {
		Organization organization = organizationRepository.findById(id);
		if (organization != null) {
			return mapper.map(organization, OrganizationDto.class, "OrganizationDtoVsOrganization");
		} else {
			return null;
		}
	}

	@Override
	public ActDto getNetLogicOrganizationById(long id) throws BaseException {
		Act act = actRepository.findById(id);
		if (act != null) {
			return mapper.map(act, ActDto.class, "ActDtoVsAct");
		} else {
			return null;
		}
	}

	@Override
	public void deleteOrganization(long id) throws BaseException {
		organizationRepository.deleteById(id);

	}

	@Override
	public List<OrganizationDto> getPaymentDueOrganizations() throws BaseException {
		List<OrganizationDto> organizationDtos = new LinkedList<OrganizationDto>();
		organizationRepository.findPaymentDue(new Date(), OrganizationStatus.ACTIVE.getId()).forEach(organization -> {
			organizationDtos.add(mapper.map(organization, OrganizationDto.class, "OrganizationDtoVsOrganization"));
		});
		return organizationDtos;
	}

	@Override
	public void approveAgents(ActDto actDto) throws BaseException {
		try {
			Act act = mapper.map(actDto, Act.class);
			actRepository.save(act);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public void suspendAgents(ActDto actDto) throws BaseException {
		try {
			Act act = mapper.map(actDto, Act.class);
			actRepository.save(act);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public OrganizationsExpiryDto saveOrganizationExpiry(OrganizationsExpiryDto organizationsExpiryDto)
			throws BaseException {
		try {
			OrganizationsExpiry organizationExpiry = mapper.map(organizationsExpiryDto, OrganizationsExpiry.class,
					"OrganizationsExpiryVsOrganizationsExpiryDto");
			organizationExpiry = organizationsExpiryRepository.save(organizationExpiry);

			organizationsExpiryDto = mapper.map(organizationExpiry, OrganizationsExpiryDto.class);
			return organizationsExpiryDto;
		} catch (DataAccessException ex) {
			throw new BaseException(ex.getMessage());
		}
	}
}
