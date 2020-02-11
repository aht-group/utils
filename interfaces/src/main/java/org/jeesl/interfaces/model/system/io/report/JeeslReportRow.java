package org.jeesl.interfaces.model.system.io.report;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportRow<L extends JeeslLang,D extends JeeslDescription,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
									IMPLEMENTATION extends JeeslStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
									SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
									GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
									COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
									ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
									TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
									CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
									STYLE extends JeeslReportStyle<L,D>,
									CDT extends JeeslStatus<CDT,L,D>,CW extends JeeslStatus<CW,L,D>,
									RT extends JeeslStatus<RT,L,D>,
									ENTITY extends EjbWithId,
									ATTRIBUTE extends EjbWithId,
									TL extends JeeslTrafficLight<L,D,TLS>,
									TLS extends JeeslStatus<TLS,L,D>>

		extends EjbWithId,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	SHEET getSheet();
	void setSheet(SHEET sheet);
	
	RT getType();
	void setType(RT type);
	
	CDT getDataType();
	void setDataType(CDT dataType);
	
	String getQueryCell();
	void setQueryCell(String queryCell);
	
	int getOffsetRows();
	void setOffsetRows(int offsetRows);
	
	int getOffsetColumns();
	void setOffsetColumns(int offsetColumns);
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}