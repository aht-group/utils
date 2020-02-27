package org.jeesl.interfaces.model.module.aom.asset;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

public interface JeeslAomType <L extends JeeslLang, D extends JeeslDescription,
							REALM extends JeeslMcsRealm<L,D,REALM,?>,
							TYPE extends JeeslAomType<L,D,REALM,TYPE,G>,
							G extends JeeslGraphic<L,D,?,?,?>>
			extends Serializable,EjbSaveable,EjbRemoveable,
					EjbWithParentAttributeResolver,
					EjbWithNonUniqueCode,EjbWithPosition,EjbWithLangDescription<L,D>,
					EjbWithCodeGraphic<G>
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	TYPE getParent();
	void setParent(TYPE parent);
	
	List<TYPE> getTypes();
	void setTypes(List<TYPE> types);
}