package com.hbo.itdmg.webservice;

import java.util.Collection;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/uuid/")
@WebService
public interface UUIDService {

	@WebMethod
	@GET
	public String createUUID();
	
	@WebMethod
//	@Produces("application/json")
	@GET
	@Path("{uuidCount}")
	public Collection<String> createUUIDs(@PathParam("uuidCount") Integer uuidCount);
	
}
