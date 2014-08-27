package net.sf.ahtutils.db.xml;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.xml.sync.XmlMapperFactory;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.sync.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdMapper
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapperFactory.class);
	
	private Map<Class<?>,Map<Long,Long>> map;
	
	public UtilsIdMapper()
	{
		map = new Hashtable<Class<?>,Map<Long,Long>>();
	}
	
	public DataUpdate add(DataUpdate dataUpdate)
	{
		for(Mapper mapper : dataUpdate.getMapper())
		{
			add(mapper);
		}
		return dataUpdate;
	}
	
	public void add(Mapper mapper)
	{
		try
		{
			Class<?> c = Class.forName(mapper.getClazz());
			if(!map.containsKey(c)){map.put(c, new Hashtable<Long,Long>());}
			map.get(c).put(mapper.getOldId(), mapper.getNewId());
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public long getMappedId(Class<?> c,long oldId) throws UtilsConfigurationException
	{
		if(!map.containsKey(c)){throw new UtilsConfigurationException(this.getClass().getSimpleName()+" does contain information for "+c.getSimpleName()+".id="+oldId);}
		if(!map.get(c).containsKey(oldId)){throw new UtilsConfigurationException("Map for "+c.getSimpleName()+" does not have an entry for "+oldId);}
		return map.get(c).get(oldId);
	}
	
	public void debug()
	{
		logger.info(this.getClass().getSimpleName()+" with "+map.keySet().size()+" classes");
		for(Class<?> c : map.keySet())
		{
			logger.info("\t"+c.getSimpleName()+" "+map.get(c).keySet().size());
		}
	}
}
