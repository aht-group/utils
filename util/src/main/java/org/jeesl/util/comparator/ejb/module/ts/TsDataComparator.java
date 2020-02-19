package org.jeesl.util.comparator.ejb.module.ts;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsDataComparator <	TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
								DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
								SAMPLE extends JeeslTsSample, 
								WS extends JeeslStatus<WS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TsDataComparator.class);
	
	public enum Type {date};
	
	public TsDataComparator()
	{
		
	}
	
	public Comparator<DATA> factory(Type type)
	{
		Comparator<DATA> c = null;
		TsDataComparator<TS,TRANSACTION,DATA,SAMPLE,WS> factory = new TsDataComparator<TS,TRANSACTION,DATA,SAMPLE,WS>();
		switch (type)
		{
			case date: c = factory.new DateComparator();break;
		}
		return c;
	}
	
	private class DateComparator implements Comparator<DATA>
	{
		public int compare(DATA a, DATA b)
		{
			CompareToBuilder ctb = new CompareToBuilder();
			ctb.append(a.getRecord().getTime(), b.getRecord().getTime());
			return ctb.toComparison();
		}
	}
}
