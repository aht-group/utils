package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Original;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOriginalFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOriginalFactory.class);
		
	private Original q;
	
	public XmlOriginalFactory(Original q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Original build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Original build(S ejb, String group)
	{
		Original xml = new Original();
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
	
	public static Original build(String code)
	{
		Original xml = new Original();
		xml.setCode(code);
		return xml;
	}
	
	public static Original build(Status status)
	{
		Original xml = new Original();
		xml.setCode(status.getCode());
		xml.setDescriptions(status.getDescriptions());
		xml.setLangs(status.getLangs());
		return xml;
	}
}