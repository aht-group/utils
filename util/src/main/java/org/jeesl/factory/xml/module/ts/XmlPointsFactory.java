package org.jeesl.factory.xml.module.ts;

import org.jeesl.model.xml.module.ts.Points;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPointsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlPointsFactory.class);
	
	public static Points build()
	{
		Points xml = new Points();
		return xml;
	}
}