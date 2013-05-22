package net.sf.ahtutils.xml.aht;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlAhtTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlAhtTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/aht";
	protected static File fXml;
}