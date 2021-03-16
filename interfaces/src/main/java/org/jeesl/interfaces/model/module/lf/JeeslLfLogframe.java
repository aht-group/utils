package org.jeesl.interfaces.model.module.lf;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslLfLogframe //<M extends JeeslMarkup<?>>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithName
//,								JeeslWithFileRepositoryContainer<FRC>
{	
	public enum Attributes{lastEvent}
	

}