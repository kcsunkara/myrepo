package com.cognizant.ws.util;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;



@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final Logger LOG =Logger.getLogger(JsonDateSerializer.class);
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US);

	@Override
	public void serialize(Date date, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		LOG.info("aaaaaa");
		jgen.writeString( FORMAT.format( date));
	}

}