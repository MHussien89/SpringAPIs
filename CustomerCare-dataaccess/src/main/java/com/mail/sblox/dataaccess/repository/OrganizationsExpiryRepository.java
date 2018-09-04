package com.mail.sblox.dataaccess.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mail.sblox.dataaccess.model.Mail;
import com.mail.sblox.dataaccess.model.OrganizationsExpiry;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.Mail;
import com.sblox.dataaccess.model.Organization;
import com.sblox.dataaccess.model.User;

@Repository
public interface OrganizationsExpiryRepository extends CrudRepository<OrganizationsExpiry, Long> {

	public long count();

	public OrganizationsExpiry save(OrganizationsExpiry mail) throws DataAccessException;;

	public List<OrganizationsExpiry> findByActive(String success);

}
