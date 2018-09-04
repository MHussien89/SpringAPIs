package com.sblox.business;

import java.util.ArrayList;
import java.util.List;

import com.sblox.common.dto.RatePlanDto;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.RatePlan;

public interface RatePlanBusiness {

    public ArrayList<RatePlanDto> getAllRatePlans() throws BaseException;
}
