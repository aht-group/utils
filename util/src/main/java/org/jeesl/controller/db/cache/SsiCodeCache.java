package org.jeesl.controller.db.cache;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class SsiCodeCache <MAPPING extends JeeslIoSsiMapping<?,ENTITY>,
							ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
							ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>,
							T extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(SsiCodeCache.class);

	private final Class<T> cT;
	
	private final Map<String,T> map;
	
	public SsiCodeCache(IoSsiFactoryBuilder<?,?,?,MAPPING,ATTRIBUTE,?,?,ENTITY> fbSsi,
						JeeslIoSsiFacade<?,?,?,MAPPING,ATTRIBUTE,?,?,ENTITY> fSsi,
						Class<T> cT)
	{
		this.cT=cT;
		map = new HashMap<>();
		
		for(ATTRIBUTE a : fSsi.all(fbSsi.getClassAttribute()))
		{
			if(a.getEntity().getCode().equals(cT.getName()))
			{
				try
				{
					T t = fSsi.fByCode(cT, a.getLocalCode());
					map.put(a.getRemoteCode(),t);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}
	}
	
	public T ejb(String code) throws UtilsNotFoundException
	{
		if(!map.containsKey(code)) {throw new UtilsNotFoundException();}
		return map.get(code);
	}
}