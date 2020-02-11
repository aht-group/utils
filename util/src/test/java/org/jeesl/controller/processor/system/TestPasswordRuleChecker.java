package org.jeesl.controller.processor.system;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPasswordRuleChecker extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPasswordRuleChecker.class);
	
	private JeeslPasswordRuleChecker<?,?> checker;
	
	@Before public void init()
	{
		checker = new JeeslPasswordRuleChecker<>();
	}
	
	@Test public void length()
	{
		Assert.assertEquals(true,checker.validLength("abc", 3));
		Assert.assertEquals(false,checker.validLength("abc", 4));
	}
	
	@Test public void digits()
	{
		Assert.assertEquals(true,checker.validDigits("a1b2c", 2));
		Assert.assertEquals(true,checker.validDigits("abcdefghijklmn12", 2));
		Assert.assertEquals(true,checker.validDigits("11abcdefghijklmn", 2));
		Assert.assertEquals(false,checker.validDigits("ab1c", 2));
	}
	
	@Test public void lower()
	{
		Assert.assertEquals(true,checker.validLower("abc", 2));
		Assert.assertEquals(true,checker.validLower("ABCDEFGHIKLMNxy", 2));
		Assert.assertEquals(true,checker.validLower("ABCDEFGHIKLMNxy", 2));
		Assert.assertEquals(true,checker.validLower("xABCDEFGHIKLMNx", 2));
		Assert.assertEquals(true,checker.validLower("AxxBCDEFGHIKLMN", 2));
		Assert.assertEquals(true,checker.validLower("ABxCDEFGHIKxLMN", 2));
		Assert.assertEquals(false,checker.validLower("ABCDEFGHIKLMNxy", 3));
		Assert.assertEquals(true,checker.validLower("ABCDEFGHIKLMNxyy", 3));
		Assert.assertEquals(false,checker.validLower("AbC", 2));
	}
	
	@Test public void upper()
	{
		Assert.assertEquals(true,checker.validUpper("ABC", 2));
		Assert.assertEquals(false,checker.validUpper("Abc", 2));
		Assert.assertEquals(false,checker.validUpper("Abcksdfjnvgkjwnv", 2));
		Assert.assertEquals(true,checker.validUpper("AbcksdfjBvgkjwnv", 2));
	}
	
	@Test public void symbols()
	{
		Assert.assertEquals(true,checker.validSymbols("a-bc*", 2));
		Assert.assertEquals(false,checker.validSymbols("ab=c", 2));
	}
}