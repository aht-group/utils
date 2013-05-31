package net.sf.ahtutils.controller.factory.r;

import net.sf.ahtutils.r.script.RengineAssign;
import net.sf.ahtutils.r.script.RengineEval;

public class RengineCommandFactory
{
	public static RengineAssign assign(String var, String value)
	{
		return new RengineAssign(var,value);
	}
	
	public static RengineEval eval(String eval)
	{
		return new RengineEval(eval);
	}
}
