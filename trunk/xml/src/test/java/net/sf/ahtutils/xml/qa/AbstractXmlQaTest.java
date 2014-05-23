package net.sf.ahtutils.xml.qa;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlQaTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlQaTest.class);
	
    protected static final String dirSuffix = "qa";
}