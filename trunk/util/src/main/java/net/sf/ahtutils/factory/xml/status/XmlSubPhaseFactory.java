package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.SubPhase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSubPhaseFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSubPhaseFactory.class);
		
	private SubPhase q;
	
	public XmlSubPhaseFactory(SubPhase q)
	{
		this.q=q;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubPhase build(S ejb){return build(ejb,null);}
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> SubPhase build(S ejb, String group)
	{
		SubPhase xml = new SubPhase();
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
	
	public static SubPhase build(String code)
	{
		SubPhase xml = new SubPhase();
		xml.setCode(code);
		return xml;
	}
}