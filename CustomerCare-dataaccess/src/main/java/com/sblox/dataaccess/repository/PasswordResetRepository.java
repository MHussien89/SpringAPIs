//package com.sblox.dataaccess.repository;
//
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import com.sblox.dataaccess.model.PasswordResetTrial;
//
//@Repository
//public interface PasswordResetRepository extends CrudRepository<PasswordResetTrial, Long> {
//
//    public PasswordResetTrial save(PasswordResetTrial passwordResetTrial) throws DataAccessException;
//
//    public PasswordResetTrial findById(Long id);
//
//    public PasswordResetTrial findByUserId(Long id);
//}
