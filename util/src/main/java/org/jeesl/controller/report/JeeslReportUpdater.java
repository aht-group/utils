package org.jeesl.controller.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.controller.processor.JobCodeProcessor;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnGroupFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportRowFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportSheetFactory;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportWorkbookFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.xls.system.io.report.XlsFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.interfaces.controller.report.JeeslReport;
import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportSetting;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.model.json.JsonFlatFigures;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportCellComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportGroupComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportRowComparator;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportSheetComparator;
import org.metachart.model.json.pivot.PivotSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.ColumnGroup;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Row;
import net.sf.ahtutils.xml.report.Rows;
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsWorkbook;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.io.JsonUtil;
import net.sf.exlp.util.io.StringUtil;

public class JeeslReportUpdater <L extends JeeslLang,D extends JeeslDescription,
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
								RCAT extends JeeslStatus<RCAT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends JeeslStatus<TLS,L,D>,
								FILLING extends JeeslStatus<FILLING,L,D>,
								TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslReportUpdater.class);
	
	private final boolean debugCreation = false;
	
	private final JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport;
	private final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport;

	private final EjbIoReportFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efReport;
	private final EjbIoReportWorkbookFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efWorkbook;
	private final EjbIoReportSheetFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efSheet;
	private final EjbIoReportColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efGroup;
	private final EjbIoReportRowFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efRow;
	private final EjbIoReportColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> efColumn;

	public JeeslReportUpdater(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport,
			final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport)
	{
		this.fReport=fReport;
		this.fbReport=fbReport;
		
		efReport = fbReport.report();
		efWorkbook = fbReport.workbook();
		efSheet = fbReport.sheet();
		efGroup = fbReport.group();
		efColumn = fbReport.column();
		efRow = fbReport.row();
	}
	
	public REPORT cloneIoReport(Report xReport) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, UtilsProcessingException
	{
		xReport.setCode(UUID.randomUUID().toString());
		if(xReport.isSetXlsWorkbook() && xReport.getXlsWorkbook().isSetXlsSheets())
		{
			for(XlsSheet xSheet : xReport.getXlsWorkbook().getXlsSheets().getXlsSheet())
			{
				xSheet.setCode(UUID.randomUUID().toString());
				for(Serializable s : xSheet.getContent())
				{
					if(s instanceof ColumnGroup)
					{
						ColumnGroup xGroup = (ColumnGroup)s;
						xGroup.setCode(UUID.randomUUID().toString());
						for(XlsColumn xColumn : xGroup.getXlsColumn())
						{
							xColumn.setCode(UUID.randomUUID().toString());
						}
					}
					else if(s instanceof Rows)
					{
						Rows xRows = (Rows)s;
						for(Row xRow : xRows.getRow())
						{
							xRow.setCode(UUID.randomUUID().toString());
						}
					}
				}
			}
		}
		
		return importSystemIoReport(xReport);
	}
	
	public REPORT importSystemIoReport(Report xReport) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, UtilsProcessingException
	{
		REPORT eReport;
		
		try
		{
			eReport = fReport.fByCode(fbReport.getClassReport(), xReport.getCode());
			if(debugCreation) {logger.debug("Existing "+fbReport.getClassReport());}
		}
		catch (JeeslNotFoundException e)
		{
			if(debugCreation) {logger.debug("New "+fbReport.getClassReport());}
			eReport = efReport.build(fReport,xReport);
			eReport = fReport.save(eReport);
		}
		eReport = efReport.update(fReport,eReport, xReport);
		
		eReport = fReport.save(eReport);
		eReport = efReport.updateLD(fReport,eReport,xReport);
				
		if(xReport.isSetXlsWorkbook())
		{
			importWorkbook(eReport,xReport.getXlsWorkbook());
		}
		
		return eReport;
	}
	
	private REPORT importWorkbook(REPORT eReport, XlsWorkbook xWorkbook) throws JeeslConstraintViolationException, JeeslLockingException, UtilsProcessingException
	{
		WORKBOOK eWorkbook;
		if(eReport.getWorkbook()!=null)
		{
			eWorkbook = eReport.getWorkbook();
			if(debugCreation) {logger.debug("Existing WB");}
		}
		else
		{
			if(debugCreation) {logger.debug("NEW WB");}
			eWorkbook = efWorkbook.build(eReport);
			eWorkbook = fReport.save(eWorkbook);
			eReport.setWorkbook(eWorkbook);
		}
		eWorkbook = fReport.load(eWorkbook);
		
		JeeslDbCodeEjbUpdater<SHEET> dbUpdaterSheet = JeeslDbCodeEjbUpdater.createFactory(fbReport.getClassSheet());
		dbUpdaterSheet.dbEjbs(eWorkbook.getSheets());
		if(xWorkbook.isSetXlsSheets())
		{
			for(XlsSheet xSheet : xWorkbook.getXlsSheets().getXlsSheet())
			{
				try
				{
					SHEET eSheet = importSheet(eWorkbook,xSheet);
					dbUpdaterSheet.handled(eSheet);
				}
				catch (JeeslNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (JeeslConstraintViolationException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (JeeslLockingException e) {throw new UtilsProcessingException(e.getMessage());}
				catch (ExlpXpathNotFoundException e) {throw new UtilsProcessingException(e.getMessage());}
			}
		}
		for(SHEET s : dbUpdaterSheet.getEjbForRemove()){fReport.rmSheet(s);}
		
		return eReport;
	}
	
	private SHEET importSheet(WORKBOOK workbook, XlsSheet xSheet) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		logger.trace("Importing "+fbReport.getClassSheet().getSimpleName()+" "+workbook.getReport().getCategory().getPosition()+"."+workbook.getReport().getPosition()+"."+xSheet.getPosition());
		SHEET eSheet;
		try
		{
			eSheet = fReport.fSheet(workbook, xSheet.getCode());
			if(debugCreation) {logger.debug("Existing Sheet");}
		}
		catch (JeeslNotFoundException e)
		{
			if(debugCreation) {logger.debug("NEW Sheet");}
			eSheet = efSheet.build(fReport,workbook,xSheet);
			eSheet = fReport.save(eSheet);
		}
		eSheet = efSheet.update(fReport,eSheet,xSheet);
		eSheet = fReport.save(eSheet);
		eSheet = efSheet.updateLD(fReport, eSheet, xSheet);
		eSheet = fReport.load(eSheet,false);
		
		JeeslDbCodeEjbUpdater<GROUP> dbUpdaterGroup = JeeslDbCodeEjbUpdater.createFactory(fbReport.getClassGroup());
		JeeslDbCodeEjbUpdater<ROW> dbUpdaterRow = JeeslDbCodeEjbUpdater.createFactory(fbReport.getClassRow());
		
		dbUpdaterGroup.dbEjbs(eSheet.getGroups());
		dbUpdaterRow.dbEjbs(eSheet.getRows());
		
		for(Serializable s : xSheet.getContent())
		{
			if(s instanceof ColumnGroup)
			{
				ColumnGroup xGroup = (ColumnGroup)s;
				GROUP eGroup = importGroup(eSheet,xGroup);
				dbUpdaterGroup.handled(eGroup);
			}
			else if(s instanceof Rows)
			{
				Rows xRows = (Rows)s;
				for(Row xRow : xRows.getRow())
				{
					ROW eRow = importRow(eSheet,xRow);
					dbUpdaterRow.handled(eRow);
				}
			}
		}
		for(GROUP g : dbUpdaterGroup.getEjbForRemove()){fReport.rmGroup(g);}
		for(ROW r : dbUpdaterRow.getEjbForRemove()){fReport.rmRow(r);}
		
		return eSheet;
	}
	
	private ROW importRow(SHEET eSheet, Row xRow) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		ROW eRow;
		try
		{
			eRow = fReport.fByCode(fbReport.getClassRow(), xRow.getCode());
			if(debugCreation) {logger.debug("Existing "+fbReport.getClassRow());}
		}
		catch (JeeslNotFoundException e)
		{
			if(debugCreation) {logger.debug("NEW "+fbReport.getClassRow());}
			eRow = efRow.build(fReport,eSheet,xRow);
			eRow = fReport.save(eRow);
		}
		eRow = efRow.update(fReport,eRow,xRow);
		eRow = fReport.save(eRow);
		eRow = efRow.updateLD(fReport,eRow,xRow);

		return eRow;
	}
	
	private GROUP importGroup(SHEET eSheet, ColumnGroup xGroup) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, ExlpXpathNotFoundException, UtilsProcessingException
	{
		GROUP eGroup;
		try
		{
			eGroup = fReport.fByCode(fbReport.getClassGroup(),xGroup.getCode());
			if(debugCreation) {logger.debug("Existing "+fbReport.getClassGroup());}
		}
		catch (JeeslNotFoundException e)
		{
			if(debugCreation) {logger.debug("New "+fbReport.getClassGroup());}
			eGroup = efGroup.build(fReport,eSheet,xGroup);
			eGroup = fReport.save(eGroup);
		}
		eGroup = efGroup.update(fReport,eGroup, xGroup);
		eGroup = fReport.save(eGroup);
		eGroup = efGroup.updateLD(fReport,eGroup, xGroup);
		eGroup = fReport.load(eGroup);
		
		JeeslDbCodeEjbUpdater<COLUMN> dbUpdaterColumn = JeeslDbCodeEjbUpdater.createFactory(fbReport.getClassColumn());
		dbUpdaterColumn.dbEjbs(eGroup.getColumns());
		for(XlsColumn xColumn : xGroup.getXlsColumn())
		{
			COLUMN eColumn = importColumn(eGroup,xColumn);
			dbUpdaterColumn.handled(eColumn);
		}
		for(COLUMN c : dbUpdaterColumn.getEjbForRemove()){fReport.rmColumn(c);}
		
		return eGroup;
	}
	
	private COLUMN importColumn(GROUP eGroup, XlsColumn xColumn) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException, ExlpXpathNotFoundException
	{
		COLUMN eColumn;
		try
		{
			eColumn = fReport.fByCode(fbReport.getClassColumn(), xColumn.getCode());
			if(debugCreation) {logger.debug("Existing "+fbReport.getClassColumn());}
		}
		catch (JeeslNotFoundException e)
		{
			if(debugCreation) {logger.debug("NEW "+fbReport.getClassColumn());}
			eColumn = efColumn.build(fReport,eGroup,xColumn);
			eColumn = fReport.save(eColumn);
		}
		efColumn.update(fReport,eGroup,eColumn,xColumn);
		eColumn = fReport.save(eColumn);
		eColumn = efColumn.updateLD(fReport,eColumn,xColumn);
		return eColumn;
	}
}