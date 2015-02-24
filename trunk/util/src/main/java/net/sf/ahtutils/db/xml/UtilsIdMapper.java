package net.sf.ahtutils.db.xml;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.factory.xml.sync.XmlMapperFactory;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.sync.Mapper;
import net.sf.ahtutils.xml.sync.Mappings;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdMapper
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapperFactory.class);
	
	// Mapping of IDs and CODEs
	private Map<Class<?>,Map<Long,Long>>     map;
	private Map<Class<?>,Map<String,String>> mapCode;
	
	// Mapping of Objects
	private Map<Class<?>,Map<String,Object>> mapObjectByCode;
	private Map<Class<?>,Map<Long  ,Object>> mapObjectById;
	
	public UtilsIdMapper()
	{
		map     = new Hashtable<Class<?>,Map<Long,Long>>();
		mapCode = new Hashtable<Class<?>,Map<String,String>>();
	}
	
	public DataUpdate add(DataUpdate dataUpdate)
	{
		for(Mapper mapper : dataUpdate.getMapper())
		{
			add(mapper);
		}
		return dataUpdate;
	}
	
	public void add(Mappings mappings)
	{
		for(Mapper mapper : mappings.getMapper())
		{
			add(mapper);
		}
	}
	
	public void add(Mapper mapper)
	{
		try
		{
			Class<?> c = Class.forName(mapper.getClazz());
			if(mapper.isSetOldId() && mapper.isSetNewId())
			{
				if(!map.containsKey(c)){map.put(c, new Hashtable<Long,Long>());}
				map.get(c).put(mapper.getOldId(), mapper.getNewId());
			}
			else if(mapper.isSetOldCode() && mapper.isSetNewCode())
			{
				if(!mapCode.containsKey(c)){mapCode.put(c, new Hashtable<String,String>());}
				mapCode.get(c).put(mapper.getOldCode(), mapper.getNewCode());
			}
			else
			{
				logger.warn("No Mapping info!");
				JaxbUtil.warn(mapper);
				
			}
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public long getMappedId(Class<?> c,long oldId) throws UtilsConfigurationException
	{
		if(!map.containsKey(c)){throw new UtilsConfigurationException(this.getClass().getSimpleName()+" does contain information for "+c.getSimpleName()+".id="+oldId);}
		if(!map.get(c).containsKey(oldId)){throw new UtilsConfigurationException("Map for "+c.getSimpleName()+" does not have an entry for id="+oldId);}
		return map.get(c).get(oldId);
	}
	
	public String getMappedCode(Class<?> c,String oldCode) throws UtilsConfigurationException
	{
		if(!mapCode.containsKey(c)){throw new UtilsConfigurationException(this.getClass().getSimpleName()+" does contain information for "+c.getSimpleName()+".code="+oldCode);}
		if(!mapCode.get(c).containsKey(oldCode)){throw new UtilsConfigurationException("Map for "+c.getSimpleName()+" does not have an entry for oldCode="+oldCode);}
		return mapCode.get(c).get(oldCode);
	}
	
	public void debug()
	{
		if(map.size()>0)
		{
			logger.info(this.getClass().getSimpleName()+" with "+map.keySet().size()+" classes");
			for(Class<?> c : map.keySet())
			{
				logger.info("\t"+c.getSimpleName()+" "+map.get(c).keySet().size());
			}
		}
		else if(mapCode.size()>0)
		{
			logger.info(this.getClass().getSimpleName()+" with "+mapCode.keySet().size()+" classes");
			for(Class<?> c : mapCode.keySet())
			{
				logger.info("\t"+c.getSimpleName()+" "+mapCode.get(c).keySet().size());
			}
		}
	}
	
	public Boolean isMapped(Class<?> c,Object code)
	{
		if(!mapCode.containsKey(c)){return false;}
		if(!mapCode.get(c).containsKey(code)){return false;}
		return true;
	}
	
	public Boolean isObjectMapped(Class<?> c,Object object)
	{
		if(!mapObjectByCode.containsKey(c)                         || !mapObjectById.containsKey(c) )
		{
			return false;
			
		}
		if(!mapObjectByCode.get(c).containsKey(object.toString())  || !mapObjectById.get(c).containsKey(new Long(object.toString())) )
		{
			return false;
			
		}
		return true;
	}
	
	public Object getMappedObject(Class<?> c,Object object)
	{
		Object o = null;
		if(object.getClass().equals(java.lang.String.class))
		{
			o = mapObjectByCode.get(c).get(object.toString());
			if (o==null)
			{
				
			}
		}
		if(object.getClass().equals(java.lang.Long.class))
		{
			o = mapObjectById.get(c).get((Long) object);
		}
		logger.info(" returning " +o.toString());
		return o;
	}
	
	public void addObjectForCode(String code, Object o)
	{
		if (mapObjectByCode == null)
		{
			mapObjectByCode = new Hashtable<Class<?>,Map<String,Object>>();
		}
		if (mapObjectByCode.get(o.getClass()) == null)
		{
			Hashtable<String,Object> map = new Hashtable<String,Object>();
			mapObjectByCode.put(o.getClass(), map);
		}
		Map<String, Object> objectMap = mapObjectByCode.get(o.getClass());
		objectMap.put(code, o);
	}
	
	public void addObjectForId(Long id, Object o)
	{
		if (mapObjectById == null)
		{
			mapObjectById = new Hashtable<Class<?>,Map<Long,Object>>();
		}
		if (mapObjectById.get(o.getClass()) == null)
		{
			Hashtable<Long,Object> map = new Hashtable<Long,Object>();
			mapObjectById.put(o.getClass(), map);
		}
		Hashtable<Long, Object> objectMap = (Hashtable<Long, Object>) mapObjectById.get(o.getClass());
		objectMap.put(id, o);
		mapObjectById.put(o.getClass(), objectMap);
	}
}
