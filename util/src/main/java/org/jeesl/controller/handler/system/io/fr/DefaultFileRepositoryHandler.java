package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.io.fr.JeeslFileReplication;
import org.jeesl.interfaces.model.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.io.fr.JeeslFileStorageEngine;
import org.jeesl.interfaces.model.io.fr.JeeslFileStorageType;
import org.jeesl.interfaces.model.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultFileRepositoryHandler<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem<L,D>,
									STORAGE extends JeeslFileStorage<L,D,SYSTEM,STYPE,ENGINE>,
									STYPE extends JeeslFileStorageType<L,D,STYPE,?>,
									ENGINE extends JeeslFileStorageEngine<L,D,ENGINE,?>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE,STATUS>,
									TYPE extends JeeslFileType<L,D,TYPE,?>,
									REP extends JeeslFileReplication<L,D,SYSTEM,STORAGE>,
									STATUS extends JeeslFileStatus<L,D,STATUS,?>>
	extends AbstractFileRepositoryHandler<L,D,LOC,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,TYPE,REP,STATUS>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DefaultFileRepositoryHandler.class);

	public DefaultFileRepositoryHandler(JeeslIoFrFacade<L,D,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,TYPE> fFr,
								IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,STYPE,ENGINE,CONTAINER,META,TYPE,REP,STATUS> fbFile,
								JeeslFileRepositoryCallback callback)
	{
		super(fFr,fbFile,callback);
	}
}