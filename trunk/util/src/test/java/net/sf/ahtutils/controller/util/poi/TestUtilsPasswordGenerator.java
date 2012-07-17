package net.sf.ahtutils.controller.util.poi;

import junit.framework.Assert;
import net.sf.ahtutils.controller.util.UtilsPasswordGenerator;
import net.sf.ahtutils.test.AhtUtilTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestUtilsPasswordGenerator
{
	static Log logger = LogFactory.getLog(TestUtilsPasswordGenerator.class);
		
	@Test
	public void pwdSize()
	{
		for(int i=5;i<15;i++)
		{
			String pwd = UtilsPasswordGenerator.random(i);
			Assert.assertEquals(i, pwd.length());
		}
	}
	
	public void direct()
	{
		logger.debug(UtilsPasswordGenerator.random());
	}
	
	public static void main (String[] args) throws Exception
	{
		AhtUtilTstBootstrap.init();
		logger.debug("Test");
		TestUtilsPasswordGenerator test = new TestUtilsPasswordGenerator();
		test.direct();
		
	}
}