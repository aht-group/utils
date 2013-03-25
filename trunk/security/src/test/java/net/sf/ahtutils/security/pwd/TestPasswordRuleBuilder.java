package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.test.AbstractAhtUtilsSecurityJUnit;
import net.sf.ahtutils.test.AhtUtilsSecurityTestBootstrap;

import org.junit.Assert;
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
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.RuleResultDetail;
import edu.vt.middleware.password.UppercaseCharacterRule;

public class TestPasswordRuleBuilder extends AbstractAhtUtilsSecurityJUnit
{
	final static Logger logger = LoggerFactory.getLogger(TestPasswordRuleBuilder.class);
	
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
		prb.lengthRule(5, 10);Assert.assertFalse(prb.createAnalyser().analyse(getSizedString(4)).isValid());
		prb.lengthRule(5, 10);Assert.assertTrue(prb.createAnalyser().analyse(getSizedString(6)).isValid());	
		prb.lengthRule(5, 10);Assert.assertFalse(prb.createAnalyser().analyse(getSizedString(12)).isValid());
	}
	
	@Test
	public void charDigit()
	{
		prb.clearRules();
		prb.charDigit(1);Assert.assertFalse(prb.createAnalyser().analyse("aaaaa").isValid());
		prb.charDigit(0);Assert.assertTrue(prb.createAnalyser().analyse("aaaaa").isValid());
		prb.charDigit(1);Assert.assertTrue(prb.createAnalyser().analyse("aa1aa").isValid());
	}
	
	@Test
	public void charUpper()
	{
		prb.clearRules();
		prb.charUpper(1);Assert.assertFalse(prb.createAnalyser().analyse("aaaaa").isValid());
		prb.charUpper(0);Assert.assertTrue(prb.createAnalyser().analyse("aaaaa").isValid());
		prb.charUpper(1);Assert.assertTrue(prb.createAnalyser().analyse("aaAaa").isValid());
	}
	
	@Test
	public void charLower()
	{
		prb.clearRules();
		prb.charLower(1);Assert.assertFalse(prb.createAnalyser().analyse("AAAA").isValid());
		prb.charLower(0);Assert.assertTrue(prb.createAnalyser().analyse("AAAA").isValid());
		prb.charLower(1);Assert.assertTrue(prb.createAnalyser().analyse("aaAaa").isValid());
	}
	
	@Test
	public void repeatChar()
	{
		prb.clearRules();
		prb.repeatChar(0);Assert.assertTrue(prb.createAnalyser().analyse("aaaa").isValid());
		prb.repeatChar(3);Assert.assertFalse(prb.createAnalyser().analyse("baaab").isValid());
		prb.repeatChar(3);Assert.assertFalse(prb.createAnalyser().analyse("babbbaab").isValid());
		prb.repeatChar(3);Assert.assertTrue(prb.createAnalyser().analyse("aabaa").isValid());
		prb.repeatChar(3);Assert.assertTrue(prb.createAnalyser().analyse("aabaab").isValid());
	}
	
	@Test
	public void seqNum()
	{
		prb.clearRules();
		prb.seqNum(0);Assert.assertTrue(prb.createAnalyser().analyse("1234").isValid());
		prb.seqNum(3);Assert.assertFalse(prb.createAnalyser().analyse("1235").isValid());
		prb.seqNum(3);Assert.assertFalse(prb.createAnalyser().analyse("124567").isValid());
		prb.seqNum(3);Assert.assertTrue(prb.createAnalyser().analyse("124578").isValid());
		prb.seqNum(3);Assert.assertTrue(prb.createAnalyser().analyse("12a45").isValid());
	}
	
	@Test
	public void seqChar()
	{
		prb.clearRules();
		prb.seqChar(0);Assert.assertTrue(prb.createAnalyser().analyse("abcde").isValid());
		prb.seqChar(3);Assert.assertFalse(prb.createAnalyser().analyse("abcf").isValid());
		prb.seqChar(3);Assert.assertFalse(prb.createAnalyser().analyse("abdefg").isValid());
		prb.seqChar(3);Assert.assertTrue(prb.createAnalyser().analyse("abdeh").isValid());
		prb.seqChar(3);Assert.assertTrue(prb.createAnalyser().analyse("ab3de").isValid());
	}
	
	@Test
	public void seqQwerty()
	{
		prb.clearRules();
		prb.seqQwerty(0);Assert.assertTrue(prb.createAnalyser().analyse("asdfgh").isValid());
		prb.seqQwerty(3);Assert.assertFalse(prb.createAnalyser().analyse("asdf").isValid());
		prb.seqQwerty(3);Assert.assertFalse(prb.createAnalyser().analyse("asvbn").isValid());
		prb.seqQwerty(3);Assert.assertFalse(prb.createAnalyser().analyse("hgfd").isValid());
		prb.seqQwerty(3);Assert.assertTrue(prb.createAnalyser().analyse("aservb").isValid());
		prb.seqQwerty(3);Assert.assertTrue(prb.createAnalyser().analyse("ghcvre").isValid());
	}
	
	public static void main(String[] args)
	{
		AhtUtilsSecurityTestBootstrap.init();
		
		LengthRule lengthRule = new LengthRule(18, 19);

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
		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule(3,true);

		// don't allow numerical sequences of length 3
		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3,true);

		// don't allow qwerty sequences
		QwertySequenceRule qwertySeqRule = new QwertySequenceRule(3,true);

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
		PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password("dfgh"));

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
			  logger.debug(rrd.getErrorCode());
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
