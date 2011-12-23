package net.sf.ahtutils.jsf.menu;

import net.sf.ahtutils.test.UtilsJsfTstBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstMenu 
{
	final static Logger logger = LoggerFactory.getLogger(TstMenu.class);
	
	public TstMenu()
	{
		
	}
	
	public void debug()
	{
		logger.debug("Debugging XML menu");
	}
	
	public static void main(String[] args)
    {
		UtilsJsfTstBootstrap.init();		
			
		TstMenu test = new TstMenu();
		test.debug();
    }
}