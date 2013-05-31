package net.sf.ahtutils.r.script;

import java.io.Serializable;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RengineEval implements Serializable,RengineCommand
{
	final static Logger logger = LoggerFactory.getLogger(RengineScript.class);
	private static final long serialVersionUID = 1L;
	
	public RengineEval()
	{
		
	}
	
	public void execute()
	{
		logger.info("Eval");
	}
}
