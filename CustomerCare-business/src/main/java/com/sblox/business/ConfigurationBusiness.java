package com.sblox.business;

import com.sblox.common.dto.ConfigurationDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;

public interface ConfigurationBusiness {

    public String getConfigurationValue(String key) throws BaseException;
    
    public void saveConfiguration(ConfigurationDto configurationDto) throws BaseException;
}
