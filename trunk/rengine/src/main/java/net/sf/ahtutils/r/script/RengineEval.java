package net.sf.ahtutils.r.script;

import java.io.Serializable;

import net.sf.ahtutils.controller.interfaces.r.RengineCommand;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RengineEval implements Serializable,RengineCommand
{
	final static Logger logger = LoggerFactory.getLogger(RengineScript.class);
	private static final long serialVersionUID = 1L;
	
	private String eval;
	
	public RengineEval(String eval)
	{
		this.eval=eval;
	}
	
	public void execute(Rengine re) throws REngineException
	{
		re.eval(eval);
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Eval: ");
		sb.append(eval);
		logger.info(sb.toString());
	}
}
