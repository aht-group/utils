package net.sf.ahtutils.interfaces.rest.security;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.security.Security;

public interface UtilsSecurityRestExport
{
	@GET @Path("/security/views")
	@Produces(MediaType.APPLICATION_XML)
	Security exportSecurityViews();
}
