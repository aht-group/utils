package net.sf.ahtutils.factory.xml.finance;

import net.sf.ahtutils.xml.finance.Figures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFiguresFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFiguresFactory.class);
	
	public static Figures build()
	{
		Figures xml = new Figures();

		return xml;
	}
}