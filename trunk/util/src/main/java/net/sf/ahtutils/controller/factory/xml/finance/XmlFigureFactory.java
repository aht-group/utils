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
}