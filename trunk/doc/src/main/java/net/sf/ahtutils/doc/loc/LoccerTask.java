package net.sf.ahtutils.doc.loc;

import java.io.IOException;
import java.util.Map;

import net.sf.ahtutils.doc.loc.CounterSelector.Lang;

public class LoccerTask 
{
	
	private Loccer loccer; 
	
	public LoccerTask()
	{
		loccer = new Loccer();
	}
	
	public LoccerTask(String dir)
	{
		try
		{
			loccer = new Loccer(dir);
			printStats(loccer.getSummary());
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
    private void printStats(Map<Lang,BasicFileInfo> stats)
    {
    	for(Lang lang : stats.keySet())
    	{
        	BasicFileInfo bfi = stats.get(lang); 
    		System.out.println(lang.toString()+": "+bfi);
    	}
    }
    
	public static void main(String args[])
	{
		new LoccerTask("/Users/thorsten/workspace/4.4/ahtutils");
	}
}
