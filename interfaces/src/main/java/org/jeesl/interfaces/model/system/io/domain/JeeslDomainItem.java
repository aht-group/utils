package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDomainItem <QUERY extends JeeslDomainQuery<?,?,?,?>,
									SET extends JeeslDomainSet<?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbWithPositionVisibleParent,EjbRemoveable
{
	public enum Attributes{itemSet,query}
	
	SET getItemSet();
	void setItemSet(SET itemSet);
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
//	Boolean getTableHeader();
//	void setTableHeader(Boolean tableHeader);
}