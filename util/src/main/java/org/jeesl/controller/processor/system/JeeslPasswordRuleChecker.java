package org.jeesl.controller.processor.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityPasswordRating;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityPasswordRule;

public class JeeslPasswordRuleChecker <RATING extends JeeslSecurityPasswordRating<?,?,?,?>,
										RULE extends JeeslSecurityPasswordRule<?,?,?,?>>
{
	private final String regexDigits = "(.*?\\d){%d,}.*?";
	private final String regexLower = "(.*?[a-z]){%d,}.*?";
	private final String regexUpper = "(.*?[A-Z]){%d,}.*?";
	private final String regexSymbols = "(.*?[_.*+:#!?%%{}\\|@\\[\\];=\"&$\\\\/,()-]){%d,}.*?";
	
	private final Map<RULE,Boolean> mapResult; public Map<RULE, Boolean> getMapResult() {return mapResult;}

	public JeeslPasswordRuleChecker()
	{		
		mapResult = new HashMap<>();
	}
	
	public void verifyPassword(List<RULE> rules, String pwd, RATING rating)
	{
		mapResult.clear();
		for(RULE r : rules)
		{
			Integer min = Integer.valueOf(r.getSymbol());
			if(r.getCode().equals(JeeslSecurityPasswordRule.Code.length.toString())) {mapResult.put(r, validLength(pwd,min));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.digit.toString())) {mapResult.put(r, validDigits(pwd,min));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.lower.toString())) {mapResult.put(r, validLower(pwd,min));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.upper.toString())) {mapResult.put(r, validUpper(pwd,min));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.symbol.toString())) {mapResult.put(r, validSymbols(pwd,min));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.rating.toString()))
			{
				if(rating==null) {mapResult.put(r, false);}
				else {mapResult.put(r, validRating(rating,min));}
			}
		}
	}
	
	protected boolean validLength(String pwd, int min)
	{
		return pwd.length()>=min;
	}
	
	protected boolean validDigits(String pwd, int min)
	{
		return Pattern.matches(String.format(regexDigits, min), pwd);
	}
	
	protected boolean validLower(String pwd, int min)
	{
		return Pattern.matches(String.format(regexLower, min), pwd);
	}
	
	protected boolean validUpper(String pwd, int min)
	{
		return Pattern.matches(String.format(regexUpper, min), pwd);
	}
	
	protected boolean validSymbols(String pwd, int min)
	{
		return Pattern.matches(String.format(regexSymbols, min), pwd);
	}
	
	protected boolean validRating(RATING rating, int min)
	{
		return rating.getPosition()>=min;
	}
}