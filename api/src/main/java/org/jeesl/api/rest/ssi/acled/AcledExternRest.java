package org.jeesl.api.rest.ssi.acled;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.ssi.acled.JsonAcledResponse;

public interface AcledExternRest
{				
    @GET @Path("/acled/read")
	@Produces(MediaType.TEXT_PLAIN)
    String sRead(@QueryParam("terms") String terms);
    
    @GET @Path("/acled/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse read(@QueryParam("terms") String terms, @QueryParam("limit") int limit);
    
    @GET @Path("/acled/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse incidents(@QueryParam("terms") String terms, @QueryParam("page") int p, @QueryParam("iso") Long iso);
    
    @GET @Path("/acled/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse incidents(@QueryParam("terms") String terms, @QueryParam("page") int p, @QueryParam("iso") Long iso, @QueryParam("admin1") String admin1);
  
    
    @GET @Path("/country/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse countries(@QueryParam("terms") String terms);
    
    @GET @Path("/country/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse countries(@QueryParam("terms") String terms, @QueryParam("iso3") String iso3Filter);
    
    @GET @Path("/actor/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse actors(@QueryParam("terms") String terms, @QueryParam("limit") int limit);
    
    @GET @Path("/actortype/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse actorType(@QueryParam("terms") String terms);
}