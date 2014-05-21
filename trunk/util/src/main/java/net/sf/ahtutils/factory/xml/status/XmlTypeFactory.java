package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTypeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTypeFactory.class);
		
	private Type q;
	
	public XmlTypeFactory(Type q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Type build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Type build(S ejb, String group)
	{
		Type xml = new Type();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
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
	
	public static Type build(String code,String label){return create(null,code,label);}
	public static Type create(String code){return create(null,code);}
	public static Type create(String key, String code){return create(key,code,null);}
	public static Type create(String key, String code, String label)
	{
		Type xml = new Type();
		xml.setKey(key);
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static Type build(Status status)
	{
		Type type = new Type();
		type.setCode(status.getCode());
		type.setDescriptions(status.getDescriptions());
		type.setLangs(status.getLangs());
		return type;
	}
}