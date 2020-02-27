package org.jeesl.api.rest.system.io.ssi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.system.io.ssi.Docker;

public interface JeeslIoDockerRestInterface
{	
	@GET @Path("/update") @Produces(MediaType.APPLICATION_XML)
	Docker update(Docker docker);
}