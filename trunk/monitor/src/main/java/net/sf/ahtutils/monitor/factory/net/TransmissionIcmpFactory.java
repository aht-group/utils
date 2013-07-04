package net.sf.ahtutils.monitor.factory.net;

import java.util.Date;

import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;
import net.sf.ahtutils.monitor.DbCleaner;
import net.sf.ahtutils.monitor.factory.AbstractTransmissionFactory;
import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResult;
import net.sf.ahtutils.xml.monitoring.Data;
import net.sf.ahtutils.xml.monitoring.DataSet;
import net.sf.ahtutils.xml.monitoring.Indicator;
import net.sf.exlp.util.DateUtil;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransmissionIcmpFactory extends AbstractTransmissionFactory
{
	final static Logger logger = LoggerFactory.getLogger(TransmissionIcmpFactory.class);
	
	public TransmissionIcmpFactory(UtilsMonitoringFacade fUm,DbCleaner dbCleaner)
	{
		super(fUm,dbCleaner);
	}
	
	public Indicator build(DateTime first, DateTime last, Duration range)
	{
		for(IcmpResult icmp : fUm.inInterval(IcmpResult.class,new Date(0),first.toDate()))
        {	//Ignoring all records before the first ceil
			dbCleaner.add(IcmpResult.class.getName(), icmp.getId());
        }
		
		Indicator indicator = new Indicator();		
		indicator.setCode(DnsResult.class.getCanonicalName());
		indicator.getDataSet().add(buildDataSet(first,last,range));
		return indicator;
	}
	
	private DataSet buildDataSet(DateTime first, DateTime last, Duration range)
	{
		DataSet ds = new DataSet();
		
		MutableDateTime mdtStart = new MutableDateTime(first);
		MutableDateTime mdtEnd = new MutableDateTime(first);
		mdtEnd.add(range);
		
		while(mdtEnd.isBefore(last) || mdtEnd.isEqual(last))
		{
			logger.info("Processing "+mdtStart+" -> "+mdtEnd);
			for(IcmpResult icmp : fUm.inInterval(IcmpResult.class,mdtStart.toDate(),mdtEnd.toDate()))
	        {
				dbCleaner.add(IcmpResult.class.getName(), icmp.getId());
	        	ds.getData().add(buildData(icmp));
	        }
			mdtStart.add(range);
			mdtEnd.add(range);
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