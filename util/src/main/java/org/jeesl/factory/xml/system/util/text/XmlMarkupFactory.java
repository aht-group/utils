package org.jeesl.factory.xml.system.util.text;

import org.jeesl.model.xml.text.Description;
import org.jeesl.model.xml.text.Markup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMarkupFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMarkupFactory.class);
	

	public static Markup build(){return new Markup();}
	public static Markup build(Description description){Markup xml = build(); xml.setDescription(description);return xml;}
}