package org.jeesl.controller.processor.module.ts.system.security;

import java.util.Date;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
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
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityPasswordRating;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TsPasswordRatinglProcessor<SYSTEM extends JeeslIoSsiSystem,
									RATING extends JeeslSecurityPasswordRating<?,?,?,?>,
									SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
									MP extends JeeslTsMultiPoint<?,?,SCOPE,?>,
									TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
									TRANSACTION extends JeeslTsTransaction<?,DATA,?,?>,
									BRIDGE extends JeeslTsBridge<EC>,
									EC extends JeeslTsEntityClass<?,?,?>,
									INT extends UtilsStatus<INT,?,?>,
									STAT extends JeeslTsStatistic<?,?,STAT,?>,
									DATA extends JeeslTsData<TS,TRANSACTION,?,WS>,
									POINT extends JeeslTsDataPoint<DATA,MP>,
									WS extends UtilsStatus<WS,?,?>>
	extends AbstractTimeSeriesProcessor<SCOPE,MP,TS,TRANSACTION,BRIDGE,EC,INT,STAT,DATA,POINT,WS>
{
	final static Logger logger = LoggerFactory.getLogger(TsPasswordRatinglProcessor.class);
	
	public TsPasswordRatinglProcessor(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?> fbTs,
									JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?> fTs)
	{
		super(fbTs,fTs);
	}
	
	
	
	public void update(SYSTEM system, Json1Tuples<RATING> tuples)
	{
		try
		{
			DateTime dt = new DateTime(new Date());
			Date date = dt.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0).toDate();
			
			TS ts = fcTs(system);
			TRANSACTION transaction = fTs.save(fbTs.transaction().build(null,null));
			
			DATA data = efData.build(ws, ts, transaction, date, null);
			data = fTs.save(data);
					
			for(MP mp : fTs.allForParent(fbTs.getClassMp(), scope))
			{
				for(Json1Tuple<RATING> t : tuples.getTuples())
				{
					
					if(t.getEjb().getCode().equals(mp.getCode()))
					{
						if(t.getCount()!=null)
						{
							POINT dp =  efPoint.build(data,mp,t.getCount().doubleValue());
							dp = fTs.save(dp);
//							logger.info(dp.getId()+" "+t.getEjb().getCode()+" "+t.getCount());
						}
						
					}
				}
			}
			
		}
		catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
	}
}