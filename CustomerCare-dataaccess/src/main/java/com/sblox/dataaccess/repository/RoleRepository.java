package com.sblox.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sblox.dataaccess.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role findById(Long id);

	public Role findByName(String name);

	public List<Role> findAll();

}
