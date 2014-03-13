package net.sf.ahtutils.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

public interface UtilsDbInitRest
{
	@POST @Path("status")
	@Produces(MediaType.TEXT_PLAIN) @Consumes(MediaType.APPLICATION_XML)
	String initStatus(Aht aht);
}
