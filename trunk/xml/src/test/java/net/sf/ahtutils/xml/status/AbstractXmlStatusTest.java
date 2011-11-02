package net.sf.ahtutils.xml.status;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlStatusTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlStatusTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/status";
	protected static File fXml;
}