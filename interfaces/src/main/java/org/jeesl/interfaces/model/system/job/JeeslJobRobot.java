package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobRobot<L extends JeeslLang,D extends JeeslDescription>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>
{	

}