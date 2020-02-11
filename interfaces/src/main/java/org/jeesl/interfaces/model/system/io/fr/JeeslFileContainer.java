package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslFileContainer<STORAGE extends JeeslFileStorage<?,?,?,?>, META extends JeeslFileMeta<?,?,?,?>>
		extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithParentAttributeResolver
{
	public enum Attributes{storage}
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
	
	List<META> getMetas();
	void setMetas(List<META> metas);
}