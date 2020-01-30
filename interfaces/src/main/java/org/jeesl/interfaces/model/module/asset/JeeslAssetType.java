package org.jeesl.interfaces.model.module.asset;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public interface JeeslAssetType <L extends UtilsLang, D extends UtilsDescription,
							REALM extends JeeslAssetRealm<L,D,REALM,?>,
							TYPE extends JeeslAssetType<L,D,REALM,TYPE,G>,
							G extends JeeslGraphic<L,D,?,?,?>>
			extends Serializable,
					EjbSaveable,EjbWithParentAttributeResolver,
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