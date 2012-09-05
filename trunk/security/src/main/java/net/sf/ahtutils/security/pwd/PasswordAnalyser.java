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

public class PasswordAnalyser
{
	final static Logger logger = LoggerFactory.getLogger(PasswordAnalyser.class);
	
	public static enum Code{length};
	private Map<Code,Boolean> mapRules;
	
	private List<Rule> ruleList;
	private Password xmlPwdAnalyse;
	
	private int lengthMin,lengthMax;
	
	public PasswordAnalyser()
	{
		mapRules = new Hashtable<Code,Boolean>();
		clearRules();
		lengthRule(true, 6, 250);
	}
	
	public void clearRules()
	{
		for(Code code : Code.values())
		{
			mapRules.put(code, false);
		}
	}
	
	public Password analyse(String password)
	{
		xmlPwdAnalyse = new Password();
		buildRules();
		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new edu.vt.middleware.password.Password(password));

		RuleResult result = validator.validate(passwordData);
		xmlPwdAnalyse.setValid(result.isValid());
		logger.trace("PWD is valid: "+result.isValid());
				
		return xmlPwdAnalyse;
		
	}
	
	private void buildRules()
	{
		ruleList = new ArrayList<Rule>();
		
		if(mapRules.get(Code.length)){ruleList.add(new LengthRule(lengthMin, lengthMax));}
	}
	
	public void lengthRule(boolean active, int min, int max)
	{
		mapRules.put(Code.length, active);
		lengthMin = min;
		lengthMax = max;
	}
}
