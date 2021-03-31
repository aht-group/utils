package org.jeesl.interfaces.model.module.mdc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;

public interface JeeslMdcData <COLLECTION extends JeeslMdcActivity<?,?,?,?>,
								ACONTAINER extends JeeslAttributeContainer<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable
				
{
	public enum Attributes{activity}
	
	COLLECTION getActivity();
	void setActivity(COLLECTION activity);
	
	ACONTAINER getCollectionContainer();
	void setCollectionContainer(ACONTAINER collectionContainer);

}