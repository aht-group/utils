package net.sf.ahtutils.security.pwd;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.UppercaseCharacterRule;

public class PasswordRuleBuilder
{
	final static Logger logger = LoggerFactory.getLogger(PasswordRuleBuilder.class);
	
	public static enum Code{length,charDigit,charUpper,charLower,repChar,seqNum,seqChar,seqQwerty};
	private Map<Code,Boolean> mapRules;
	
	private int lengthMin,lengthMax;
	private int charDigitMin,charUpperMin,charLowerMin;
	private int repeatCharMax,seqNumMax,seqCharMax,seqQwertyMax;
	
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
		if(mapRules.get(Code.charDigit)){ruleList.add(new DigitCharacterRule(charDigitMin));}
		if(mapRules.get(Code.charUpper)){ruleList.add(new UppercaseCharacterRule(charUpperMin));}
		if(mapRules.get(Code.charLower)){ruleList.add(new LowercaseCharacterRule(charLowerMin));}
		if(mapRules.get(Code.repChar)){ruleList.add(new RepeatCharacterRegexRule(repeatCharMax));}
		if(mapRules.get(Code.seqNum)){ruleList.add(new NumericalSequenceRule(seqNumMax,true));}
		if(mapRules.get(Code.seqChar)){ruleList.add(new AlphabeticalSequenceRule(seqCharMax,true));}
		if(mapRules.get(Code.seqQwerty)){ruleList.add(new QwertySequenceRule(seqQwertyMax,true));}
		
		return ruleList;
	}
	
	// ********* RULES *********
	
	public void lengthRule(int lengthMin, int lengthMax)
	{
		mapRules.put(Code.length, true);
		this.lengthMin = lengthMin;
		this.lengthMax = lengthMax;
	}
	
	public void charDigit(int charDigitMin)
	{
		if(charDigitMin>0)
		{
			mapRules.put(Code.charDigit, true);
			this.charDigitMin=charDigitMin;
		}
		else{mapRules.put(Code.charDigit, false);}
	}
	
	public void charUpper(int charUpperMin)
	{
		if(charUpperMin>0)
		{
			mapRules.put(Code.charUpper, true);
			this.charUpperMin=charUpperMin;
		}
		else{mapRules.put(Code.charUpper, false);}
	}
	
	public void charLower(int charLowerMin)
	{
		if(charLowerMin>0)
		{
			mapRules.put(Code.charLower, true);
			this.charLowerMin=charLowerMin;
		}
		else{mapRules.put(Code.charLower, false);}
	}
	
	public void repeatChar(int repeatCharMax)
	{
		if(repeatCharMax>0)
		{
			mapRules.put(Code.repChar, true);
			this.repeatCharMax=repeatCharMax;
		}
		else{mapRules.put(Code.repChar, false);}
	}
	
	public void seqNum(int seqNumMax)
	{
		if(seqNumMax>0)
		{
			mapRules.put(Code.seqNum, true);
			this.seqNumMax=seqNumMax;
		}
		else{mapRules.put(Code.seqNum, false);}
	}
	
	public void seqChar(int seqCharMax)
	{
		if(seqCharMax>0)
		{
			mapRules.put(Code.seqChar, true);
			this.seqCharMax=seqCharMax;
		}
		else{mapRules.put(Code.seqChar, false);}
	}
	
	public void seqQwerty(int seqQwertyMax)
	{
		if(seqQwertyMax>0)
		{
			mapRules.put(Code.seqQwerty, true);
			this.seqQwertyMax=seqQwertyMax;
		}
		else{mapRules.put(Code.seqQwerty, false);}
	}
	
	
	
	// ********* Analyser ******
	
	public PasswordAnalyser createAnalyser()
	{
		return new PasswordAnalyser(buildRules());
	}
	
}