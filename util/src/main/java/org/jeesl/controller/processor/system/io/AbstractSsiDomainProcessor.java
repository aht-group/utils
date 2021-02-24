package org.jeesl.controller.processor.system.io;

import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.ssi.IoSsiDataFactoryBuilder;
import org.jeesl.factory.ejb.io.ssi.data.EjbIoSsiDataFactory;
import org.jeesl.interfaces.controller.processor.SsiMappingProcessor;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiCredential;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiCleaning;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiLink;
import org.jeesl.interfaces.model.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.util.db.cache.EjbCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSsiDomainProcessor<L extends JeeslLang,D extends JeeslDescription,
										SYSTEM extends JeeslIoSsiSystem<L,D>,
										CRED extends JeeslIoSsiCredential<SYSTEM>,
										MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
										ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
										DATA extends JeeslIoSsiData<MAPPING,LINK>,
										LINK extends JeeslIoSsiLink<L,D,LINK,?>,
										ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>,
										CLEANING extends JeeslIoSsiCleaning<L,D,CLEANING,?>
										>
						implements SsiMappingProcessor<MAPPING,DATA>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractSsiDomainProcessor.class);
	
	protected final IoSsiDataFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING> fbSsi;
	protected final JeeslIoSsiFacade<L,D,SYSTEM,CRED,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING,?> fSsi;
	
	protected final EjbIoSsiDataFactory<MAPPING,DATA,LINK> efData;
	
	protected final EjbCodeCache<LINK> cacheLink; public EjbCodeCache<LINK> getCacheLink() {return cacheLink;}
	
	protected LINK linkUnlinked; public LINK getLinkUnlinked() {return linkUnlinked;}
	protected LINK linkPrecondition; public LINK getLinkPrecondition() {return linkPrecondition;}
	protected LINK linkPossible; public LINK getLinkPossible() {return linkPossible;}
	protected LINK linkLinked; public LINK getLinkLinked() {return linkLinked;}
	protected LINK linkIgnore; public LINK getLinkIgnore() {return linkIgnore;}
	protected LINK linkUpdate;
	
	protected MAPPING mapping; @Override public MAPPING getMapping() {return mapping;}

	public AbstractSsiDomainProcessor(IoSsiDataFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING> fbSsi,
									JeeslIoSsiFacade<L,D,SYSTEM,CRED,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING,?> fSsi)
	{
		this.fSsi=fSsi;
		this.fbSsi=fbSsi;
		
		cacheLink = new EjbCodeCache<>(fbSsi.getClassLink(),fSsi);
		
		efData = fbSsi.ejbData();
		
		try {initLinks();}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}
	
	private void initLinks() throws JeeslNotFoundException
	{		
		linkUnlinked = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.unlinked);
		linkPrecondition = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.precondition);
		linkPossible = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.possible);
		linkLinked = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.linked);
		linkIgnore = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.ignore);
		linkUpdate = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.update);
	}
	
	@Override public void ignoreData(List<DATA> datas)
	{
		for(DATA d : datas)
		{
			if(!d.getLink().getCode().equals(JeeslIoSsiLink.Code.linked.toString()))
			{
				ignoreData(d);
			}
		}
	}
	private void ignoreData(DATA data)
	{
		logger.info("Ignoring "+data.toString());
		try
		{
			data.setLocalId(null);
			data.setLink(linkIgnore);
			fSsi.save(data);
		}
		catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
	}
	
	@Override public void unignoreData(List<DATA> datas)
	{
		for(DATA d : datas)
		{
			if(d.getLink().getCode().equals(JeeslIoSsiLink.Code.ignore.toString()))
			{
				unignoreData(d);
			}
		}
	}
	private void unignoreData(DATA data)
	{
		logger.info("UnIgnoring "+data.toString());
		try
		{
			data.setLocalId(null);
			data.setLink(linkUnlinked);
			fSsi.save(data);
		}
		catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
	}
}