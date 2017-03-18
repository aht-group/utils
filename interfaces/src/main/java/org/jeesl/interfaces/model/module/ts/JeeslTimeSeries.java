package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTimeSeries <L extends UtilsLang,
									D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
									BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
									EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
									INT extends UtilsStatus<INT,L,D>,
									DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
									WS extends UtilsStatus<WS,L,D>,
									QAF extends UtilsStatus<QAF,L,D>>
		extends EjbWithId
{
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getInterval();
	void setInterval(INT interval);
	
	BRIDGE getBridge();
	void setBridge(BRIDGE bridge);
}