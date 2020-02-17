package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorageEngine;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultFileRepositoryHandler<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									STORAGE extends JeeslFileStorage<L,D,SYSTEM,ENGINE>,
									ENGINE extends JeeslFileStorageEngine<L,D,ENGINE,?>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE,STATUS>,
									TYPE extends JeeslFileType<L,D,TYPE,?>,
									STATUS extends JeeslFileStatus<L,D,STATUS,?>>
	extends AbstractFileRepositoryHandler<L,D,LOC,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DefaultFileRepositoryHandler.class);

	public DefaultFileRepositoryHandler(JeeslIoFrFacade<L,D,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
								IoFileRepositoryFactoryBuilder<L,D,LOC,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS> fbFile,
								JeeslFileRepositoryCallback callback)
	{
		super(fFr,fbFile,callback);
	}
}