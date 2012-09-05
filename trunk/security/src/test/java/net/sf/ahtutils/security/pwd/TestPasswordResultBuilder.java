package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.test.AbstractAhtUtilsSecurityJUnit;
import net.sf.ahtutils.test.AhtUtilsSecurityTestBootstrap;
import net.sf.ahtutils.xml.security.Password;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.RuleResultDetail;
import edu.vt.middleware.password.UppercaseCharacterRule;

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
