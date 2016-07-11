package org.jeesl.interfaces.rest.system.revision;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.system.revision.Entities;

import net.sf.ahtutils.xml.sync.DataUpdate;

public interface JeeslRevisionRestImport
{
	@POST @Path("/system/revision/entities") @Produces(MediaType.APPLICATION_XML) @Consumes(MediaType.APPLICATION_XML)
	DataUpdate importSystemRevisionEntities(Entities entities);
}