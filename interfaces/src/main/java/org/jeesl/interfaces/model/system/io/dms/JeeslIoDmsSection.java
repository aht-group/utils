package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDmsSection<L extends JeeslLang, D extends JeeslDescription,
					S extends JeeslIoDmsSection<L,D,S>>
					extends Serializable,EjbWithId,
					EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithPositionVisibleParent,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);
	
	List<S> getSections();
	void setSections(List<S> columns);
}