package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Origin;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOriginFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOriginFactory.class);
		
	private Origin q;
	
	public XmlOriginFactory(Origin q)
	{
		this.q=q;
	}
	
	public <S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription> Origin build(S ejb){return build(ejb,null);}
	public <S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription> Origin build(S ejb, String group)
	{
		Origin xml = new Origin();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		return xml;
	}
	
	public static Origin build(String code)
	{
		Origin xml = new Origin();
		xml.setCode(code);
		return xml;
	}
}