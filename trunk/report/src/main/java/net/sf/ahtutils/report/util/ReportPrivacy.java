package net.sf.ahtutils.report.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportPrivacy
{
	final static Logger logger = LoggerFactory.getLogger(ReportPrivacy.class);
	
	private Random rnd; 
	
	public ReportPrivacy()
	{
		rnd = new Random();
	}
	
	public double d(boolean allowNegative, Double originalValue)
	{
		double r = rnd.nextDouble();
		int n = originalValue.intValue();
		if(n==0){return 0;}
		if(n<0){n=(-1)*n;}
		
		if(allowNegative && r<0){r=r*-1;}
		r=r*(1+rnd.nextInt(n));
		
		while(r<originalValue)
		{
			r=r*1.75;
		}
		return r;
	}
	
	public String s(String input)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<input.length();i++)
		{
			sb.append((char)(rnd.nextInt(26) + 'a'));
		}
		return sb.toString();
	}
}