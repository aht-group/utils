package org.jeesl.web.rest.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataPointFactory;
import org.jeesl.factory.json.module.ts.JsonTsDataFactory;
import org.jeesl.factory.json.module.ts.JsonTsMultiPointFactory;
import org.jeesl.factory.json.module.ts.JsonTsPointFactory;
import org.jeesl.factory.json.module.ts.JsonTsScopeFactory;
import org.jeesl.factory.json.system.status.JsonIntervalFactory;
import org.jeesl.factory.json.system.status.JsonWorkspaceFactory;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsInterval;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScopeType;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsCron;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.model.json.module.ts.JsonTsData;
import org.jeesl.model.json.module.ts.JsonTsSeries;
import org.jeesl.util.query.json.JsonStatusQueryProvider;
import org.jeesl.util.query.json.JsonTsQueryProvider;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsRestHandler <L extends JeeslLang, D extends JeeslDescription,
							CAT extends JeeslStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
							ST extends JeeslTsScopeType<L,D,ST,?>,
							UNIT extends JeeslStatus<UNIT,L,D>,
							MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
							TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
							TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
							SOURCE extends EjbWithLangDescription<L,D>, 
							BRIDGE extends JeeslTsBridge<EC>,
							EC extends JeeslTsEntityClass<L,D,CAT>,
							INT extends JeeslTsInterval<L,D,INT,?>,
							STAT extends JeeslTsStatistic<L,D,STAT,?>,
							DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
							POINT extends JeeslTsDataPoint<DATA,MP>,
							SAMPLE extends JeeslTsSample, 
							USER extends EjbWithId, 
							WS extends JeeslStatus<WS,L,D>,
							QAF extends JeeslStatus<QAF,L,D>,
							CRON extends JeeslTsCron<SCOPE,INT,STAT>>
					extends AbstractJeeslRestService<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(TsRestHandler.class);
	
	private final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs;
	private final JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs;
	
	private final EjbTsDataPointFactory<MP,DATA,POINT> efPoint;
	
	private JsonTsScopeFactory<L,D,SCOPE,ST> jfScope;
	private final JsonTsDataFactory<DATA> jfData;
	private JsonTsPointFactory<MP,POINT> jfPoint;
	private JsonIntervalFactory<L,D,INT> jfInterval;
	private JsonWorkspaceFactory<L,D,WS> jfWorkspace;
	
	public TsRestHandler(TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs,
							JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super(fTs,fbTs.getClassL(),fbTs.getClassD());
		this.fbTs=fbTs;
		this.fTs=fTs;
		
		efPoint = fbTs.ejbDataPoint();
		
		jfScope = new JsonTsScopeFactory<>(JsonTsQueryProvider.scope());
		jfData = new JsonTsDataFactory<>(JsonTsQueryProvider.data());
		jfPoint = new JsonTsPointFactory<>(JsonTsQueryProvider.point());
		jfInterval = new JsonIntervalFactory<>(JsonStatusQueryProvider.intervalCode());
		jfWorkspace = new JsonWorkspaceFactory<>(JsonStatusQueryProvider.workspaceCode());
	}
	
	public JsonTsSeries jsonTs(WS workspace, SCOPE scope, INT interval, BRIDGE bridge)
	{
		
		DateTime dtNow = new DateTime();
		JsonTsSeries json = new JsonTsSeries();
		
		try
		{
			TS ts = fTs.fTimeSeries(scope,interval, bridge);
			json.setScope(jfScope.build(ts.getScope()));
			json.setWorkspace(jfWorkspace.build(workspace));
			json.setInterval(jfInterval.build(interval));
			switch(JeeslTsScopeType.Code.valueOf(scope.getType().getCode()))
			{
				case ts: break; 
				case mp: json.setDatas(multiPoints(workspace,ts,dtNow.minusHours(1).toDate(),dtNow.toDate())); break;
			}	
		}
		catch (JeeslNotFoundException e) {logger.warn(e.getMessage());}
		
		return json;
	}
	
	private List<JsonTsData> multiPoints(WS workspace,TS ts,Date from, Date to)
	{
		List<JsonTsData> list = new ArrayList<>();
		List<POINT> points = fTs.fPoints(workspace,ts,from,to);
		Map<DATA,List<POINT>> map = efPoint.toMapData(points);
		List<DATA> datas = new ArrayList<>(map.keySet());
		for(DATA d : datas)
		{
			JsonTsData jData = jfData.build(d);
			jData.setPoints(new ArrayList<>());
			for(POINT p : map.get(d))
			{
				jData.getPoints().add(jfPoint.build(p));
			}
			
			list.add(jData);
			
		}
		return list;
	}
}