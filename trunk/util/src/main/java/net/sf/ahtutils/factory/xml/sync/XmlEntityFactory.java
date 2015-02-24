package net.sf.ahtutils.factory.xml.sync;

import net.sf.ahtutils.xml.sync.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEntityFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEntityFactory.class);
	
	public static Entity build(long id)
	{
		Entity xml = new Entity();
		xml.setValue(new Long(id).toString());
		return xml;
	}
	
	public static long toLong(Entity entity)
	{
		Long l = new Long(entity.getValue());
		return l.longValue(); 
	}
}