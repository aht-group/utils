package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Description;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlDescriptionFactory
{
	static Log logger = LogFactory.getLog(XmlDescriptionFactory.class);
		
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
}