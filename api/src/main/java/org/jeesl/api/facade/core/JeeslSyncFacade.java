package org.jeesl.api.facade.core;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslSync;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslSyncFacade <L extends JeeslLang,
									D extends JeeslDescription,
									STATUS extends JeeslStatus<STATUS,L,D>,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									SYNC extends JeeslSync<L,D,STATUS,CATEGORY>>
					extends JeeslFacade
{	
	SYNC fSync(Class<SYNC> cSync, CATEGORY category, String code) throws JeeslNotFoundException;
	SYNC fcSync(Class<SYNC> cSync, Class<STATUS> cStatus, CATEGORY category, String code);
	
	boolean checkValid(Class<SYNC> cSync, SYNC sync, long seconds);
}