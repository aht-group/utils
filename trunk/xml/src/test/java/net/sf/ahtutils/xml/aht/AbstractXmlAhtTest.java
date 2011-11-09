package net.sf.ahtutils.xml.aht;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlAhtTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlAhtTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/aht";
	protected static File fXml;
}