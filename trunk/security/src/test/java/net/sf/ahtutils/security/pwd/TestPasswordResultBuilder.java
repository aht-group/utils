package net.sf.ahtutils.security.pwd;

import net.sf.ahtutils.test.AbstractAhtUtilsSecurityJUnit;
import net.sf.ahtutils.xml.security.Password;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPasswordResultBuilder extends AbstractAhtUtilsSecurityJUnit
{
	final static Logger logger = LoggerFactory.getLogger(TestPasswordResultBuilder.class);
	
	@Before
	public void init()
	{
		prb = new PasswordRuleBuilder();
	}
	
	private PasswordRuleBuilder prb;
	
	@Test
	public void length()
	{
		prb.clearRules();
		prb.lengthRule(5, 10);
		Password pwd = prb.createAnalyser().analyse(getSizedString(4));
		JaxbUtil.error(pwd);
	}
	
}
