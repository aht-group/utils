package org.jeesl.interfaces.model.system.io.repository;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslFileContainer<META extends JeeslFileMeta<?,?,?,?>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithName
{	
	List<META> getMetas();
	void setMetas(List<META> metas);
}