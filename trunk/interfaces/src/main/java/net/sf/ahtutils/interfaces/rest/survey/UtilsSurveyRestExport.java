package net.sf.ahtutils.interfaces.rest.survey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.survey.Templates;

public interface UtilsSurveyRestExport
{
	@GET @Path("/survey/template/category")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportSurveyTemplateCategory();
	
	@GET @Path("/survey/template/status")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportSurveyTemplateStatus();
	
	@GET @Path("/survey/question/unit")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportSurveyUnits();
	
	@GET @Path("/survey/status")
	@Produces(MediaType.APPLICATION_XML)
	Aht exportSurveyStatus();
	
	@GET @Path("/survey/templates")
	@Produces(MediaType.APPLICATION_XML)
	Templates exportSurveyTemplates();
}
