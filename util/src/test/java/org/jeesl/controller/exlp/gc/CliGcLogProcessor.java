package org.jeesl.controller.exlp.gc;

import org.jeesl.JeeslUtilTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerTail;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class CliGcLogProcessor
{
	final static Logger logger = LoggerFactory.getLogger(CliGcLogProcessor.class);
	
	public CliGcLogProcessor()
	{
		
	}
	
	public void jboss()
	{
		LogEventHandler leh = new EhDebug();
		LogParser lp = new WildflyGcParser(leh);
		
		LogListener ll = new LogListenerTail(lp,"/Volumes/ramdisk/jboss/standalone/log/gc.log.0.current");
		ll.processSingle();
	}
	
	public static void main(String[] args) throws Exception
	{
		JeeslUtilTestBootstrap.init();
		
		CliGcLogProcessor cli = new CliGcLogProcessor();
		cli.jboss();
	}
}