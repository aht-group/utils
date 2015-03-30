package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Responsible;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlResponsibleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlResponsibleFactory.class);
		
	private Responsible q;
	
	public XmlResponsibleFactory(Responsible q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Responsible build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Responsible build(S ejb, String group)
	{
		Responsible xml = new Responsible();
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
	
	public static Responsible build(String code)
	{
		Responsible xml = new Responsible();
		xml.setCode(code);
		return xml;
	}
	
	public static Responsible buildLabel(String code, String label)
	{
		Responsible xml = new Responsible();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}