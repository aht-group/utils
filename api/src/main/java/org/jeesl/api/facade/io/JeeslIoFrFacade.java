package org.jeesl.api.facade.io;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.io.JeeslFileRepositoryStore;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoFrFacade <L extends UtilsLang, D extends UtilsDescription,
								SYSTEM extends JeeslIoSsiSystem,
								STORAGE extends JeeslFileStorage<L,D,SYSTEM,ENGINE>,
								ENGINE extends UtilsStatus<ENGINE,L,D>,
								CONTAINER extends JeeslFileContainer<STORAGE,META>,
								META extends JeeslFileMeta<D,CONTAINER,TYPE,?>,
								TYPE extends JeeslFileType<L,D,TYPE,?>>
		extends JeeslFacade,JeeslFileRepositoryStore<META>
{
	CONTAINER moveContainer(CONTAINER container, STORAGE destination) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException;
	Json2Tuples<STORAGE,TYPE> tpIoFileByStorageType();
}