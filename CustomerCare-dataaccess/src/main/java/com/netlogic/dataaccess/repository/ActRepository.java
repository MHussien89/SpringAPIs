package com.netlogic.dataaccess.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.netlogic.dataaccess.model.Act;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Mail;
import com.sblox.dataaccess.model.Organization;
import com.sblox.dataaccess.model.User;

@Repository
public interface ActRepository extends CrudRepository<Act, Long> {

	public long count();

	public Act save(Act act) throws DataAccessException;;

	public Act findById(Long id);

	@Modifying
	@Transactional
	@Query("delete from Act a where a.id = ?1")
	public void deleteById(Long id);

	// public List<Act> findBySuccess(String success);

}
