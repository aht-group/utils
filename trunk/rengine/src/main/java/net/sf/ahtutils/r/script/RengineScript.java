package net.sf.ahtutils.r.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rcaller.RCaller;
import rcaller.RCode;

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
		
	public void execute() throws Exception
	{
		RCaller rc = new RCaller();
		rc.setRscriptExecutable("/usr/bin/Rscript");
		rc.cleanRCode();
		RCode code = new RCode();
		for(RengineCommand cmd : commands)
		{
			logger.debug("Adding code for execution in R: " +cmd.renderR());
			code.addRCode(cmd.renderR() +"\n");
		}
		rc.setRCode(code);
		rc.runOnly();
	}
	
	public void debug()
	{
		for(RengineCommand cmd : commands)
		{
			cmd.debug();
		}
	}
}
