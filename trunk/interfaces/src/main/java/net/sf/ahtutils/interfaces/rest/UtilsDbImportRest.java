package net.sf.ahtutils.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.Utils;

public interface UtilsDbImportRest
{
	@POST @Path("/utils/properties")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate importProperties(Utils utils);
}
