package org.jeesl.interfaces.model.module.mdc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

public interface JeeslMdcData <COLLECTION extends JeeslMdcActivity<?,?,?,?>,
								ACONTAINER extends JeeslAttributeContainer<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver
				
{
	public enum Attributes{activity}
	
	COLLECTION getActivity();
	void setActivity(COLLECTION activity);
	
	ACONTAINER getCollectionContainer();
	void setCollectionContainer(ACONTAINER collectionContainer);

}