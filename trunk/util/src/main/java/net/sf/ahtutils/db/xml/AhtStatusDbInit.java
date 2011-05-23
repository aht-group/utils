package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AhtStatusDbInit
{
	static Log logger = LogFactory.getLog(AhtStatusDbInit.class);
	
	protected Map<String,Set<Integer>> mDbAvailableStatus;
	protected Set<Long> sDeleteLangs;
	
	public AhtStatusDbInit()
	{
		mDbAvailableStatus = new Hashtable<String,Set<Integer>>();
		sDeleteLangs = new HashSet<Long>();
	}
	
	public List<Status> getStatus(String xmlFile) throws FileNotFoundException
	{
		Aht aht = (Aht)JaxbUtil.loadJAXB(xmlFile, Aht.class);
		logger.debug("Loaded "+aht.getStatus().size()+" Elements from "+xmlFile);
		return aht.getStatus();
	}
	
	public boolean isGroupInMap(String group)
	{
		return mDbAvailableStatus.containsKey(group);
	}
	
	public void savePreviousDbEntries(String key, List<UtilsStatus<UtilsLang>> availableStatus)
	{
		logger.trace("Adding available DB entries: "+key);
		Set<Integer> dbStatus = new HashSet<Integer>();
		for(UtilsStatus<UtilsLang> ejbStatus : availableStatus)
		{
			dbStatus.add(ejbStatus.getId());
		}
		mDbAvailableStatus.put(key, dbStatus);
	}
	
	public UtilsStatus<UtilsLang> removeData(UtilsStatus<UtilsLang> ejbStatus)
	{
		Map<String,UtilsLang> dbLangMap = ejbStatus.getName();
		ejbStatus.setName(null);		
		for(UtilsLang lang : dbLangMap.values()){sDeleteLangs.add(lang.getId());}
		return ejbStatus;
	}
	
	public UtilsStatus<UtilsLang> addVisible(UtilsStatus<UtilsLang> ejbStatus, Status status)
	{
		boolean visible=true;
		if(status.isSetVisible()){visible=status.isVisible();}
		ejbStatus.setVisible(visible);
		return ejbStatus;
	}
	
	public UtilsStatus<UtilsLang> addLangs(UtilsStatus<UtilsLang> ejbStatus, Status status)
	{
		Map<String,UtilsLang> langMap = new Hashtable<String,UtilsLang>();
//		if(status.isSetLangs()){langMap.putAll(AhtUtilEjbFactory.getLangMap(status.getLangs()));}
		ejbStatus.setName(langMap);
		return ejbStatus;
	}
	
	public void removeStatusFromDelete(String key, int id)
	{
		mDbAvailableStatus.get(key).remove(id);
	}
	
	public List<Integer> getDeleteStatusIds()
	{
		List<Integer> result = new ArrayList<Integer>();
		for(String group : mDbAvailableStatus.keySet())
		{
			Set<Integer> delIds = mDbAvailableStatus.get(group);
			Iterator<Integer> iterator = delIds.iterator();
			while(iterator.hasNext())
			{
				result.add(iterator.next());
			}
		}
		return result;
	}
	
/*	public void deleteUnusedStatus(AhtUtilFacade fUtil) throws AhtUtilIntegrityException
	{
		List<Integer> delStatus = getDeleteStatusIds();
		logger.debug("Deleting langs: "+sDeleteLangs.size());
		logger.debug("Deleting status: "+delStatus.size());
		
		Iterator<Long> iteratorLang = sDeleteLangs.iterator();
		while(iteratorLang.hasNext())
		{
			Long id = iteratorLang.next();
			logger.warn("NYI delete id="+id);
//			Object o = fUtil.findObject(AhtLang.class ,id);
//			fUtil.deleteObject(o);
		}
		
		for(Integer id : delStatus)
		{
			logger.trace("Delete status with id="+id);
			logger.warn("NYI delete id="+id);
//			Object o = fUtil.findObject(AhtStatus.class ,id);
//			fUtil.deleteObject(o);
		}
	}
	*/
}