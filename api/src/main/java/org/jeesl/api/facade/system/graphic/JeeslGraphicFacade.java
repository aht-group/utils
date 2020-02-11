package org.jeesl.api.facade.system.graphic;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.graphic.EjbWithGraphic;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFacade <L extends JeeslLang, D extends JeeslDescription,
									S extends EjbWithId,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>>
			extends JeeslFacade
{	
	G fGraphicForStatus(long statusId) throws JeeslNotFoundException;
	<W extends EjbWithGraphic<G>> G fGraphic(Class<W> c, W w) throws JeeslNotFoundException;
	<W extends EjbWithGraphic<G>> G fGraphic(Class<W> c, long id) throws JeeslNotFoundException; 
	<T extends EjbWithGraphic<G>> List<T> allWithGraphicFigures(Class<T> c);
}