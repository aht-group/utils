package net.sf.ahtutils.jsf.functions;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPrettyUrl extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestPrettyUrl.class);
	
	@Test
	public void simpleStatic()
	{
		String input = "a b";
		String actual = PrettyUrl.prettyUrl(input);
		Assert.assertEquals("a-b", actual);
	}
	
	@Test
	public void simpleObject()
	{
		String input = "a b";
		
		PrettyUrl pu = new PrettyUrl();
		
		String actual = pu.format(input);
		Assert.assertEquals("a-b", actual);
	}
	
	@Test
	public void dynamicBlank()
	{
		String input = "a b";
		
		PrettyUrl pu = new PrettyUrl();
		pu.setBlankReplace("_");
		
		String actual = pu.format(input);
		Assert.assertEquals("a_b", actual);
	}
}