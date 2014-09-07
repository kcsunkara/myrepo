package com.hbo.itdmg.webservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

@Service("uuidService")
public class UUIDServiceImpl implements UUIDService {
	
	private static Logger LOG = Logger.getLogger(UUIDServiceImpl.class.getName());

	@Autowired
	private Validator validator;

	@Override
	public String createUUID() {
		String uuid = UUID.randomUUID().toString();
		LOG.debug("Time: "+Calendar.getInstance().getTime()+", Generated UUID: " + uuid);
		return uuid;
	}

	@Override
	public Collection<String> createUUIDs(Integer uuidCount) {
		List<String> uuidList = new ArrayList<String>();
		for(int i = 0; i < uuidCount.intValue(); i++) {
			uuidList.add(UUID.randomUUID().toString());
		}
		/*LOG.debug("Time: "+Calendar.getInstance().getTime()+", Generated UUID List: " + uuidList);
		UUIDList uuidListObj = new UUIDList();
		uuidListObj.setUuids(uuidList);
		return uuidListObj;*/
		return uuidList;
	}
    
}