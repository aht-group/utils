package net.sf.ahtutils.xml.monitoring;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlMonitoringTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMonitoringTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/monitoring";
    protected static final String dirSuffix = "monitoring";
}