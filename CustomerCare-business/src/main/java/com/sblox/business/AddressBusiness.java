package com.sblox.business;

import java.util.ArrayList;

import com.sblox.common.dto.AddressDto;
import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.exception.BaseException;

public interface AddressBusiness {

//    public long count();

    public void saveAddress(AddressDto addressDto) throws BaseException;
    
}
