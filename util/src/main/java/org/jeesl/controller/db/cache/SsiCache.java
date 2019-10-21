package org.jeesl.controller.db.cache;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiLink;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SsiCache <MAPPING extends JeeslIoSsiMapping<?,?>,
							DATA extends JeeslIoSsiData<MAPPING,LINK>,
							LINK extends UtilsStatus<LINK,?,?>,
							T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(SsiCache.class);

	private final JeeslIoSsiFacade<?,?,?,MAPPING,?,DATA,LINK,?> fSsi;
	private final Class<T> cT;
	private final MAPPING mapping;
	
	private final Map<String,T> map;
	
	public SsiCache(IoSsiFactoryBuilder<?,?,?,MAPPING,?,?,?,?> fbSsi,
						JeeslIoSsiFacade<?,?,?,MAPPING,?,DATA,LINK,?> fSsi,
						Class<T> cT,
						MAPPING mapping
						)
	{
		this.fSsi=fSsi;
		this.mapping=mapping;
		this.cT=cT;
		map = new HashMap<>();

	}
	
	public T ejb(String code) throws UtilsNotFoundException
	{
		if(!map.containsKey(code))
		{
			DATA data = fSsi.fIoSsiData(mapping,code);
			if(!data.getLink().getCode().equals(JeeslIoSsiLink.Code.linked.toString())) {throw new UtilsNotFoundException("Not Linked");}
			if(data.getLocalId()==null) {throw new UtilsNotFoundException("No LocalId");}
			T t = fSsi.find(cT,data.getLocalId());
			map.put(code,t);
		}
		return map.get(code);
	}
}