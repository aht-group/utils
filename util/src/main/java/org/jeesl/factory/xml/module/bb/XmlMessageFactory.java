package org.jeesl.factory.xml.module.bb;

import org.jeesl.model.xml.module.bb.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMessageFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMessageFactory.class);
	
	public static Message build() {return new Message();}
	public static Message build(String subject) {Message xml = build(); xml.setSubject(subject); return xml;}
}