package com.sblox.dataaccess.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sblox.common.exception.BaseException;
import com.sblox.dataaccess.model.Organization;
import com.sblox.dataaccess.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public long count();

	public User save(User user) throws DataAccessException;;

	public List<User> save(List<User> user);

	public ArrayList<User> findByEmailIgnoreCaseOrOrganizationNameIgnoreCase(String email, String organizationName);
	
	public User findByEmailIgnoreCase(String email);
	
	public User findById(Long id);
	
	@Modifying
	@Transactional
	@Query("delete from User u where u.id = ?1")
	public void deleteById(Long id);

	public List<User> findByActive(boolean active);
	
	public ArrayList<User> findByOrganizationId(long id);
	
	public ArrayList<User> findByOrganizationUpdated(boolean updated);
	
	public ArrayList<User> findByOrganizationStatusAndOrganizationFirstLoginAndRoleIdAndPrimaryUserAndOrganizationAccepted(int status , String firstLogin , long roleId , String primaryUser , boolean accepted);
	
	public ArrayList<User> findByRoleId(long roleId);
	
//	public ArrayList<User> findByPrimaryUser(String primaryUser);
	
	public List<User> findAll();
}
