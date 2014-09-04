package net.sf.ahtutils.interfaces.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.xml.io.Dir;

public interface UtilsDbDumpRest
{	
	@POST @Path("/upload")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	DataUpdate uploadDumps(Dir directory);
}
