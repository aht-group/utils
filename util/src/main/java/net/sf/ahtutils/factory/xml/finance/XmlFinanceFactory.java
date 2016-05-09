package net.sf.ahtutils.factory.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.finance.UtilsFinance;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;

public class XmlFinanceFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFinanceFactory.class);
	
	public static <F extends UtilsFinance> Finance create(F ejb)
	{
		Finance xml = new Finance();
		xml.setValue(ejb.getValue());
		return xml;
	}
	
	public static <E extends Enum<E>> Finance build(E code, double value){return create(code.toString(),value);}
	public static Finance create(String code, double value)
	{
		return build(code,null,value);
	}
	public static Finance build(String code, String label, double value)
	{
		Finance xml = new Finance();
		xml.setCode(code);
		xml.setLabel(label);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance create(String code, String label)
	{
		Finance xml = new Finance();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static void addId(Figures figures, EjbWithId ejb, Double value){addId(figures,ejb.getId(),value);}
	public static void addId(Figures figures, long id, Double value)
	{
		if(value!=null)
		{
			figures.getFinance().add(XmlFinanceFactory.id(id, value));
		}
	}
	
	public static void addCode(Figures figures, String code, Double value)
	{
		if(value!=null)
		{
			figures.getFinance().add(XmlFinanceFactory.create(code, value));
		}
	}
	
	public static Finance id(long id, double value)
	{
		Finance xml = new Finance();
		xml.setId(id);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance build()
	{
		return new Finance();
	}
}