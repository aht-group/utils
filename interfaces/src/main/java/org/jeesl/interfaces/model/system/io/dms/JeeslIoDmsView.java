package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDmsView<L extends JeeslLang,D extends JeeslDescription,
								DMS extends JeeslIoDms<L,?,?,?,?,?>
//,								VT extends UtilsStatus<TYPE,L,D>
>
					extends Serializable,EjbWithId,EjbRemoveable,EjbPersistable,EjbSaveable,
							EjbWithPositionVisibleParent,EjbWithLang<L>
							
{	
	public enum Attributes{dms}
	
	DMS getDms();
	void setDms(DMS dms);
}