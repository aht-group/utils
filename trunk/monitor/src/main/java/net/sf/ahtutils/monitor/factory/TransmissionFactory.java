package net.sf.ahtutils.monitor.factory;

import java.util.List;

import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;
import net.sf.ahtutils.xml.monitoring.Component;
import net.sf.ahtutils.xml.monitoring.Indicator;
import net.sf.ahtutils.xml.monitoring.Transmission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransmissionFactory extends AbstractTransmissionFactory
{
	final static Logger logger = LoggerFactory.getLogger(TransmissionFactory.class);
	
	public TransmissionFactory(UtilsMonitoringFacade fUm)
	{
		super(fUm);
	}
	
	public Transmission build(List<Indicator> indicators)
	{
		Transmission transmission = new Transmission();		
		transmission.getComponent().add(buildComponent(indicators));
		return transmission;
	}
	
	private Component buildComponent(List<Indicator> indicators)
	{
		Component component = new Component();
		component.getIndicator().addAll(indicators);
		return component;
	}
}