package net.sf.ahtutils.xml.issue;

import java.io.File;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlIssueTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlIssueTest.class);
	
	protected static final String rootDir = "src/test/resources/data/xml/issue";
	protected static File fXml;
}