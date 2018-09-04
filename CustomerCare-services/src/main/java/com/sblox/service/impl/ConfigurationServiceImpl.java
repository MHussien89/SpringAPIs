package com.sblox.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sblox.business.ConfigurationBusiness;
import com.sblox.common.dto.ConfigurationDto;
import com.sblox.common.exception.BaseException;
import com.sblox.service.ConfigurationService;

@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationBusiness configurationBusiness;

    @Override
    public String getConfigurationValue(String name) throws BaseException {
	return configurationBusiness.getConfigurationValue(name);
    }

	@Override
	public void saveConfigurations(ArrayList<ConfigurationDto> configurationDtos) throws BaseException {
		for(int i =0 ; i < configurationDtos.size() ; i++) {
			configurationBusiness.saveConfiguration(configurationDtos.get(i));
		}
	}

}
