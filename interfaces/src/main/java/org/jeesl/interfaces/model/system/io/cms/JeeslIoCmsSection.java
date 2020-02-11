package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCmsSection<L extends JeeslLang, S extends JeeslIoCmsSection<L,S>>
		extends Serializable,EjbWithId,
					EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithNonUniqueCode,
					EjbWithPositionVisibleParent,EjbWithLang<L>
{	
	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);
	
	List<S> getSections();
	void setSections(List<S> columns);
	
	
}