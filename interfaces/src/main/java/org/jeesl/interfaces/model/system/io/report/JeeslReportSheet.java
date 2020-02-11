package org.jeesl.interfaces.model.system.io.report;

import java.io.Serializable;
import java.util.List;

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
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportSheet<L extends JeeslLang,D extends JeeslDescription,
									IMPLEMENTATION extends JeeslStatus<IMPLEMENTATION,L,D>,
									WORKBOOK extends JeeslReportWorkbook<?,?>,
									GROUP extends JeeslReportColumnGroup<L,D,?,?,?>,
									ROW extends JeeslReportRow<L,D,?,?,IMPLEMENTATION,WORKBOOK,?,GROUP,?,ROW,?,?,?,?,?,?,?,?,?,?>>
		extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum Attributes{workbook,code}
	public enum Offset{rows,columns} 
	
	WORKBOOK getWorkbook();
	void setWorkbook(WORKBOOK workbook);
	
	IMPLEMENTATION getImplementation();
	void setImplementation(IMPLEMENTATION implementation);
	
	String getQueryTable();
	void setQueryTable(String queryTable);
	
	List<ROW> getRows();
	void setRows(List<ROW> rows);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}