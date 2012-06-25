package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlConditionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlConditionFactory.class);
		
	private Condition q;
	
	public XmlConditionFactory(Condition q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus> Condition build(S ejb)
	{
		Condition xml = new Condition();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		
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