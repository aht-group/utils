package org.jeesl.interfaces.model.module.aom.company;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;

public interface JeeslAomStaff <REALM extends JeeslMcsRealm<?,?,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,?>>
		extends Serializable,EjbSaveable,
							JeeslWithMultiClientSupport<REALM>
{
	public enum Attributes{realm,realmIdentifier,scopes}	
}