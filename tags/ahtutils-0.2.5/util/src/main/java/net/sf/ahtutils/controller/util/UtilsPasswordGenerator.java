package net.sf.ahtutils.controller.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PwGenerator;

public class UtilsPasswordGenerator
{
	final static Logger logger = LoggerFactory.getLogger(UtilsPasswordGenerator.class);
	
	public static String random()
	{
		return random(10);
	}
	
	public static String random(int size)
	{
		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder(); 
		flags.setIncludeNumerals();
//		flags.setIncludeCapitals(); 
//		flags.setIncludeReducedSymbols();
		flags.setFilterAmbiguous(); 
		
		List<String> passwords = PwGenerator.generate(size, 1, 1000, flags.build(), null, null); 
		return passwords.get(0);
	}
}
