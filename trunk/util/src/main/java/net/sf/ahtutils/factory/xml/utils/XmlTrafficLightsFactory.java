package net.sf.ahtutils.factory.xml.utils;

import net.sf.ahtutils.xml.utils.TrafficLights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTrafficLightsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTrafficLightsFactory.class);
		
	public static TrafficLights build()
	{
		TrafficLights xml = new TrafficLights();
		return xml;
	}
}