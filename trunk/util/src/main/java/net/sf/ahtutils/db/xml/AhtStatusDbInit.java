package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.factory.UtilsStatusEjbFactory;
import net.sf.ahtutils.controller.interfaces.AhtUtilsStatusInterface;
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
	
	private Map<String,Set<Long>> mDbAvailableStatus;
	private Set<Long> sDeleteLangs;

	private UtilsStatusEjbFactory statusEjbFactory;

	public AhtStatusDbInit()
	{
		mDbAvailableStatus = new Hashtable<String,Set<Long>>();
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
		Set<Long> dbStatus = new HashSet<Long>();
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
	
	public UtilsStatus<UtilsLang> addLangs(UtilsStatus ejbStatus, Status status) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		UtilsStatus ejbUpdateInfo = (UtilsStatus)statusEjbFactory.create(status);
		ejbStatus.setName(ejbUpdateInfo.getName());
		return ejbStatus;
	}
	
	public void removeStatusFromDelete(String key, long id)
	{
		mDbAvailableStatus.get(key).remove(id);
	}
	
	public List<Long> getDeleteStatusIds()
	{
		List<Long> result = new ArrayList<Long>();
		for(String group : mDbAvailableStatus.keySet())
		{
			Set<Long> delIds = mDbAvailableStatus.get(group);
			Iterator<Long> iterator = delIds.iterator();
			while(iterator.hasNext())
			{
				result.add(iterator.next());
			}
		}
		return result;
	}
	
	public <S extends UtilsStatus<L>,L extends UtilsLang> void deleteUnusedStatus(AhtUtilsStatusInterface fStatus, Class<S> cStatus, Class<L> cLang)
	{
		logger.debug("Deleing unused Status/Langs");
		for(long id : getsDeleteLangs())
		{
			try
			{
				logger.trace("Deleting lang: "+id);
				L lang = fStatus.fAhtUtilsEntity(cLang, id);
				fStatus.rmAhtUtilsEntity(lang);
			}
			catch (AhtUtilsContraintViolationException e) {logger.error(e);}
			catch (AhtUtilsNotFoundException e) {logger.error(e);}
		}
		
		 Map<String, Set<Long>> mapUnusedStatus = getmDbAvailableStatus();
		 for(String group : mapUnusedStatus.keySet())
		 {
			 Set<Long> sIds = mapUnusedStatus.get(group);
			 logger.trace("Deleting Group "+group+": "+sIds.size());
			 for(long id : sIds)
			 {
				 try
				 {
					 logger.trace("Deleting status: "+id);
						S status = fStatus.fAhtUtilsEntity(cStatus, id);
					 fStatus.rmAhtUtilsEntity(status);
				 }
				 catch (AhtUtilsContraintViolationException e) {logger.error(e);}
				 catch (AhtUtilsNotFoundException e) {logger.error(e);}
			 }
		 }
	}
	
	public void setStatusEjbFactory(UtilsStatusEjbFactory statusEjbFactory) {this.statusEjbFactory = statusEjbFactory;}
	public Set<Long> getsDeleteLangs() {return sDeleteLangs;}
	public Map<String, Set<Long>> getmDbAvailableStatus() {return mDbAvailableStatus;}
}