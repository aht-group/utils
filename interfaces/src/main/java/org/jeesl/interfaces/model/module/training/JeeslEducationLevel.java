package org.jeesl.interfaces.model.module.training;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

public interface JeeslEducationLevel <S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription,G extends JeeslGraphic<L,D,?,?,?>>
									extends Serializable,EjbPersistable,
									EjbWithCode,//UtilsStatusFixedCode,
									JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
									JeeslStatus<S,L,D>
{

}