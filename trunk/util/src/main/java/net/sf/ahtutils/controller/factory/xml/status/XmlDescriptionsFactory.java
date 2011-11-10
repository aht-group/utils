package net.sf.ahtutils.controller.factory.xml.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Descriptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlDescriptionsFactory
{
	static Log logger = LogFactory.getLog(XmlDescriptionsFactory.class);
		
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