package org.jeesl.interfaces.model.module.its.config;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslItsConfig <R extends JeeslMcsRealm<?,?,R,?>,
								O extends JeeslItsConfigOption<?,?,O,?>>
			extends Serializable,EjbSaveable,EjbRemoveable,
					JeeslWithMultiClientSupport<R>
					
					
{
	public enum Attributes{realm,rref,option}
	
	O getOption();
	void setOption(O option);
}