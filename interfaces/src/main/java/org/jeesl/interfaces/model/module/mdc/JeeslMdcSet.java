package org.jeesl.interfaces.model.module.mdc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.system.tenant.JeeslWithTenantSupport;

public interface JeeslMdcSet <R extends JeeslTenantRealm<?,?,R,?>,
								SET extends JeeslAttributeSet<?,?,?,?>
									>
		extends Serializable,EjbSaveable,EjbRemoveable,
				JeeslWithTenantSupport<R>
				
{
	public enum Attributes{}
	
	SET getAttributeSet();
	void setAttributeSet(SET AttributeSet);
	

}