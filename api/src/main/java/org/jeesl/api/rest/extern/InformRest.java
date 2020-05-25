package org.jeesl.api.rest.extern;

import java.util.List;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Encoded @Path("/API/InformAPI")
public interface InformRest
{				
	String WorkflowGroup = "";

    @GET @Path("/Workflows/WorkflowGroups")
	@Produces(MediaType.TEXT_PLAIN)
    String sWorkflows();
	
	@GET @Path("/Countries/Index")
	@Produces(MediaType.TEXT_PLAIN) String sCountries();
	
    @GET @Path("/Countries/GetByGroup/Europe")  
    @Produces(MediaType.TEXT_PLAIN) String sCountriesds();
	
	@GET @Path("/Workflows/WorkflowGroups")
	@Produces(MediaType.APPLICATION_JSON) List<String> workflows();
	
    @GET @Path("/Indicators/Index")
    @Produces("*")  List<Object>  sIndicators();
    
    @GET @Path("/Processes/GetByWorkflowId/{id}")
    @Produces("*")  List<Object>  sGetProcessesByWorkflowId( @PathParam("id") String id);
    
    @GET @Path("/Countries/Scores/?WorkflowId={WorkflowId}&Iso3={country}")
    @Produces("*") 
    List<Object>  sScoresByWfId(  @PathParam("WorkflowId") String id, @PathParam("country") String Country);
    
}