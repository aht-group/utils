package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportColumnGroup<L extends JeeslLang,D extends JeeslDescription,
									SHEET extends JeeslReportSheet<L,D,?,?,?,?>,
									COLUMN extends JeeslReportColumn<L,D,?,STYLE,?,?,?>,
									STYLE extends JeeslReportStyle<L,D>
									>
		extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	SHEET getSheet();
	void setSheet(SHEET sheet);
	
	List<COLUMN> getColumns();
	void setColumns(List<COLUMN> columns);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean showLabel);
	
	Boolean getShowWeb();
	void setShowWeb(Boolean showWeb);
	
	STYLE getStyleHeader();
	void setStyleHeader(STYLE styleHeader);
	
	String getQueryColumns();
	void setQueryColumns(String queryColumns);
}