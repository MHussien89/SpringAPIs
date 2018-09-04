//package com.sblox.business.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.dozer.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.sblox.business.AddressBusiness;
////import com.sblox.business.AddressBusiness;
//import com.sblox.business.OrganizationBusiness;
//import com.sblox.business.UserBusiness;
//import com.sblox.common.dto.AddressDto;
//import com.sblox.common.dto.OrganizationDto;
//import com.sblox.common.dto.UserDto;
//import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Address;
//import com.sblox.dataaccess.model.Organization;
//import com.sblox.dataaccess.model.User;
//import com.sblox.dataaccess.repository.AddressRepository;
//import com.sblox.dataaccess.repository.OrganizationRepository;
//import com.sblox.dataaccess.repository.UserRepository;
//
//@Service("addressBusiness")
//public class AddressBusinessImpl implements AddressBusiness {
//
//	@Autowired
//	private Mapper mapper;
//
//	@Autowired
//	private AddressRepository addressRepository;
//
//	// @Override
//	// public long count() {
//	// return usersRepository.count();
//	// }
//
//	// @Override
//	// public void saveUser(UserDto userDto) {
//	// User user = mapper.map(userDto, User.class);
//	// usersRepository.save(user);
//	// }
//
//	// @Override
//	// public UserDto getUserByEmail(String email) {
//	// User user = usersRepository.findByEmailIgnoreCase(email);
//	// return mapper.map(user, UserDto.class);
//	// }
//	//
//	// @Override
//	// public String getHashedPassword(String email) {
//	// User user = usersRepository.findByEmailIgnoreCase(email);
//	// return user.getPassword();
//	// }
//	@Transactional(rollbackFor = BaseException.class)
//	@Override
//	public void saveAddress(AddressDto addressDto) throws BaseException {
//		// TODO Auto-generated method stub
//		try {
//			Address address = mapper.map(addressDto, Address.class);
//			addressRepository.save(address);
//		} catch (DataAccessException ex) {
//			throw new BaseException(ex.getMessage());
//		}
//
//	}
//
//}
