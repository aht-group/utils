package org.jeesl.interfaces.model.system.news;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.date.EjbWithValidFromUntil;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSystemNews<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
								USER extends EjbWithId>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithValidFromUntil,EjbWithVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Attributes{visible,validFrom}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	USER getAuthor();
	void setAuthor(USER user);
}