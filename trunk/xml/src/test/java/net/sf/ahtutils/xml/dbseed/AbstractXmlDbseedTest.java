package net.sf.ahtutils.xml.dbseed;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlDbseedTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlDbseedTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/dbseed";
	protected static File fXml;
}