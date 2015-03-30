package net.sf.ahtutils.interfaces.facade;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.sync.UtilsSync;

public interface UtilsSyncFacade <L extends UtilsLang,
									D extends UtilsDescription,
									STATUS extends UtilsStatus<STATUS,L,D>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									SYNC extends UtilsSync<L,D,STATUS,CATEGORY>>
					extends UtilsFacade
{	
	SYNC fSync(Class<SYNC> cSync, CATEGORY category, String code) throws UtilsNotFoundException;
	SYNC fcSync(Class<SYNC> cSync, Class<STATUS> cStatus, CATEGORY category, String code);
	
	boolean checkValid(Class<SYNC> cSync, SYNC sync, long seconds);
}
