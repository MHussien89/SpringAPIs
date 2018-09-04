package com.sblox.dataaccess.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sblox.dataaccess.model.Organization;
import java.util.Date;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    public Organization findById(Long id);

    public Organization findByNameIgnoreCase(String name);

    public ArrayList<Organization> findByStatusAndFirstLogin(int status, String firstLogin);

    @Modifying
    @Transactional
    @Query("delete from Organization o where o.id = ?1")
    public void deleteById(Long id);

    @Query("from Organization o where o.expiryDate < ?1 and o.status = ?2")
    public ArrayList<Organization> findPaymentDue(Date dueDate, int status);

}
