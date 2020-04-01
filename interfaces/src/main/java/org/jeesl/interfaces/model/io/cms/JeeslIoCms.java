package org.jeesl.interfaces.model.io.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.interfaces.model.with.system.status.JeeslWithCategory;

public interface JeeslIoCms<L extends JeeslLang, D extends JeeslDescription,
								CAT extends JeeslStatus<CAT,L,D>,
								S extends JeeslIoCmsSection<L,S>,
								LOC extends JeeslLocale<L,D,LOC,?>>
		extends Serializable,EjbWithId,
				EjbSaveable,EjbRemoveable,EjbWithPosition,EjbWithLang<L>,JeeslWithCategory<CAT>
{	
	public enum Attributes{category,position}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	S getRoot();
	void setRoot(S section);
	
	List<LOC> getLocales();
	void setLocales(List<LOC> locales);
	
	boolean getToc();
	void setToc(boolean toc);
}