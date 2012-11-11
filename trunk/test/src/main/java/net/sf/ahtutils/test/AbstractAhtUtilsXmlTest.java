package net.sf.ahtutils.test;

import java.io.File;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsXmlTest.class);
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("Actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11));
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, true);
	}
}