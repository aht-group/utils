package org.jeesl.controller.processor.module.ts.system.io.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.processor.module.ts.AbstractTimeSeriesProcessor;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
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
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TsDbCountProcessor<RE extends JeeslRevisionEntity<?,?,?,?,?,?>,
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
	final static Logger logger = LoggerFactory.getLogger(TsDbCountProcessor.class);
	
	private final IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?,?> fbRevision;
		
	private final JeeslIoDbFacade<?,?,?,?,?,?,?> fDb;
	
	public TsDbCountProcessor(IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?,?> fbRevision,
									TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?> fbTs,
									JeeslIoDbFacade<?,?,?,?,?,?,?> fDb,
									JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,TRANSACTION,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?> fTs)
	{
		super(fbTs,fTs);
		this.fbRevision=fbRevision;
		this.fDb=fDb;
	}
	
	public List<RE> findActive()
	{
		List<RE> listTs = new ArrayList<RE>();
		for(RE entity : fDb.all(fbRevision.getClassEntity()))
		{
			try
			{
				Class<?> c = Class.forName(entity.getCode());
				
				boolean active = BooleanComparator.active(entity.getTimeseries());
				boolean table = c.getAnnotation(Table.class)!=null;
				
				if(active && table){listTs.add(entity);}
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
		}
		return listTs;
	}
	
	public void count() throws IllegalStateException
	{
		if(!isInitialized()) {throw new IllegalStateException(this.getClass().getSimpleName()+" is not fully initialized");}
		List<RE> listTs = findActive();
		
		if(!listTs.isEmpty())
		{
			try
			{
				TRANSACTION transaction = fbTs.transaction().build(null,null);
				transaction = fTs.save(transaction);
				Date date = (new DateTime(new Date())).withTimeAtStartOfDay().toDate();
				for(RE entity : listTs)
				{
					try
					{
						Class<?> c = Class.forName(entity.getCode());
						count(transaction,date,entity,c);
					}
					catch (ClassNotFoundException e) {e.printStackTrace();}
					catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
				}
			}
			catch (UtilsConstraintViolationException | UtilsLockingException e) {e.printStackTrace();}
		}
	}
	
	private void count(TRANSACTION transaction, Date date, RE entity, Class<?> c) throws UtilsConstraintViolationException, UtilsLockingException
	{
		BRIDGE bridge = fTs.fcBridge(fbTs.getClassBridge(),ec,entity);
		TS ts = fTs.fcTimeSeries(scope,interval,bridge);
		Long count = fDb.countEstimate(c);
		DATA data = efData.build(ws, ts, transaction, date, count.doubleValue());
		data = fTs.save(data);
		logger.info(entity.getCode()+" "+c.getAnnotation(Table.class).name()+" "+count);
	}
}