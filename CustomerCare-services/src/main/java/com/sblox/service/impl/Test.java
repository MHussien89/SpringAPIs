package com.sblox.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Test {
	
	private static final String DATE_FORMAT = "E MMM dd HH:mm:ss zzz yyyy";

	public static void main(String[] args) {
		String myDate = "Mon Apr 30 11:55:04 UTC 2018";
		
		ZoneId singaporeZoneId = ZoneId.of("Pacific/Pitcairn");
        
        LocalDateTime ldt = LocalDateTime.parse(myDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        
        ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        System.out.println("Date (Singapore) : " + format.format(asiaZonedDateTime));
//        System.out.println("Date (New York) : " + format.format(nyDateTime));
        
        
        
	}

}
