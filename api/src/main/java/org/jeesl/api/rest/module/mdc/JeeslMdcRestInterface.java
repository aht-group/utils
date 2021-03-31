package org.jeesl.api.rest.module.mdc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.jeesl.model.json.module.mdc.JsonMdcCollection;

public interface JeeslMdcRestInterface
{
	@GET @Path("/attribute/set") @Produces(MediaType.APPLICATION_JSON)
	JsonAttributeSet attributeSet();
	
	@GET @Path("/collection") @Produces(MediaType.APPLICATION_JSON)
	JsonMdcCollection collection();
}