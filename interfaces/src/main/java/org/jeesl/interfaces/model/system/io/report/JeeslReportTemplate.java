package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportTemplate<L extends JeeslLang,D extends JeeslDescription,
								CELL extends JeeslReportCell<L,D,?,?,?,?,?,?,?,?,?,CELL,?,?,?,?,?,?,?,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	List<CELL> getCells();
	void setCells(List<CELL> cells);
}