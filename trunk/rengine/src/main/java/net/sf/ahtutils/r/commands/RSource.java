package net.sf.ahtutils.r.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.factory.r.RCommandFactory;
import net.sf.ahtutils.controller.interfaces.r.RengineCommand;
import net.sf.ahtutils.r.RScript;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSource implements Serializable,RengineCommand
{
	final static Logger logger = LoggerFactory.getLogger(RScript.class);
	private static final long serialVersionUID = 1L;
	
	private REval eval;
	private String source;
	
	public RSource(String source)
	{
		this.source=source;
	}
	
	public void execute() throws Exception
	{
		RScript script = new RScript();
		script.addCommand(this);
		script.execute();
	}
	
	public List<String> render()
	{
		List<String> result = new ArrayList<String>();
		eval = RCommandFactory.eval("source(\""+source+"\")");
		result.addAll(eval.render());
		return (result);
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Source: ");
		sb.append(source);
		logger.info(sb.toString());
	}
}
