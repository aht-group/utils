package net.sf.ahtutils.test.reflection;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.ahtutils.xml.aht.Aht;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClassForName extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestClassForName.class);
	
	@Test
	public void test() throws ClassNotFoundException
	{
		String expected = Aht.class.getName();
		
		Class<?> clazz = Class.forName(expected);
		String actual = clazz.getName();
		
		logger.debug("Expected: "+expected);
		logger.debug("Actual: "+actual);
		
		Assert.assertEquals(expected, actual);
	}
	
	public static void main(String args[]) throws Exception
    {
		AhtUtilsTstBootstrap.init();
		
		TestClassForName test = new TestClassForName();
		test.test();
    }
}