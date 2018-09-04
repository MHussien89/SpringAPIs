package com.sblox.dataaccess.repository;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sblox.dataaccess.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	public long count();
	public ArrayList<Payment> findByProjectName(String projectName) throws DataAccessException;
	public Payment save(Payment payment) throws DataAccessException;

}
