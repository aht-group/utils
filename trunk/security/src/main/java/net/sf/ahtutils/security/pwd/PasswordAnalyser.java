package net.sf.ahtutils.security.pwd;

import net.sf.ahtutils.xml.security.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordAnalyser
{
	final static Logger logger = LoggerFactory.getLogger(PasswordAnalyser.class);
	
	private int lengthMin,lengthMax;
	
	public PasswordAnalyser()
	{
		lengthMin = 8;
		lengthMax = 250;
	}
	
	public Password test(String password)
	{
		Password xml = new Password();
		
		return xml;
		
	}
}
