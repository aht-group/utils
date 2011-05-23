package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilIntegrityException;
import net.sf.ahtutils.controller.factory.UtilsStatusEjbFactory;
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
	
	private Map<String,Set<Integer>> mDbAvailableStatus;
	public Map<String, Set<Integer>> getmDbAvailableStatus() {
		return mDbAvailableStatus;
	}
	private Set<Long> sDeleteLangs;

	private UtilsStatusEjbFactory statusEjbFactory;

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
	
	public void savePreviousDbEntries(String key, List<UtilsStatus> availableStatus)
	{
		logger.trace("Adding available DB entries: "+key);
		Set<Integer> dbStatus = new HashSet<Integer>();
		for(UtilsStatus<UtilsLang> ejbStatus : availableStatus)
		{
			dbStatus.add(ejbStatus.getId());
		}
		mDbAvailableStatus.put(key, dbStatus);
	}
	
	public UtilsStatus removeData(UtilsStatus ejbStatus)
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
	
	public UtilsStatus<UtilsLang> addLangs(UtilsStatus ejbStatus, Status status) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
	{
		UtilsStatus ejbUpdateInfo = (UtilsStatus)statusEjbFactory.create(status);
		ejbStatus.setName(ejbUpdateInfo.getName());
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
	
	public void setStatusEjbFactory(UtilsStatusEjbFactory statusEjbFactory) {this.statusEjbFactory = statusEjbFactory;}
	public Set<Long> getsDeleteLangs() {return sDeleteLangs;}
}