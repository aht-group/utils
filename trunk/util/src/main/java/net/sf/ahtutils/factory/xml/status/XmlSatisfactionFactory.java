package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Phase;
import net.sf.ahtutils.xml.status.Satisfaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSatisfactionFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSatisfactionFactory.class);
		
	private Satisfaction q;
	
	public XmlSatisfactionFactory(Satisfaction q)
	{
		this.q=q;
	}
	
	public Satisfaction build(S ejb){return build(ejb,null);}
	public Satisfaction build(S ejb, String group)
	{
		Satisfaction xml = new Satisfaction();
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
	
	public static Phase build(String code)
	{
		Phase xml = new Phase();
		xml.setCode(code);
		return xml;
	}
}