package net.sf.ahtutils.factory.xml.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Descriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionsFactory.class);
		
	private Descriptions q;
	
	public XmlDescriptionsFactory(Descriptions q)
	{
		this.q=q;
	}	
	
	public <D extends UtilsDescription> Descriptions create(Map<String,D> mapEjb)
	{
		Descriptions xml = new Descriptions();
		
		if(q.isSetDescription())
		{
			XmlDescriptionFactory f = new XmlDescriptionFactory(q.getDescription().get(0));
			for(D ejb : mapEjb.values())
			{
				xml.getDescription().add(f.create(ejb));
			}
		}
		return xml;
	}
}