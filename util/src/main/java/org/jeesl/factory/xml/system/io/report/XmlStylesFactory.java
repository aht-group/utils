package org.jeesl.factory.xml.system.io.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Styles;

public class XmlStylesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStylesFactory.class);
	
	public static Styles build()
	{
		Styles xml = new Styles();
		return xml;
	}
}