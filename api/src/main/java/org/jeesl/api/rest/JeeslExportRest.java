package org.jeesl.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.model.xml.system.revision.Entity;

@Path("/rest/jeesl/export")
public interface JeeslExportRest <L extends JeeslLang, D extends JeeslDescription, R extends JeeslMcsRealm<L,D,R,?>>
								extends org.jeesl.interfaces.rest.JeeslExportRest<L,D,R>
{	
	@GET @Path("/status/{code}") @Produces(MediaType.APPLICATION_XML)
	<X extends JeeslStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(@PathParam("code") String code) throws UtilsConfigurationException;
	
	@GET @Path("/revision/entity/{code}") @Produces(MediaType.APPLICATION_XML)
	Entity exportRevisionEntity(@PathParam("code") String code) throws UtilsConfigurationException;
}