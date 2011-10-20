package net.sf.ahtutils.xml.mail;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractXmlMailTest extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(AbstractXmlMailTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/xml/mail";
	protected static File fXml;
}