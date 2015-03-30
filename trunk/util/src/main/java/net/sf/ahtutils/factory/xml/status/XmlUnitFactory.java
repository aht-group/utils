package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUnitFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUnitFactory.class);
		
	private Unit q;
	
	public XmlUnitFactory(Unit q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Unit build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Unit build(S ejb, String group)
	{
		Unit xml = new Unit();
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
	
	public static Unit build(String code)
	{
		Unit xml = new Unit();
		xml.setCode(code);
		return xml;
	}
}