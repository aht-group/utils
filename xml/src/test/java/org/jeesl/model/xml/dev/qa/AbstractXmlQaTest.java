package org.jeesl.model.xml.dev.qa;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlQaTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlQaTest.class);
	
	public AbstractXmlQaTest(Class<T> cXml)
	{
		super(cXml,"qa");
	}
}