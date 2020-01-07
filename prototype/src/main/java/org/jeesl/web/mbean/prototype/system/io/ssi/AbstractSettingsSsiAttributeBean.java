package org.jeesl.web.mbean.prototype.system.io.ssi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.data.EjbIoSsiAttributeFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSettingsSsiAttributeBean <L extends UtilsLang,D extends UtilsDescription,
										SYSTEM extends JeeslIoSsiSystem,
										MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
										ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
										DATA extends JeeslIoSsiData<MAPPING,LINK>,
										LINK extends UtilsStatus<LINK,L,D>,
										ENTITY extends JeeslRevisionEntity<L,D,?,?,?,?>>
						implements Serializable,SbSingleBean,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsSsiAttributeBean.class);
	
	private final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fbSsi;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,ENTITY,?,?,?,?,?> fbRevision;
	
	private JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi;
	
	private final SbSingleHandler<MAPPING> sbhMapping; public SbSingleHandler<MAPPING> getSbhMapping() {return sbhMapping;}
	private final SbMultiHandler<ENTITY> sbhEntity; public SbMultiHandler<ENTITY> getSbhEntity() {return sbhEntity;}

	private final List<ENTITY> entities; public List<ENTITY> getEntities() {return entities;}
	private final List<ATTRIBUTE> attributes; public List<ATTRIBUTE> getAttributes() {return attributes;}
	
	private ATTRIBUTE attribute; public ATTRIBUTE getAttribute() {return attribute;} public void setAttribute(ATTRIBUTE attribute) {this.attribute = attribute;}

	private final EjbIoSsiAttributeFactory<MAPPING,ATTRIBUTE,ENTITY> efAttribute;
	
	public AbstractSettingsSsiAttributeBean(final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fbSsi,
											final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,ENTITY,?,?,?,?,?> fbRevision)
	{
		this.fbSsi=fbSsi;
		this.fbRevision=fbRevision;
		entities = new ArrayList<>();
		attributes = new ArrayList<>();
		
		sbhMapping = new SbSingleHandler<MAPPING>(fbSsi.getClassMapping(),this);
		sbhEntity = new SbMultiHandler<ENTITY>(fbRevision.getClassEntity(),this);
		
		efAttribute = fbSsi.ejbAttribute();
	}

	public void postConstructSsiAttribute(JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi)
	{
		this.fSsi=fSsi;
		entities.addAll(fSsi.all(fbRevision.getClassEntity()));

		sbhMapping.setList(fSsi.all(fbSsi.getClassMapping()));
		sbhMapping.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		sbhEntity.setList(efAttribute.toListEntity(fSsi.allForParent(fbSsi.getClassAttribute(),sbhMapping.getSelection())));
		sbhEntity.toggleAll();
		reload();
		reset(true);
	}
	
	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reload();
		reset(true);
	}

	private void reload()
	{
		attributes.clear();
		for(ATTRIBUTE a : fSsi.allForParent(fbSsi.getClassAttribute(),sbhMapping.getSelection()))
		{
			if(sbhEntity.getSelected().contains(a.getEntity())) {attributes.add(a);}
		}
	}
	
	private void reset(boolean rAttribute)
	{
		if(rAttribute) {attribute=null;}
	}
	
	public void selectAttribute()
	{
		logger.info(AbstractLogMessage.selectEntity(attribute));
	}
	
	public void addAttribute()
	{
		attribute = efAttribute.build(sbhMapping.getSelection());
		if(sbhEntity.getHasSelected()) {attribute.setEntity(sbhEntity.getSelected().get(0));}
	}
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException
	{
		attribute.setMapping(fSsi.find(fbSsi.getClassMapping(),attribute.getMapping()));
		attribute.setEntity(fSsi.find(fbRevision.getClassEntity(),attribute.getEntity()));
		attribute = fSsi.save(attribute);
		reload();
	}
	
	public void deleteAttribute() throws UtilsConstraintViolationException, UtilsLockingException
	{
		fSsi.rm(attribute);
		reset(true);
		reload();
	}
}