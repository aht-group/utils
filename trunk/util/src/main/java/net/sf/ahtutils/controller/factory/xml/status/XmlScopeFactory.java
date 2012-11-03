package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScopeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopeFactory.class);
		
	private Scope q;
	
	public XmlScopeFactory(Scope q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus> Scope build(S ejb)
	{
		Scope xml = new Scope();
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