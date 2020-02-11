package org.jeesl.interfaces.model.module.map;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslStatisticalMap<L extends JeeslLang, D extends JeeslDescription>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithPosition,EjbWithCode,
								EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{}
	
	
}