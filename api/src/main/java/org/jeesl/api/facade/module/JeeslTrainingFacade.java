package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslTrainingFacade <L extends JeeslLang,D extends JeeslDescription,
										TYPE extends JeeslStatus<TYPE,L,D>>
			extends JeeslFacade
{	
	
}