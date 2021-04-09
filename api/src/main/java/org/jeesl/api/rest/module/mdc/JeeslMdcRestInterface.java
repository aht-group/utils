package org.jeesl.api.rest.module.mdc;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.jeesl.model.json.module.mdc.JsonMdcCollection;
import org.jeesl.model.json.module.mdc.JsonMdcData;

public interface JeeslMdcRestInterface
{
	@GET @Path("/attribute/set") @Produces(MediaType.APPLICATION_JSON)
	JsonAttributeSet attributeSet();
	
	@GET @Path("/collection") @Produces(MediaType.APPLICATION_JSON)
	JsonMdcCollection collection();
	
	@POST @Path("/upload") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	JsonMdcData upload(JsonMdcData data);
	
}