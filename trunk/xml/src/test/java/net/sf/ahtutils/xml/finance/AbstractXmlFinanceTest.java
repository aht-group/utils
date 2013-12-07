package net.sf.ahtutils.xml.finance;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlFinanceTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlFinanceTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/finance";
    protected static final String dirSuffix = "finance";
}