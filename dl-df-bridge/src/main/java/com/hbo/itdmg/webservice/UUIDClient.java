package com.hbo.itdmg.webservice;

import java.util.Collection;

import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

public class UUIDClient implements UUIDService {
	
	private static Logger LOG = Logger.getLogger(UUIDClient.class.getName());

	private UUIDService uuidService;
	
	public static enum CLIENT_TYPE {REST("REST"), SOAP("SOAP");
		
		private String value; 
		private CLIENT_TYPE(String value) {
			this.value = value;
		}
		
		public static CLIENT_TYPE fromString(String value) {
			
			if (REST.value.equalsIgnoreCase(value)) {
				return REST;
			}

			if (SOAP.value.equalsIgnoreCase(value)) {
				return SOAP;
			}
			
			return null;
		}
	};
	
	public UUIDClient(String applicationURI, CLIENT_TYPE clientType) {
		LOG.debug("Generating client Object for "+clientType.toString());
		if (clientType == CLIENT_TYPE.REST) {
			uuidService = JAXRSClientFactory.create(applicationURI + "/cxf/rest/", UUIDService.class, null, true);
			ClientConfiguration cfgProxy = WebClient.getConfig(uuidService);
			cfgProxy.getHttpConduit().getAuthorization().setPassword("krishna");
			cfgProxy.getHttpConduit().getAuthorization().setUserName("krishna");
		} else {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(UUIDService.class);
			factory.setAddress(applicationURI + "/cxf/soap/");
			factory.setUsername("krishna");
			factory.setPassword("krishna");
			uuidService = (UUIDService) factory.create();
		}
		
	}

	@Override
	public String createUUID() {
		return uuidService.createUUID();
	}

	@Override
	public Collection<String> createUUIDs(Integer uuidCount) {
		return uuidService.createUUIDs(uuidCount);
	}

}