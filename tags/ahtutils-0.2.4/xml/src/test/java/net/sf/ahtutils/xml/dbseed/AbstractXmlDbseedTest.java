package net.sf.ahtutils.xml.dbseed;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlDbseedTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbseedTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/dbseed";
	protected static File fXml;
}