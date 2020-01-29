package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.om.JeeslOmCompany;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslOmFacade <L extends UtilsLang, D extends UtilsDescription,
									COMPANY extends JeeslOmCompany>
			extends UtilsFacade
{	

}