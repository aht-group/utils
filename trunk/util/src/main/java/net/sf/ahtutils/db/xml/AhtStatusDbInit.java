package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.controller.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtStatusDbInit
{
	final static Logger logger = LoggerFactory.getLogger(AhtStatusDbInit.class);
	
	private Map<String,Set<Long>> mDbAvailableStatus;
	private Set<Long> sDeleteLangs,sDeleteDescriptions;

	private EjbStatusFactory statusEjbFactory;
	private UtilsFacade fStatus;

	public AhtStatusDbInit()
	{
		mDbAvailableStatus = new Hashtable<String,Set<Long>>();
		sDeleteLangs = new HashSet<Long>();
		sDeleteDescriptions = new HashSet<Long>();
	}
	
	public void setStatusEjbFactory(EjbStatusFactory statusEjbFactory) {this.statusEjbFactory = statusEjbFactory;}
	public void setFacade(UtilsFacade fStatus){this.fStatus=fStatus;}
	
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
		
		Set<Long> dbStatus = new HashSet<Long>();
		for(UtilsStatus<UtilsLang,UtilsDescription> ejbStatus : availableStatus)
		{
			dbStatus.add(ejbStatus.getId());
		}
		logger.debug("Saved existing DB entries for "+key+": "+dbStatus.size());
		mDbAvailableStatus.put(key, dbStatus);
	}
	
	public UtilsStatus<UtilsLang,UtilsDescription> addVisible(UtilsStatus<UtilsLang,UtilsDescription> ejbStatus, Status status)
	{
		boolean visible=true;
		if(status.isSetVisible()){visible=status.isVisible();}
		ejbStatus.setVisible(visible);
		return ejbStatus;
	}
	
	public UtilsStatus<UtilsLang,UtilsDescription> addLangsAndDescriptions(UtilsStatus ejbStatus, Status status) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		UtilsStatus ejbUpdateInfo = (UtilsStatus)statusEjbFactory.create(status);
		ejbStatus.setName(ejbUpdateInfo.getName());
		ejbStatus.setDescription(ejbUpdateInfo.getDescription());
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
	
	public <S extends UtilsStatus<L,D>,L extends UtilsLang, D extends UtilsDescription> void deleteUnusedStatus(Class<S> cStatus, Class<L> cLang, Class<D> cDescription)
	{
		logger.debug("Deleing unused childs of Status: "+cLang.getName()+":"+sDeleteLangs.size());
		for(long id : sDeleteLangs)
		{
			try
			{
				logger.trace("Deleting "+cLang.getName()+": "+id);
				L lang = fStatus.find(cLang, id);
				fStatus.rm(lang);
			}
			catch (UtilsNotFoundException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
		for(long id : sDeleteDescriptions)
		{
			try
			{
				logger.info("Deleting "+cDescription.getName()+": "+id);
				D d = fStatus.find(cDescription, id);
				fStatus.rm(d);
			}
			catch (UtilsNotFoundException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
		}
		
		 for(String group : mDbAvailableStatus.keySet())
		 {
			 Set<Long> sIds = mDbAvailableStatus.get(group);
			 logger.trace("Deleting Group "+group+": "+sIds.size());
			 for(long id : sIds)
			 {
				 try
				 {
					 logger.trace("Deleting status: "+id);
					 S status = fStatus.find(cStatus, id);
					 fStatus.rm(status);
				 }
				 catch (UtilsIntegrityException e) {logger.error("Error with following ID:"+id,e);}
				 catch (UtilsNotFoundException e)  {logger.error("Error with following ID:"+id,e);}
			 }
		 }
	}
	
	public <S extends UtilsStatus<L,D>,L extends UtilsLang, D extends UtilsDescription> void iuStatus(List<Status> list, Class<S> cStatus, Class<L> cLang)
	{
		if(fStatus==null){logger.warn("No Handler available");}
		else {logger.info("Updating "+cStatus.getSimpleName()+" with "+list.size()+" entries");}
		if(fStatus!=null){iuStatusEJB(list, cStatus, cLang);}

	}
	
	private <S extends UtilsStatus<L,D>,L extends UtilsLang, D extends UtilsDescription> void iuStatusEJB(List<Status> list, Class<S> cStatus, Class<L> cLang)
	{
		for(Status status : list)
		{
			try
			{
				logger.debug("Processing "+status.getGroup()+" with "+status.getCode());
				S ejbStatus;
				if(!isGroupInMap(status.getGroup()))
				{
					List<UtilsStatus> l = new ArrayList<UtilsStatus>();
					for(S s : fStatus.all(cStatus)){l.add(s);}
					savePreviousDbEntries(status.getGroup(), l);
					logger.trace("Delete Pool: "+mDbAvailableStatus.get(status.getGroup()).size());
				}
				try
				{
					ejbStatus = fStatus.fByCode(cStatus,status.getCode());
					ejbStatus = (S)removeData(ejbStatus);
					ejbStatus = fStatus.update(ejbStatus);
					ejbStatus = fStatus.find(cStatus, ejbStatus.getId());
					removeStatusFromDelete(status.getGroup(), ejbStatus.getId());
					logger.trace("Now in Pool: "+mDbAvailableStatus.get(status.getGroup()).size());
					logger.trace("Found: "+ejbStatus);
				}
				catch (UtilsNotFoundException e)
				{
					ejbStatus = cStatus.newInstance();
					ejbStatus.setCode(status.getCode());
					ejbStatus = fStatus.persist(ejbStatus);
					logger.trace("Added: "+ejbStatus);
				}
				
				try
				{
					addLangsAndDescriptions(ejbStatus,status);
					if(status.isSetImage()){ejbStatus.setImage(status.getImage());}
				}
				catch (InstantiationException e) {logger.error("",e);}
				catch (IllegalAccessException e) {logger.error("",e);}
				catch (UtilsIntegrityException e) {logger.error("",e);}
		        
				if(status.isSetPosition()){ejbStatus.setPosition(status.getPosition());}
		        else{ejbStatus.setPosition(0);}
				ejbStatus = fStatus.update(ejbStatus);
			}
			catch (UtilsContraintViolationException e){logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
	}
	
	private UtilsStatus removeData(UtilsStatus ejbStatus)
	{
		Map<String,UtilsLang> dbLangMap = ejbStatus.getName();
		ejbStatus.setName(null);
		for(UtilsLang lang : dbLangMap.values()){sDeleteLangs.add(lang.getId());}
		
		if(ejbStatus.getDescription()!=null)
		{
			Map<String,UtilsDescription> dbDescrMap = ejbStatus.getDescription();
			ejbStatus.setDescription(null);
			for(UtilsDescription d : dbDescrMap.values()){sDeleteDescriptions.add(d.getId());}
		}
		
		return ejbStatus;
	}
}