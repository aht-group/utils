package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Copy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCopyFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCopyFactory.class);
		
	public static Copy build(String code)
	{
		Copy xml = new Copy();
		xml.setCode(code);
		return xml;
	}
}