package org.jeesl.api.facade.io;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.ssi.core.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public interface JeeslIoSsiDockerFacade <L extends JeeslLang,D extends JeeslDescription,
									SYSTEM extends JeeslIoSsiSystem<L,D>
									>
			extends JeeslFacade
{	

}