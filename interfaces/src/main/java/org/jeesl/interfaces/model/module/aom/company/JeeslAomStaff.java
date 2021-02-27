package org.jeesl.interfaces.model.module.aom.company;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.system.tenant.JeeslWithTenantSupport;

public interface JeeslAomStaff <REALM extends JeeslTenantRealm<?,?,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,?>>
		extends Serializable,EjbSaveable,
							JeeslWithTenantSupport<REALM>
{
	public enum Attributes{realm,realmIdentifier,scopes}	
}