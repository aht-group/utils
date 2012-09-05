package net.sf.ahtutils.xml.security;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlSecurityTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlSecurityTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/security";
	protected static File fXml;
}