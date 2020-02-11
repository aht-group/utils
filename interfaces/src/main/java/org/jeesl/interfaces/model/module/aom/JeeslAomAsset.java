package org.jeesl.interfaces.model.module.aom;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.text.EjbWithRemark;

import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAomAsset <REALM extends JeeslAomRealm<?,?,REALM,?>,
							ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,ATYPE>,
							COMPANY extends JeeslAomCompany<REALM,?>,
							STATUS extends JeeslAomStatus<?,?,STATUS,?>,
							ATYPE extends JeeslAomType<?,?,REALM,ATYPE,?>>
			extends Serializable,EjbSaveable,
					EjbWithPosition,EjbWithParentAttributeResolver,
					EjbWithNonUniqueCode,EjbWithName,EjbWithRemark,
					JeeslWithStatus<STATUS>
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	ASSET getParent();
	void setParent(ASSET parent);
	
	ATYPE getType1();
	void setType1(ATYPE type1);
	
	COMPANY getManufacturer();
	void setManufacturer(COMPANY manufacturer);
	
	List<ASSET> getAssets();
	void setAssets(List<ASSET> assets);
}