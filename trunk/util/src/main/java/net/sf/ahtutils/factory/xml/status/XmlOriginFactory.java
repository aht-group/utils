package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Origin;

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
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Origin build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Origin build(S ejb, String group)
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