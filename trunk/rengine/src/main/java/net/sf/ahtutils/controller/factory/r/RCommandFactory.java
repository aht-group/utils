package net.sf.ahtutils.controller.factory.r;

import net.sf.ahtutils.r.commands.RAssign;
import net.sf.ahtutils.r.commands.REval;
import net.sf.ahtutils.r.commands.RSource;

public class RCommandFactory
{
	public static RAssign assign(String var, String value){return new RAssign(var,value);}
	
	public static REval eval(String eval){return new REval(eval);}
	
	public static RSource source(String source){return new RSource(source);}
}
