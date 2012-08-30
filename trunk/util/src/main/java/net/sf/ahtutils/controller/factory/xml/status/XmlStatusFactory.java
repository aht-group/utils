package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStatusFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlStatusFactory.class);
		
	private Status q;
	
	public XmlStatusFactory(Status q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus> Status build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus> Status build(S ejb, String group)
	{
		Status xml = new Status();
		xml.setGroup(group);
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		return xml;
	}	
}