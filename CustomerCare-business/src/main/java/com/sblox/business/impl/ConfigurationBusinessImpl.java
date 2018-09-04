package com.sblox.business.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sblox.business.ConfigurationBusiness;
import com.sblox.common.dto.ConfigurationDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.dataaccess.model.Configuration;
import com.sblox.dataaccess.model.User;
import com.sblox.dataaccess.repository.ConfigurationRepository;
import com.sblox.common.util.ErrorCodes;

@Service("configurationBusiness")
public class ConfigurationBusinessImpl implements ConfigurationBusiness, ErrorCodes {
	
	@Autowired
	private Mapper mapper;
	
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public String getConfigurationValue(String name) throws BaseException {
	Configuration config = configurationRepository.findByName(name);
	if (config != null) {
	    return config.getValue();
	}
	throw new BaseException(GENERAL_ERROR);
    }

	@Override
	public void saveConfiguration(ConfigurationDto configurationDto) throws BaseException {
		try {
			Configuration configuration = mapper.map(configurationDto, Configuration.class);
			configurationRepository.save(configuration);
		} catch (DataAccessException ex) {
			throw new BaseException();
		}
	}
}
