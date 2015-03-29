package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.SubType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSubTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSubTypeFactory.class);
		
	private SubType q;
	
	public XmlSubTypeFactory(SubType q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubType build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubType build(S ejb, String group)
	{
		SubType xml = new SubType();
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
	
	public static SubType id()
	{
		SubType xml = new SubType();
		xml.setId(0);
		return xml;
	}
	
	public static SubType build(String code)
	{
		SubType xml = new SubType();
		xml.setCode(code);
		return xml;
	}
	
	public static SubType transform(Status status)
	{
		SubType type = new SubType();
		type.setCode(status.getCode());
		type.setDescriptions(status.getDescriptions());
		type.setLangs(status.getLangs());
//		if(status.isSetParent()){type.setParent(status.getParent());}
		return type;
	}
}