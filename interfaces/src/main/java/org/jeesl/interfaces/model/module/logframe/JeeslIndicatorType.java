package org.jeesl.interfaces.model.module.logframe;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslIndicatorType <L extends JeeslLang, D extends JeeslDescription,
									S extends JeeslStatus<S,L,D>,
									G extends JeeslGraphic<L,D,?,?,?>>
								extends Serializable,EjbPersistable,
										EjbWithCode,JeeslStatusFixedCode,
										EjbWithCodeGraphic<G>,JeeslStatus<S,L,D>
{
	public static enum Code{impact,outcome,output,activity};
}