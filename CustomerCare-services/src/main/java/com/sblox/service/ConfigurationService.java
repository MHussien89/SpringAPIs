package com.sblox.service;

import java.util.ArrayList;

import com.sblox.common.dto.ConfigurationDto;
import com.sblox.common.exception.BaseException;

public interface ConfigurationService {

    public String getConfigurationValue(String name) throws BaseException;
    
    public void saveConfigurations(ArrayList<ConfigurationDto> configurationDtos) throws BaseException;

}
