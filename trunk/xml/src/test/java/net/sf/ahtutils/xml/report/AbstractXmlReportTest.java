package net.sf.ahtutils.xml.report;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlReportTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlReportTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/report";
    protected static final String dirSuffix = "report";
}