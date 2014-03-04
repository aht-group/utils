package net.sf.ahtutils.monitor;

public class ProcessingTimeTracker
{
	private long start;
	private long stop;
	
	public ProcessingTimeTracker()
	{
		
	}
	
	public void start()
	{
		start = System.currentTimeMillis();
	}
	
	public void stop()
	{
		stop = System.currentTimeMillis();
	}
	
	public String toTotalTime()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(stop-start);
		sb.append(" ms");
		return sb.toString();
	}
}
