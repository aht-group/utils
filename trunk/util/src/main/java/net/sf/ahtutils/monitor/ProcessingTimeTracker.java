package net.sf.ahtutils.monitor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingTimeTracker
{
	final static Logger logger = LoggerFactory.getLogger(ProcessingTimeTracker.class);
	
	private long start;
	private long stop;
	private int counter;
	
	public ProcessingTimeTracker()
	{
		this(false);
	}
	public ProcessingTimeTracker(boolean autoStart)
	{
		counter=0;
		if(autoStart){start();}
	}
	

	
	public void start()
	{
		start = System.currentTimeMillis();
	}
	
	public void stop()
	{
		stop = System.currentTimeMillis();
	}
	
	public void round()
	{
		counter++;
	}
	
	public int getCounter() {return counter;}
	
	public String toTotalTime()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(stop-start);
		sb.append(" ms");
		return sb.toString();
	}
	
	public String buildDefaultDebug(String prefix)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(" ").append(counter).append(" times");
		sb.append(" in ").append(toTotalTime());
		return sb.toString();
	}
	
	private List<Long> ticksTime;
	private List<String> ticksMarker;
	private String ticker;
	
	public void startTicker(String ticker)
	{
		this.ticker=ticker;
		ticksTime = new ArrayList<Long>();
		ticksMarker = new ArrayList<String>();
		start = System.currentTimeMillis();
	}
	
	public void tick(String s)
	{
		ticksTime.add(System.currentTimeMillis());
		ticksMarker.add(s);
	}
	
	public void debugTicker()
	{
		logger.info("Ticker: "+ticker);
		for(int i=0;i<ticksTime.size();i++)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\t");
			sb.append(ticksMarker.get(i));
			
			long ms = ticksTime.get(i)-start;
			sb.append(": ").append(ms);
			logger.info(sb.toString());
		}
	}
}
