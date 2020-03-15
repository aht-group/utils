package org.jeesl.interfaces.model.system.locale.status;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.graphic.EjbWithGraphic;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslMcsStatus<L extends JeeslLang, D extends JeeslDescription,
								REALM extends JeeslMcsRealm<L,D,REALM,?>,
								G extends JeeslGraphic<L,D,?,?,?>>
					extends EjbWithId,EjbRemoveable,Serializable,EjbSaveable,
							EjbWithNonUniqueCode,EjbWithPositionVisible,
							EjbWithLangDescription<L,D>,EjbWithGraphic<G>
{	
	enum EjbAttributes{code,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRref();
	void setRref(long rref);
}