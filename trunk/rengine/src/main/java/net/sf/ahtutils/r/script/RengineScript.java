package net.sf.ahtutils.r.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RengineScript implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(RengineScript.class);
	private static final long serialVersionUID = 1L;
	
	private List<RengineCommand> commands;
	
	public RengineScript()
	{
		commands = new ArrayList<RengineCommand>();
	}

	public void addCommand(RengineCommand command)
	{
		commands.add(command);
	}
	
	public void execute(Rengine re) throws REngineException
	{
		StringBuffer cmdBuffer = new StringBuffer();
		for(RengineCommand cmd : commands)
		{
			cmdBuffer.append(cmd.render() +"\n");
			cmd.execute(re);
		}
		re.eval(cmdBuffer.toString());
	}
	
	public void debug()
	{
		for(RengineCommand cmd : commands)
		{
			cmd.debug();
		}
	}
}
