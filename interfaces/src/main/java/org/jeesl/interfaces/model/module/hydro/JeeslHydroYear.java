package org.jeesl.interfaces.model.module.hydro;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.date.EjbWithValidFromUntil;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslHydroYear <L extends JeeslLang, D extends JeeslDescription,
								HD extends JeeslStatus<HD,L,D>,
								HY extends JeeslHydroYear<L,D,HD,HY>>
					extends Serializable,EjbSaveable,EjbRemoveable,
							EjbWithParentAttributeResolver,
							EjbWithLang<L>,EjbWithDescription<D>,
							EjbWithCode,EjbWithPosition,
							EjbWithValidFromUntil
{

	public enum Attributes{decade}

	void setDecade(HD decade);
	HD getDecade();
}