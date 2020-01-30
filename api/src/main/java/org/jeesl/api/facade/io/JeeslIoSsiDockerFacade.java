package org.jeesl.api.facade.io;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoSsiDockerFacade <L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem
									
									>
			extends JeeslFacade
{	

}