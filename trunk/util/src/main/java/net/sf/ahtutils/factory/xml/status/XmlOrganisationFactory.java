package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Copy;
import net.sf.ahtutils.xml.status.Organisation;
import net.sf.ahtutils.xml.status.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlOrganisationFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlOrganisationFactory.class);
		
	private Copy q;
	
	public XmlOrganisationFactory(Copy q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Organisation build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Organisation build(S ejb, String group)
	{
		Organisation xml = new Organisation();
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
	
	public static Organisation build(String code)
	{
		Organisation xml = new Organisation();
		xml.setCode(code);
		return xml;
	}
	
	public static Organisation build(Status status)
	{
		Organisation xml = new Organisation();
		xml.setCode(status.getCode());
		xml.setDescriptions(status.getDescriptions());
		xml.setLangs(status.getLangs());
		return xml;
	}
}