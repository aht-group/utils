package org.jeesl.interfaces.model.module.rating;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRating<L extends JeeslLang, D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								DOMAIN extends EjbWithId
>
			extends Serializable,EjbWithId,EjbSaveable,EjbWithParentAttributeResolver
{
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	int getRating();
	void setRating(int rating);
}