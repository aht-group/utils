package net.sf.ahtutils.factory.xml.status;

import java.util.Map;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionsFactory<D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionsFactory.class);
		
	private Descriptions q;
	
	public XmlDescriptionsFactory(Descriptions q)
	{
		this.q=q;
	}	
	
	public Descriptions create(Map<String,D> mapEjb)
	{
		Descriptions xml = new Descriptions();
		
		if(q.isSetDescription() && mapEjb!=null)
		{
			XmlDescriptionFactory<D> f = new XmlDescriptionFactory<D>(q.getDescription().get(0));
			for(D ejb : mapEjb.values())
			{
				xml.getDescription().add(f.create(ejb));
			}
		}
		return xml;
	}
	
	public static Descriptions build()
	{
		return new Descriptions();
	}
	
	public static Descriptions build(String[] langs)
	{
		Descriptions xml = build();
		for(String lang : langs)
		{
			xml.getDescription().add(XmlDescriptionFactory.create(lang,""));
		}
		return xml;
	}
}