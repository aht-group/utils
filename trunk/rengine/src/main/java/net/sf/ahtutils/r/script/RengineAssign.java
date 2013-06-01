package net.sf.ahtutils.r.script;

import java.io.Serializable;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RengineAssign implements Serializable,RengineCommand
{
	final static Logger logger = LoggerFactory.getLogger(RengineScript.class);
	private static final long serialVersionUID = 1L;
	
	private String var,value;
	
	public RengineAssign(String var, String value)
	{
		this.var=var;
		this.value=value;
	}
	
	public void execute(Rengine re) throws REngineException
	{
		re.assign(var, value);
	}
	
	public String render()
	{
		return ("re.assign(" +var +", " +value +");");
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
