package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.exception.xml.UtilsXmlStructureException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScopeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopeFactory.class);
		
	private Scope q;
	private String lang;
	
	public XmlScopeFactory(Scope q, String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Scope build(S ejb) throws UtilsXmlStructureException
	{
		Scope xml = new Scope();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		if(q.isSetLabel())
		{
			if(!ejb.getName().containsKey(lang)){throw new UtilsXmlStructureException("Entity "+ejb+" does not contain lang="+lang);}
			xml.setLabel(ejb.getName().get(lang).getLang());
		}
		
		return xml;
	}	
}