package org.jeesl.api.facade.system;

import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSystemNewsFacade <L extends JeeslLang,D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
										USER extends EjbWithId>
			extends JeeslFacade
{	
	List<NEWS> fActiveNews();
}