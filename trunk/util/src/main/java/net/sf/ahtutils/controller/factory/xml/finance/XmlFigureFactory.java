package net.sf.ahtutils.controller.factory.xml.finance;

import net.sf.ahtutils.model.interfaces.finance.UtilsFigure;
import net.sf.ahtutils.xml.finance.Figure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFigureFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFigureFactory.class);
	
	public static <F extends UtilsFigure> Figure create(F ejb)
	{
		Figure xml = new Figure();
		xml.setValue(ejb.getValue());
		return xml;
	}
	
	public static Figure create(String code, double value)
	{
		Figure xml = new Figure();
		xml.setCode(code);
		xml.setValue(value);
		return xml;
	}
	
	public static Figure create(String code, String label)
	{
		Figure xml = new Figure();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}