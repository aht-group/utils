package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.status.JeeslWithCategory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCms<L extends JeeslLang,D extends JeeslDescription,
								CAT extends JeeslStatus<CAT,L,D>,
								S extends JeeslIoCmsSection<L,S>,
								LOC extends JeeslStatus<LOC,L,D>>
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