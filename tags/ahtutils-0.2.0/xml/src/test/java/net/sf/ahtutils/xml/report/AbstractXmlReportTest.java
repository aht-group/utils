package net.sf.ahtutils.xml.report;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlReportTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlReportTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/report";
	protected static File fXml;
}