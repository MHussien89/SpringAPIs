package com.sblox.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sblox.dataaccess.model.TimeZone;

@Repository
public interface TimeZoneRepository extends CrudRepository<TimeZone, String> {

    @Override
    public List<TimeZone> findAll();

}
