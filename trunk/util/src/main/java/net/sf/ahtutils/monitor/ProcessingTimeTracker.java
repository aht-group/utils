package net.sf.ahtutils.monitor;

public class ProcessingTimeTracker
{
	private long start;
	private long stop;
	private int counter;
	
	public ProcessingTimeTracker()
	{
		counter=0;
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
}
