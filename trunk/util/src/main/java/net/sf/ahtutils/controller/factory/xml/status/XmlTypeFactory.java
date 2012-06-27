package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTypeFactory.class);
		
	private Type q;
	
	public XmlTypeFactory(Type q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus> Type build(S ejb)
	{
		Type xml = new Type();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		return xml;
	}	
}