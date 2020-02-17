package org.jeesl.controller.processor.module.ts.system.health;

import java.util.Date;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.bean.system.JeeslSessionRegistryBean;
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
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.joda.time.DateTime;
import org.metachart.xml.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsSessionProcessor<SYSTEM extends JeeslIoSsiSystem,
									USER extends JeeslUser<?>,
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
	final static Logger logger = LoggerFactory.getLogger(TsSessionProcessor.class);
	
	public TsSessionProcessor(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fbTs,
									JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fTs)
	{
		super(fbTs,fTs);
	}
		
	public void update(SYSTEM system, JeeslSessionRegistryBean<USER> bSession)
	{
		try
		{
			DateTime dt = new DateTime(new Date());
			Date date = dt.withMillisOfSecond(0).withSecondOfMinute(0).toDate();
			
			TS ts = fcTs(system);
			TRANSACTION transaction = fTs.save(fbTs.transaction().build(null,null));
			
			DATA data = efData.build(ws, ts, transaction, date, null);
			data = fTs.save(data);
					
			for(MP mp : fTs.allForParent(fbTs.getClassMp(), scope))
			{
				if(mp.getCode().equals("active"))
				{
					POINT dp =  efPoint.build(data, mp, Integer.valueOf(bSession.activeSessions()).doubleValue());
					fTs.save(dp);
				}
				else if(mp.getCode().equals("authenticated"))
				{
					POINT dp =  efPoint.build(data, mp, Integer.valueOf(bSession.authenticatedSessions()).doubleValue());
					fTs.save(dp);
				}
			}
			
		}
		catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
	}
	
	public Chart build(String localeCode, Date begin, Date end, SYSTEM system) 
	{
		Chart chart = mfTs.build(localeCode);
		chart.setSubtitle(null);
		try
		{
			chart.setDs(mfTs.multiPoint(localeCode,system,begin,end));
		}
		catch (JeeslNotFoundException e) {}
		return chart;
	}
}