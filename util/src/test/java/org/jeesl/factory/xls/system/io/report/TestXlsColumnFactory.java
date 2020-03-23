package org.jeesl.factory.xls.system.io.report;

import org.jeesl.AbstractJeeslUtilTest;
import org.jeesl.factory.txt.system.io.mail.core.TxtMimeTypeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXlsColumnFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXlsColumnFactory.class);
		
	@Test
	public void code2Index()
    {	
		
		Assert.assertEquals(0,XlsColumnFactory.code2Index("A"));
		Assert.assertEquals(25,XlsColumnFactory.code2Index("Z"));
		Assert.assertEquals(188,XlsColumnFactory.code2Index("GG"));
    }
}