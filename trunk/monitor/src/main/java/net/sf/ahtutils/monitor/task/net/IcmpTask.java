package net.sf.ahtutils.monitor.task.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.sf.ahtutils.monitor.result.net.IcmpResults;
import net.sf.exlp.core.handler.EhList;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.shell.spawn.ping.IcmpPing;
import net.sf.exlp.shell.spawn.ping.PingEvent;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpTask implements Callable<IcmpResults>
{
	final static Logger logger = LoggerFactory.getLogger(IcmpTask.class);
	
	private String host;
	
	public IcmpTask(String host)
	{
		this.host=host;
	}

	@Override
	public IcmpResults call()
	{
		DateTime dt = new DateTime();
		
		IcmpResults results = new IcmpResults();
		
		InetAddress iaHost;
		try
		{
			iaHost = getAddress();
			List<LogEvent> list = new ArrayList<LogEvent>();
			LogEventHandler leh = new EhList(list);
			IcmpPing ping = new IcmpPing(iaHost.getHostAddress(),60);
			ping.ping(leh);
			
			for(LogEvent event : list)
			{
				PingEvent pe = (PingEvent)event;
				logger.info(pe.toString());
			}
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	    
		return results;
	}
	
	private InetAddress getAddress() throws UnknownHostException
	{
		InetAddress iaHost;
		iaHost = InetAddress.getByName(host);
		return iaHost;
	}
}