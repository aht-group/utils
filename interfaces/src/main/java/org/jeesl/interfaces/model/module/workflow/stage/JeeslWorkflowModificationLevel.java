package org.jeesl.interfaces.model.module.workflow.stage;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDescription;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;

public interface JeeslWorkflowModificationLevel <L extends JeeslLang, D extends JeeslDescription,
												S extends JeeslStatus<L,D,S>,
												G extends JeeslGraphic<L,D,?,?,?>>
									extends Serializable,EjbPersistable,
											EjbWithCode,JeeslStatusFixedCode,
											JeeslOptionRestDescription,JeeslOptionRestDownload,
											EjbWithCodeGraphic<G>,
											JeeslStatus<L,D,S>
{
	public enum Code{nothing,full,admin}
}