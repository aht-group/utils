package org.jeesl.controller.processor.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityPasswordRating;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityPasswordRule;
import org.jeesl.interfaces.model.system.security.user.JeeslPasswordHistory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

public class JeeslPasswordRuleChecker <RATING extends JeeslSecurityPasswordRating<?,?,?,?>,
										RULE extends JeeslSecurityPasswordRule<?,?,?,?>,
										HISTORY extends JeeslPasswordHistory<?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslPasswordRuleChecker.class);
	
	private final String regexDigits = "(.*?\\d){%d,}.*?";
	private final String regexLower = "(.*?[a-z]){%d,}.*?";
	private final String regexUpper = "(.*?[A-Z]){%d,}.*?";
	private final String regexSymbols = "(.*?[_.*+:#!?%%{}\\|@\\[\\];=\"&$\\\\/,()-]){%d,}.*?";
	
	private final Class<RATING> cRating;
	private final Zxcvbn zxcvbn;
	
	private final Map<RULE,Boolean> mapResult; public Map<RULE,Boolean> getMapResult() {return mapResult;}

	private JeeslFacade fJeesl;
	
	public JeeslPasswordRuleChecker(JeeslFacade fJeesl, Class<RATING> cRating)
	{		
		this.fJeesl=fJeesl;
		this.cRating=cRating;
		zxcvbn = new Zxcvbn();
		mapResult = new HashMap<>();
	}
	
	public void clear()
	{
		mapResult.clear();
	}
	
	public boolean passwordCompliant()
	{
		boolean result = true;
		for(Boolean r : mapResult.values())
		{
			if(r==false) {result=false;}
		}
		return result;
	}
	
	public void analyseComplexity1(List<RULE> rules, String pwd) throws JeeslNotFoundException
	{
		RATING rating = null;
		Strength strength = zxcvbn.measure(pwd);
		
		logger.info("fJeesl:"+(fJeesl==null));
		logger.info("strength:"+(strength==null));
		rating = fJeesl.fByCode(cRating,JeeslSecurityPasswordRating.codePrefix+""+strength.getScore());
		
		analyseComplexity1(rules,pwd,rating);
	}
	
	public void analyseComplexity1(List<RULE> rules, String pwd, RATING rating)
	{
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
	
	public void analyseHistory(List<RULE> rules, String hash, List<HISTORY> histories)
	{
		for(RULE r : rules)
		{
			Integer value = Integer.valueOf(r.getSymbol());
			if(r.getCode().equals(JeeslSecurityPasswordRule.Code.history.toString())) {mapResult.put(r, validHistory(value,hash,histories));}
			else if(r.getCode().equals(JeeslSecurityPasswordRule.Code.age.toString())) {mapResult.put(r, validAge(value,histories));}
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
	
	protected boolean validHistory(int maxMonths, String hash, List<HISTORY> histories)
	{
		DateTime dtNow = new DateTime();
		for(HISTORY h : histories)
		{
			DateTime dt = new DateTime(h.getRecord());
			int months = Months.monthsBetween(dt,dtNow).getMonths();
			if(h.getPwd().contentEquals(hash) && months<=maxMonths)
			{
				return false;
			}
		}
		return true;
	}
	
	protected boolean validAge(int maxMonths, List<HISTORY> histories)
	{
		if(histories.isEmpty()) {return true;}
		DateTime dtNow = new DateTime();
		DateTime dt = new DateTime(histories.get(0).getRecord());
		int months = Days.daysBetween(dt,dtNow).getDays();
		if(months>=maxMonths) {return false;}
		else {return true;}
	}
}