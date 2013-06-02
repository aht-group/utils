package net.sf.ahtutils.controller.interfaces.r;


public interface RengineCommand
{
	void execute() throws Exception;
	String renderR();
	void debug();
}
