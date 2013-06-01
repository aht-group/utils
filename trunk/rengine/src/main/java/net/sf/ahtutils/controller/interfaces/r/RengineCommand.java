package net.sf.ahtutils.controller.interfaces.r;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REngineException;

public interface RengineCommand
{
	void execute(Rengine re) throws REngineException;
	String render();
	void debug();
}
