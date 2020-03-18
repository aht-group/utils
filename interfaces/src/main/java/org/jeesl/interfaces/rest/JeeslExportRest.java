package org.jeesl.interfaces.rest;

import javax.ws.rs.PathParam;

import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.model.xml.system.revision.Entity;

public interface JeeslExportRest <L extends JeeslLang, D extends JeeslDescription,
									R extends JeeslMcsRealm<L,D,R,?>,
									G extends JeeslGraphic<L,D,?,?,?>>
{	
	<X extends JeeslStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(String code) throws UtilsConfigurationException;
	Entity exportRevisionEntity(@PathParam("code") String code) throws UtilsConfigurationException;
}