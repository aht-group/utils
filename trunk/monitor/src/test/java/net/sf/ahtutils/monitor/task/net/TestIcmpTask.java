package net.sf.ahtutils.monitor.task.net;

import java.net.InetAddress;

import net.sf.ahtutils.monitor.result.net.IcmpResult;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.shortpasta.icmp.IcmpPingResponse;
import org.shortpasta.icmp.IcmpUtil;
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
		InetAddress iaHost = InetAddress.getByName("www.google.com");
		logger.info(iaHost.getHostAddress());
		 IcmpPingResponse icmpPingResponse = IcmpUtil.executeIcmpPingRequest (iaHost.getHostAddress(),20,2000);
		 logger.info("Ping "+icmpPingResponse.toString());
		
		for(int i=0;i<5;i++)
		{
			IcmpTask task = new IcmpTask("www.google.com");
			IcmpResult result = task.call();
			logger.info(result.toString());
		}
	}
}