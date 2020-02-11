package org.jeesl.controller.processor.system.io;

import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.data.EjbIoSsiDataFactory;
import org.jeesl.interfaces.controller.processor.SsiMappingProcessor;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiCleaning;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiLink;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSsiDomainProcessor<L extends JeeslLang,D extends JeeslDescription,
										SYSTEM extends JeeslIoSsiSystem,
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
	
	protected final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING> fbSsi;
	protected final JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi;
	
	protected final EjbIoSsiDataFactory<MAPPING,DATA,LINK> efData;
	
	protected LINK linkUnlinked; public LINK getLinkUnlinked() {return linkUnlinked;}
	protected LINK linkPrecondition; public LINK getLinkPrecondition() {return linkPrecondition;}
	protected LINK linkPossible; public LINK getLinkPossible() {return linkPossible;}
	protected LINK linkLinked; public LINK getLinkLinked() {return linkLinked;}
	protected LINK linkIgnore; public LINK getLinkIgnore() {return linkIgnore;}
	
	protected MAPPING mapping; @Override public MAPPING getMapping() {return mapping;}

	public AbstractSsiDomainProcessor(IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY,CLEANING> fbSsi,
									JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi)
	{
		this.fSsi=fSsi;
		this.fbSsi=fbSsi;
		
		efData = fbSsi.ejbData();
	}
	
	public void initLinks() throws JeeslNotFoundException
	{		
		linkUnlinked = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.unlinked);
		linkPrecondition = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.precondition);
		linkPossible = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.possible);
		linkLinked = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.linked);
		linkIgnore = fSsi.fByCode(fbSsi.getClassLink(),JeeslIoSsiLink.Code.ignore);
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
}