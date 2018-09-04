package com.sblox.business.impl;

import java.util.ArrayList;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sblox.business.UserBusiness;
import com.sblox.common.dto.OrganizationStatus;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.PasswordResetTrial;
import com.sblox.dataaccess.model.User;
//import com.sblox.dataaccess.repository.PasswordResetRepository;
import com.sblox.dataaccess.repository.UserRepository;

@Service("userBusiness")
public class UserBusinessImpl implements UserBusiness {

	@Autowired
	private Mapper mapper;

	@Autowired
	private UserRepository usersRepository;

	// @Autowired
	// private PasswordResetRepository passwordResetRepository;

	@Override
	public long count() {
		return usersRepository.count();
	}

	@Override
	public UserDto saveUser(UserDto userDto) throws BaseException {
		try {
			User user = mapper.map(userDto, User.class);
			user = usersRepository.save(user);
			return mapper.map(user, UserDto.class);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public UserDto getUserByEmail(String email) {

		User user = usersRepository.findByEmailIgnoreCase(email);

		if (user != null) {
			return mapper.map(user, UserDto.class);
		} else {
			return null;
		}

	}

	@Override
	public String getHashedPassword(String email) {
		User user = usersRepository.findByEmailIgnoreCase(email);
		if (user != null)
			return user.getPassword();
		return null;
	}

	@Override
	public ArrayList<UserDto> getUsersOrganizationId(long organizationId) throws BaseException {
		try {
			ArrayList<User> users = usersRepository.findByOrganizationId(organizationId);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public UserDto getUserById(long id) throws BaseException {
		User user = usersRepository.findById(id);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public void deleteUserById(long id) throws BaseException {
		try {
			usersRepository.deleteById(id);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}

	}

	@Override
	public ArrayList<UserDto> getPendingUsers() throws BaseException {
		try {
			ArrayList<User> users = usersRepository
					.findByOrganizationStatusAndOrganizationFirstLoginAndRoleIdAndPrimaryUserAndOrganizationAccepted(
							OrganizationStatus.DEACTIVE.getId(), "1", 2, "1", true);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public ArrayList<UserDto> getUpdatedUsers() throws BaseException {
		try {
			ArrayList<User> users = usersRepository.findByOrganizationUpdated(true);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public ArrayList<UserDto> getApprovedUsers() throws BaseException {
		try {
			ArrayList<User> users = usersRepository
					.findByOrganizationStatusAndOrganizationFirstLoginAndRoleIdAndPrimaryUserAndOrganizationAccepted(
							OrganizationStatus.ACTIVE.getId(), "-1", 2, "1", true);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public ArrayList<UserDto> getRejectedUsers() throws BaseException {
		try {
			ArrayList<User> users = usersRepository
					.findByOrganizationStatusAndOrganizationFirstLoginAndRoleIdAndPrimaryUserAndOrganizationAccepted(
							OrganizationStatus.DEACTIVE.getId(), "-1", 2, "1", true);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public ArrayList<UserDto> getNewUsers() throws BaseException {
		try {
			ArrayList<User> users = usersRepository
					.findByOrganizationStatusAndOrganizationFirstLoginAndRoleIdAndPrimaryUserAndOrganizationAccepted(
							OrganizationStatus.DEACTIVE.getId(), "1", 2, "1", false);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	@Override
	public void deleteUser(long id) throws BaseException {
		usersRepository.deleteById(id);

	}

	@Override
	public ArrayList<UserDto> getSuperAdmins() throws BaseException {
		try {
			ArrayList<User> users = usersRepository.findByRoleId(1);
			ArrayList<UserDto> userDtos = new ArrayList<UserDto>();
			for (int i = 0; i < users.size(); i++) {
				UserDto userDto = mapper.map(users.get(i), UserDto.class);
				userDtos.add(userDto);
			}
			return userDtos;
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}

	// public PasswordResetTrial getPasswordResetTrialByUserId(Long id) throws
	// BaseException {
	// return passwordResetRepository.findByUserId(id);
	// }
	//
	// @Override
	// public void savePasswordResetTrial(PasswordResetTrial passwordResetTrial)
	// throws BaseException {
	// try {
	// passwordResetRepository.save(passwordResetTrial);
	// } catch (DataAccessException ex) {
	// throw new BaseException();
	// }
	// }
}
