package org.jeesl.interfaces.model.module.map;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslStatisticalMap<L extends JeeslLang, D extends JeeslDescription>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithPosition,EjbWithCode,
								EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{}
	
	
}