package net.sf.ahtutils.interfaces.rest.util.traffic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.utils.TrafficLights;

public interface UtilsTrafficLightRestExport
{
	@GET @Path("/utils/trafficLights") @Produces(MediaType.APPLICATION_XML)
	TrafficLights exportTrafficLights();
}
