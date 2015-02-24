package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Gender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGenderFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlGenderFactory.class);
		
	private Gender q;
	
	public XmlGenderFactory(Gender q)
	{
		this.q=q;
	}
	
	public Gender build(S ejb){return build(ejb,null);}
	public Gender build(S ejb, String group)
	{
		Gender xml = new Gender();
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
	
	public static Gender female() {return build("female");}
	public static Gender male() {return build("male");}
	public static Gender build(String code)
	{
		Gender xml = new Gender();
		xml.setCode(code);
		return xml;
	}
	
	
}