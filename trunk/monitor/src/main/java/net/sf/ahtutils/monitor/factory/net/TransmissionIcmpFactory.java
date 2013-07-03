package net.sf.ahtutils.monitor.factory.net;

import net.sf.ahtutils.monitor.factory.AbstractTransmissionFactory;
import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResult;
import net.sf.ahtutils.xml.monitoring.Data;
import net.sf.ahtutils.xml.monitoring.DataSet;
import net.sf.ahtutils.xml.monitoring.Indicator;
import net.sf.exlp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransmissionIcmpFactory extends AbstractTransmissionFactory
{
	final static Logger logger = LoggerFactory.getLogger(TransmissionIcmpFactory.class);
	
	public TransmissionIcmpFactory()
	{
		
	}
	
	public Indicator build(long resolution)
	{
		Indicator indicator = new Indicator();		
		indicator.setCode(DnsResult.class.getCanonicalName());
		indicator.getDataSet().add(buildDataSet(resolution));
		return indicator;
	}
	
	private DataSet buildDataSet(long resolution)
	{
		DataSet ds = new DataSet();
		
		for(IcmpResult icmp : fUm.all(IcmpResult.class))
        {
        	ds.getData().add(buildData(icmp));
        }
		
		return ds;
	}
	
	private Data buildData(IcmpResult icmp)
	{
		Data data = new Data();
		data.setRecord(DateUtil.toXmlGc(icmp.getRecord()));
		return data;
	}
}