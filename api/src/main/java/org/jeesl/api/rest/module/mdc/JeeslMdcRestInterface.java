package org.jeesl.api.rest.module.mdc;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.jeesl.model.json.module.mdc.JsonMdcCollection;
import org.jeesl.model.json.module.mdc.JsonMdcContainer;
import org.jeesl.model.json.module.mdc.JsonMdcData;

public interface JeeslMdcRestInterface
{
	@Deprecated // Will be removed soon
	@GET @Path("/attribute/set") @Produces(MediaType.APPLICATION_JSON)
	JsonAttributeSet attributeSet();
	
	@Deprecated // Will be removed soon and replaced by /collection/download
	@GET @Path("/collection") @Produces(MediaType.APPLICATION_JSON)
	JsonMdcCollection collection();
	
	@GET @Path("/collection/enrolment/{token}") @Produces(MediaType.APPLICATION_JSON)
	JsonMdcContainer enrolment(@PathParam("token") String token);

	@GET @Path("/collection/download/{id}") @Produces(MediaType.APPLICATION_JSON)
	JsonMdcContainer download(@PathParam("id") Long id);
	
	@POST @Path("/upload") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
	JsonMdcData upload(JsonMdcData data);
	
}