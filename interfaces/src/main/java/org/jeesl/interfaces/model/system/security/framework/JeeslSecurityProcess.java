package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslSecurityProcess<L extends JeeslLang
								  
								   >
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithCode,EjbWithPositionVisible,
					EjbWithLang<L>
{

	
}