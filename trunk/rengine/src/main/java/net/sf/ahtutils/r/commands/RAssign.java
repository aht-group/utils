package net.sf.ahtutils.r.commands;

import java.io.Serializable;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;
import net.sf.ahtutils.r.RScript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAssign implements Serializable,RengineCommand
{
	final static Logger logger = LoggerFactory.getLogger(RScript.class);
	private static final long serialVersionUID = 1L;
	
	private String var,value;
	
	public RAssign(String var, String value)
	{
		this.var=var;
		this.value=value;
	}
	
	public void execute() throws Exception
	{
		RScript script = new RScript();
		script.addCommand(this);
		script.execute();
	}
	
	public String renderR()
	{
		return (var +" <- " +value);
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Assign: ");
		sb.append(var);
		sb.append(" ");
		sb.append(value);
		logger.info(sb.toString());
	}
}
