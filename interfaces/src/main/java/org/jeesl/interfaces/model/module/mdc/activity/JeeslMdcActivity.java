package org.jeesl.interfaces.model.module.mdc.activity;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.system.tenant.JeeslWithTenantSupport;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslMdcActivity <R extends JeeslTenantRealm<?,?,R,?>,
									
									SCOPE extends JeeslMdcScope<?,?,R,SCOPE,?>,
									STATUS extends JeeslMdcStatus<?,?,STATUS,?>,
									AS extends JeeslAttributeSet<?,?,?,?>
									>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithName,
				JeeslWithTenantSupport<R>
				
{
	public enum Attributes{scope, attributeSet}
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	AS getAttributeSet();
	void setAttributeSet(AS attributeSet);
}