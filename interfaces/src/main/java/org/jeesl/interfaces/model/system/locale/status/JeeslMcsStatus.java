package org.jeesl.interfaces.model.system.locale.status;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithGraphic;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslMcsStatus<L extends JeeslLang, D extends JeeslDescription,
								R extends JeeslMcsRealm<L,D,R,?>,
								S extends JeeslMcsStatus<L,D,R,S,G>,
								G extends JeeslGraphic<L,D,?,?,?>>
					extends EjbWithId,EjbRemoveable,Serializable,EjbSaveable,
							EjbWithNonUniqueCode,EjbWithPositionVisible,
							EjbWithLangDescription<L,D>,EjbWithGraphic<G>,
							JeeslStatus<S,L,D>,
							JeeslWithMultiClientSupport<R>
{	
	enum EjbAttributes{code,parent}
}