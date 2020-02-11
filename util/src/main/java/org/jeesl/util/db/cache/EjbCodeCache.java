package org.jeesl.util.db.cache;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCodeCache <T extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCodeCache.class);

	private final JeeslFacade fUtils;
	private final Class<T> c;
	
	private final Map<String,T> map;
	
	public EjbCodeCache(JeeslFacade fUtils, Class<T> c)
	{
		this.fUtils=fUtils;
		this.c=c;
		map = new HashMap<>();
	}
	
	public <E extends Enum<E>> T ejb(E code) {return ejb(code.toString());}
	public T ejb(String code)
	{
		if(!map.containsKey(code))
		{
			try
			{
				map.put(code, fUtils.fByCode(c,code));
			}
			catch (JeeslNotFoundException e) {e.printStackTrace();}
		}
		return map.get(code);
	}
}