package net.sf.ahtutils.xml.navigation;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlNavigationTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlNavigationTest.class);
	
	protected static final String dirSuffix = "navigation";
	protected static final String rootDir = "src/test/resources/data/xml/navigation";
}