package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.xml.security.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;

public class PasswordRuleBuilder
{
	final static Logger logger = LoggerFactory.getLogger(PasswordRuleBuilder.class);
	
	public static enum Code{length};
	private Map<Code,Boolean> mapRules;
	
	private int lengthMin,lengthMax;
	
	public PasswordRuleBuilder()
	{
		mapRules = new Hashtable<Code,Boolean>();
		clearRules();
	}
	
	public void clearRules()
	{
		for(Code code : Code.values())
		{
			mapRules.put(code, false);
		}
	}
	
	public List<Rule> buildRules()
	{
		List<Rule> ruleList = new ArrayList<Rule>();
		
		if(mapRules.get(Code.length)){ruleList.add(new LengthRule(lengthMin, lengthMax));}
		
		return ruleList;
	}
	
	// ********* RULES *********
	
	public void lengthRule(int min, int max)
	{
		mapRules.put(Code.length, true);
		lengthMin = min;
		lengthMax = max;
	}
	
	// ********* Analyser ******
	
	public PasswordAnalyser createAnalyser()
	{
		return new PasswordAnalyser(buildRules());
	}
	
}