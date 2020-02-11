package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportColumn<L extends JeeslLang,D extends JeeslDescription,
									GROUP extends JeeslReportColumnGroup<L,D,?,?,STYLE>,
									STYLE extends JeeslReportStyle<L,D>,
									CDT extends JeeslStatus<CDT,L,D>,
									CW extends JeeslStatus<CW,L,D>,
									TLS extends JeeslStatus<TLS,L,D>>

		extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	GROUP getGroup();
	void setGroup(GROUP group);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean showLabel);
	
	Boolean getShowWeb();
	void setShowWeb(Boolean showWeb);
	
	Boolean getShowHint();
	void setShowHint(Boolean showHint);
	
	Boolean getFilterBy();
	void setFilterBy(Boolean filterBy);
	
	CDT getDataType();
	void setDataType(CDT dataType);
	
	String getQueryHeader();
	void setQueryHeader(String queryHeader);
	
	String getQueryCell();
	void setQueryCell(String queryCell);
	
	String getQueryFooter();
	void setQueryFooter(String queryFooter);

	String getQueryTrafficLight();
	void setQueryTrafficLight(String queryTrafficLight);
	
	CW getColumWidth();
	void setColumWidth(CW columWidth);
	
	Integer getColumSize();
	void setColumSize(Integer columSize);
	
	STYLE getStyleHeader();
	void setStyleHeader(STYLE styleHeader);
	
	STYLE getStyleCell();
	void setStyleCell(STYLE styleCell);
	
	STYLE getStyleFooter();
	void setStyleFooter(STYLE styleFooter);
	
	TLS getTrafficLightScope();
	void setTrafficLightScope(TLS trafficLightScope);
}