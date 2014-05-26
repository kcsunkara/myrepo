package com.cognizant.ws.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;



public class CustomObjectMapper extends ObjectMapper {
	private static final Logger LOG = Logger.getLogger(CustomObjectMapper.class);
    public CustomObjectMapper(){
    	LOG.info("CustomObjectMapper:");
    	ObjectMapper objectMapper = new ObjectMapper();
    	final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	objectMapper.setDateFormat(df);
    }
}
