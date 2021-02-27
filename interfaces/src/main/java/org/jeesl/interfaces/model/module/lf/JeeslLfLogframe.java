package org.jeesl.interfaces.model.module.lf;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.io.fr.JeeslWithFileRepositoryContainer;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.system.tenant.JeeslWithTenantSupport;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslLfLogframe<R extends JeeslTenantRealm<?,?,R,?>,
								
								M extends JeeslMarkup<?>,
								FRC extends JeeslFileContainer<?,?>>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithName,EjbWithNonUniqueCode,
								JeeslWithTenantSupport<R>
//,								JeeslWithFileRepositoryContainer<FRC>
{	
	public enum Attributes{lastEvent}
	

}