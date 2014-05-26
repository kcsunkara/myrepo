package com.cognizant.ws.util;

import java.text.SimpleDateFormat; 
import java.util.Date; 

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class JsonDateDeserializer extends JsonDeserializer<Date> { 
	private static final Logger LOG =Logger.getLogger(JsonDateDeserializer.class);
    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd");
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	
    @Override 
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) { 
         
        try { 
        	
        	LOG.info("deserialize:"+jp.getText());
            return formatter.parse(jp.getText()); 
        } catch (Exception e) { 
            throw new RuntimeException(e); 
        } 
     
    } 

}  