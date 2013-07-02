package net.sf.ahtutils.monitor.task.net.icmp;

import net.sf.ahtutils.monitor.result.net.IcmpResult;
import net.sf.ahtutils.monitor.task.net.IcmpTask;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestIcmpTask
{
	final static Logger logger = LoggerFactory.getLogger(TestIcmpTask.class);
	
	@Test
	public void test() throws Exception
	{
		IcmpTask task = new IcmpTask("localhost");
		IcmpResult result = task.call();
		Assert.assertEquals(IcmpResult.Code.REACHABLE, result.getCode());
	}
	
	public static void main(String args[]) throws Exception
	{
		UtilsMonitorTestBootstrap.init();
		
		for(int i=0;i<100;i++)
		{
			IcmpTask task = new IcmpTask("192.168.1.152");
			IcmpResult result = task.call();
			logger.info(result.toString());
		}
	}
}