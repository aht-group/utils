package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.sf.ahtutils.test.AbstractAhtUtilsSecurityTest;
import net.sf.ahtutils.test.AhtUtilsSecurityTestBootstrap;
import net.sf.ahtutils.xml.security.Password;

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

public class TestPasswordRules extends AbstractAhtUtilsSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPasswordRules.class);
	
	@Before
	public void init()
	{
		pa = new PasswordAnalyser();
	}
	
	private PasswordAnalyser pa;
	
	@Test
	public void test()
	{
		Password result = pa.test("test");
		Assert.assertNotNull(result);
	}
	
	
	public static void main(String[] args)
	{
		AhtUtilsSecurityTestBootstrap.init();
		
		LengthRule lengthRule = new LengthRule(8, 16);

		// don't allow whitespace
//		WhitepaceRule whitespaceRule = new WhitespaceRule();

		// control allowed characters
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		// require at least 1 digit in passwords
		charRule.getRules().add(new DigitCharacterRule(1));
		// require at least 1 non-alphanumeric char
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		// require at least 1 upper case char
		charRule.getRules().add(new UppercaseCharacterRule(1));
		// require at least 1 lower case char
		charRule.getRules().add(new LowercaseCharacterRule(1));
		// require at least 3 of the previous rules be met
		charRule.setNumberOfCharacteristics(3);

		// don't allow alphabetical sequences
		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();

		// don't allow numerical sequences of length 3
//		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3);

		// don't allow qwerty sequences
		QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

		// don't allow 4 repeat characters
		RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

		// group all rules together in a List
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
//		ruleList.add(whitespaceRule);
		ruleList.add(charRule);
		ruleList.add(alphaSeqRule);
//		ruleList.add(numSeqRule);
		ruleList.add(qwertySeqRule);
		ruleList.add(repeatRule);

		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password("testpassword"));

		RuleResult result = validator.validate(passwordData);
		if (result.isValid())
		{
		  System.out.println("Valid password");
		}
		else
		{
		  System.out.println("Invalid password:");
		  for (String msg : validator.getMessages(result)){System.out.println(msg);}
		  
		  for(RuleResultDetail rrd : result.getDetails())
		  {
			  for(Object o : rrd.getValues())
			  {
				  System.out.println("  "+o);
			  }
			  Map<String,?> map = rrd.getParameters();
			  for(String key : map.keySet())
			  {
				  System.out.println(key+" "+map.get(key));
			  }
		  }
		}
	}
	
}
