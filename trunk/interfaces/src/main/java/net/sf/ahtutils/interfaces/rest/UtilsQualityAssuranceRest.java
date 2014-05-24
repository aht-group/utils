package net.sf.ahtutils.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.qa.Qa;

@Path("/rest/qa")
public interface UtilsQualityAssuranceRest
{
	public static final String cfgRestUrl = "doc.qa.rest";
	
	@GET @Path("/team/{id:[0-9]*}")
	@Produces(MediaType.APPLICATION_XML)
	Qa qaTeam(@PathParam("id") int qaId);
}
