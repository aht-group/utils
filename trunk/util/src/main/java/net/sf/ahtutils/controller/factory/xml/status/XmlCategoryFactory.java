package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.xml.status.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCategoryFactory.class);
		
	public static Category create(String code)
	{
		Category xml = new Category();
		xml.setCode(code);
		return xml;
	}
}