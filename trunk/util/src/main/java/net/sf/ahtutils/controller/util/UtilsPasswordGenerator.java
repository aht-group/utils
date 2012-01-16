package net.sf.ahtutils.controller.util;

import de.rrze.jpwgen.impl.PwGenerator;
import de.rrze.jpwgen.utils.BlankRemover;

public class UtilsPasswordGenerator
{
	public static String getPwd()
	{
		String flags = "-N 1 -M 10000  -m -q -s 10"; 
		flags = BlankRemover.itrim(flags); 
		String[] ar = flags.split(" "); 
		PwGenerator generator = new PwGenerator(); 
		generator.getDefaultBlacklistFilter().addToBlacklist("badpassword"); 
//		List<String> passwords = generator.generatePassword(arg0, arg1, arg2, arg3); 
		return "pwd";
	}
}
