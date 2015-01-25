package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.MainType;
import net.sf.ahtutils.xml.status.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMainTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMainTypeFactory.class);
		
	private MainType q;
	
	public XmlMainTypeFactory(MainType q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> MainType build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> MainType build(S ejb, String group)
	{
		MainType xml = new MainType();
		if(q.isSetId()){xml.setId(ejb.getId());}
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
	
	public static MainType id()
	{
		MainType xml = new MainType();
		xml.setId(0);
		return xml;
	}
	
	public static MainType build(String code)
	{
		MainType xml = new MainType();
		xml.setCode(code);
		return xml;
	}
	
	public static MainType build(Status status)
	{
		MainType type = new MainType();
		type.setCode(status.getCode());
		type.setDescriptions(status.getDescriptions());
		type.setLangs(status.getLangs());
		return type;
	}
}