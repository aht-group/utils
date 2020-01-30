package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTrainingFacade <L extends UtilsLang,D extends UtilsDescription,
										TYPE extends UtilsStatus<TYPE,L,D>>
			extends JeeslFacade
{	
	
}