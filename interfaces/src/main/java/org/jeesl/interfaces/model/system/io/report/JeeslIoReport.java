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
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.xml.report.Report;

public interface JeeslIoReport<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								WORKBOOK extends JeeslReportWorkbook<?,?>
								>
		extends Serializable,EjbWithId,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	public enum FooterOrientation{left,center,right}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	WORKBOOK getWorkbook();
	void setWorkbook(WORKBOOK workbook);
	
	String getXmlExample();
	void setXmlExample(String xmlExample);
}