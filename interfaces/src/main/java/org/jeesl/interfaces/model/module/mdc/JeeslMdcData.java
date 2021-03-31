package org.jeesl.interfaces.model.module.mdc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;

public interface JeeslMdcData <ACTIVITY extends JeeslMdcActivity<?,?,?,?>,
								AC extends JeeslAttributeContainer<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable
				
{
	public enum Attributes{activity}
	
	ACTIVITY getActivity();
	void setActivity(ACTIVITY activity);
	
	AC getCollectionContainer();
	void setCollectionContainer(AC collectionContainer);

}