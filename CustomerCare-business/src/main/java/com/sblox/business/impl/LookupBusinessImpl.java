package com.sblox.business.impl;

import com.sblox.business.LookupBusiness;
import com.sblox.common.dto.TimeZoneDto;
import com.sblox.dataaccess.repository.TimeZoneRepository;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mostafa Hussien
 */
@Service("lookupBusiness")
public class LookupBusinessImpl implements LookupBusiness {

    @Autowired
    private Mapper mapper;

    @Autowired
    private TimeZoneRepository timeZoneRepository;

    @Override
    public List<TimeZoneDto> getAllTimeZone() {
        List<TimeZoneDto> timeZone = new ArrayList<>();
        timeZoneRepository.findAll().forEach(zone -> {
            timeZone.add(mapper.map(zone, TimeZoneDto.class));
        });
        return timeZone;
    }

}
