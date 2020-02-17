package org.jeesl.controller.processor.module.ts.system.io.db;

import java.util.Date;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.metachart.xml.chart.Chart;
import org.metachart.xml.chart.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsDbPoolProcessor<SYSTEM extends JeeslIoSsiSystem,
									SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
									MP extends JeeslTsMultiPoint<?,?,SCOPE,?>,
									TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
									BRIDGE extends JeeslTsBridge<EC>,
									EC extends JeeslTsEntityClass<?,?,?>,
									INT extends JeeslStatus<INT,?,?>,
									STAT extends JeeslTsStatistic<?,?,STAT,?>,
									DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
									POINT extends JeeslTsDataPoint<DATA,MP>,
									WS extends JeeslStatus<WS,?,?>>
	extends AbstractTimeSeriesProcessor<SCOPE,MP,TS,TRANSACTION,BRIDGE,EC,INT,STAT,DATA,POINT,WS>
{
	final static Logger logger = LoggerFactory.getLogger(TsDbPoolProcessor.class);
	
	
	
	public TsDbPoolProcessor(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fbTs,
									JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fTs)
	{
		super(fbTs,fTs);
		
		
	}
	
	public Chart build(String localeCode, Date begin, Date end, SYSTEM system) 
	{
		Chart chart = mfTs.build(localeCode);
		
		try
		{
			Ds ds = mfTs.multiPoint(localeCode,system,begin,end);
			
			if(!ds.getDs().isEmpty())
			{
				chart.setDs(ds);
			}
		}
		catch (JeeslNotFoundException e) {}
		return chart;
	}
	
}