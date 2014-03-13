package net.sf.ahtutils.monitor;

import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;
import net.sf.ahtutils.xml.monitoring.Transmission;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestTransmission
{
	final static Logger logger = LoggerFactory.getLogger(RestTransmission.class);
	
	public RestTransmission(UtilsMonitoringFacade fUm)
	{
		
	}
	
	public void send(Transmission transmission)
	{
		JaxbUtil.info(transmission);
	}
}