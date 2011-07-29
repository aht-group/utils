package net.sf.ahtutils.xml.access;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlAccessTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlAccessTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/access";
	protected static File fXml;
}