package org.jeesl.interfaces.model.module.its.task;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;

public interface JeeslItsTaskType <L extends JeeslLang, D extends JeeslDescription, S extends JeeslStatus<S,L,D>, G extends JeeslGraphic<L,D,?,?,?>>
									extends Serializable,EjbPersistable,
										EjbWithCode,JeeslStatusFixedCode,
										JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
										JeeslStatus<S,L,D>
{
	public static enum Code{issue,splinter};
}