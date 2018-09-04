package com.sblox.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sblox.dataaccess.model.Configuration;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, String> {
	
	public Configuration findByName(String name);
	
	public List<Configuration> findAll();
	
}
