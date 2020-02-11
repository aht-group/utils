package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.ProcurementMethod;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlProcurementMethodFactory <S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcurementMethodFactory.class);
		
	private ProcurementMethod q;
	
	public XmlProcurementMethodFactory(ProcurementMethod q)
	{
		this.q=q;
	}
	
	public ProcurementMethod build(S ejb){return build(ejb,null);}
	public ProcurementMethod build(S ejb, String group)
	{
		ProcurementMethod xml = new ProcurementMethod();
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
	
	public static ProcurementMethod build(String code)
	{
		ProcurementMethod xml = new ProcurementMethod();
		xml.setCode(code);
		return xml;
	}
}