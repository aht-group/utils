package org.jeesl.web.mbean.prototype.system.io.fr;

import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.controller.handler.system.io.fr.JeeslFileTypeHandler;
import org.jeesl.controller.handler.tuple.JsonTuple1Handler;
import org.jeesl.controller.handler.tuple.JsonTuple2Handler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorageEngine;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorageType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractFrStorageBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									STORAGE extends JeeslFileStorage<L,D,SYSTEM,STYPE,ENGINE>,
									STYPE extends JeeslFileStorageType<L,D,STYPE,?>,
									ENGINE extends JeeslFileStorageEngine<L,D,ENGINE,?>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,FTYPE,STATUS>,
									FTYPE extends JeeslFileType<L,D,FTYPE,?>,
									STATUS extends JeeslFileStatus<L,D,STATUS,?>>
						extends AbstractAdminBean<L,D>
						implements SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFrStorageBean.class);
	
	private JeeslIoFrFacade<L,D,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,FTYPE> fFr;
	private final IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,FTYPE,STATUS> fbFr;
	
	protected final SbMultiHandler<STYPE> sbhStorageType; public SbMultiHandler<STYPE> getSbhStorageType() {return sbhStorageType;}
	private final JsonTuple1Handler<STORAGE> thSize; public JsonTuple1Handler<STORAGE> getThSize() {return thSize;}
	private final JsonTuple2Handler<STORAGE,FTYPE> thCount; public JsonTuple2Handler<STORAGE,FTYPE> getThCount() {return thCount;}

	private JeeslFileTypeHandler<META,FTYPE> fth;
	
	private List<STORAGE> storages; public List<STORAGE> getStorages() {return storages;}
	private List<ENGINE> engines; public List<ENGINE> getEngines() {return engines;}
	
	private STORAGE storage; public STORAGE getStorage() {return storage;} public void setStorage(STORAGE storage) {this.storage = storage;}
	private FTYPE typeUnknown;public FTYPE getTypeUnknown() {return typeUnknown;}

	protected AbstractFrStorageBean(IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,FTYPE,STATUS> fbFr,
									JeeslComparatorProvider<FTYPE> jcpB)
	{
		super(fbFr.getClassL(),fbFr.getClassD());
		this.fbFr=fbFr;
		sbhStorageType = new SbMultiHandler<>(fbFr.getClassStorageType(),this);
		thSize = new JsonTuple1Handler<STORAGE>(fbFr.getClassStorage());
		thCount = new JsonTuple2Handler<STORAGE,FTYPE>(fbFr.getClassStorage(),fbFr.getClassType());
		thCount.setComparatorProviderB(jcpB);
	}
	
	protected void postConstructFrStorage(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
											JeeslIoFrFacade<L,D,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,FTYPE> fFr)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fFr=fFr;
		fth = new JeeslFileTypeHandler<>(fbFr,fFr);
		typeUnknown = fFr.fByEnum(fbFr.getClassType(), JeeslFileType.Code.unknown);
		initSbh();
		reloadStorages();
		engines = fFr.allOrderedPositionVisible(fbFr.getClassEngine());
		thCount.init(fFr.tpcIoFileByStorageType());
		thSize.init(fFr.tpsIoFileByStorage());
	}
	
	protected void initSbh()
	{
		sbhStorageType.setList(fFr.allOrderedPositionVisible(fbFr.getClassStorageType()));
		sbhStorageType.toggleAll();
	}
	
	public void resetStorage() {reset(true);}
	private void reset(boolean rStorage)
	{
		if(rStorage) {storage=null;}
	}
	
	@Override
	public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		reloadStorages();
	}
	
	private void reloadStorages()
	{
		storages = fFr.allOrderedPosition(fbFr.getClassStorage());
	}
	
	public void addStorage()
	{
		reset(true);
		STYPE type = null; if(!sbhStorageType.getList().isEmpty()) {type = sbhStorageType.getList().get(0);}
		storage = fbFr.ejbStorage().build(type);
		storage.setName(efLang.createEmpty(localeCodes));
		storage.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveStorage() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(storage));}
		storage.setEngine(fFr.find(fbFr.getClassEngine(),storage.getEngine()));
		storage = fFr.save(storage);
		reloadStorages();
	}
	
	public void deleteStorage() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(storage));}
		fFr.rm(storage);
		reloadStorages();
		reset(true);
	}
	
	public void selectStorage()
	{
		storage = fFr.find(fbFr.getClassStorage(), storage);
		storage = efLang.persistMissingLangs(fFr, localeCodes, storage);
		storage = efDescription.persistMissingLangs(fFr, localeCodes, storage);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(storage));}
		reset(false);
	}
	
	public void fixType()
	{	
		if(typeUnknown!=null && fth!=null)
		{
			int i=0;
			for(META meta : fFr.allForType(fbFr.getClassMeta(), typeUnknown))
			{
				String code = meta.getType().getCode();
				fth.updateType(meta);
				if(!code.contentEquals(meta.getType().getCode()))
				{
					i++;
					try {fFr.save(meta);}
					catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
				}
				if(i==250)
				{
					logger.info("Breaking loop to prevent timeout");
					break;
				}
			}
		}
		thCount.init(fFr.tpcIoFileByStorageType());
	}
	
	public void reorderStorages() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fFr, storages);}
}