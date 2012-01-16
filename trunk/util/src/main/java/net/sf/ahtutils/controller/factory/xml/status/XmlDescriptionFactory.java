package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);
		
	private Description q;
	
	public XmlDescriptionFactory(Description q)
	{
		this.q=q;
	}
	
	public <D extends UtilsDescription> Description create(D ejb)
	{
		Description xml = new Description();
		if(q.isSetValue()){xml.setValue(ejb.getLang());}
		if(q.isSetKey()){xml.setKey(ejb.getLkey());}
		return xml;
	}
	
	public static Description create(String key, String translation)
	{
		Description xml = new Description();
		xml.setKey(key);
		xml.setValue(translation);
		return xml;
	}
}