package org.jeesl.factory.ejb.system.io.report;

import java.util.UUID;

import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Cell;

public class EjbIoReportCellFactory<L extends JeeslLang,D extends JeeslDescription,
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
								STYLE extends JeeslReportStyle<L,D>,CDT extends JeeslStatus<CDT,L,D>,CW extends JeeslStatus<CW,L,D>,
								RT extends JeeslStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends JeeslStatus<TLS,L,D>,
								FILLING extends JeeslStatus<FILLING,L,D>,
								TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportCellFactory.class);
	
	final Class<CELL> cCell;
	
	private JeeslDbLangUpdater<CELL,L> dbuLang;
	private JeeslDbDescriptionUpdater<CELL,D> dbuDescription;
    
	public EjbIoReportCellFactory(final Class<L> cL,final Class<D> cD,final Class<CELL> cCell)
	{       
        this.cCell = cCell;
        dbuLang = JeeslDbLangUpdater.factory(cCell, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cCell, cD);
	}
	    
	public CELL build(TEMPLATE template)
	{
		CELL ejb = null;
		try
		{
			ejb = cCell.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setTemplate(template);
			ejb.setRowNr(1);
			ejb.setColNr(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public CELL build(TEMPLATE template, Cell cell)
	{
		CELL ejb = null;
		try
		{
			ejb = cCell.newInstance();
			ejb.setCode(cell.getCode());
			ejb.setTemplate(template);
			ejb = update(ejb,cell);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
		
	public CELL update(CELL eCell, Cell xCell)
	{
		eCell.setVisible(xCell.isVisible());
		eCell.setRowNr(xCell.getRowNr());
		eCell.setColNr(xCell.getColNr());
		return eCell;
	}
		
	public CELL updateLD(JeeslFacade fUtils, CELL eCell, Cell xCell) throws JeeslConstraintViolationException, JeeslLockingException
	{
		eCell=dbuLang.handle(fUtils, eCell, xCell.getLangs());
		eCell = fUtils.save(eCell);
		eCell=dbuDescription.handle(fUtils, eCell, xCell.getDescriptions());
		eCell = fUtils.save(eCell);
		return eCell;
	}
}