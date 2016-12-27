package org.jeesl.factory.xls.system.io.report;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsCellStyleProvider<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RT,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								RT extends UtilsStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XlsCellStyleProvider.class);
		
	private CellStyle styleFallback; public CellStyle getStyleFallback() {return styleFallback;}
	
	private CellStyle styleLabelCenter; public CellStyle getStyleLabelCenter() {return styleLabelCenter;}
	private CellStyle styleLabelLeft; public CellStyle getStyleLabelLeft() {return styleLabelLeft;}

	public XlsCellStyleProvider(Workbook xlsWorkbook, List<COLUMN> ioColumns, List<ROW> ioRows)
	{
		styleFallback = xlsWorkbook.createCellStyle();
//        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        styleFallback.setAlignment(CellStyle.ALIGN_LEFT);
        styleFallback.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
 //       dateHeaderStyle.setFont(font);
        
        styleLabelCenter = xlsWorkbook.createCellStyle();
        styleLabelCenter.setAlignment(CellStyle.ALIGN_CENTER);
        styleLabelCenter.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        styleLabelLeft = xlsWorkbook.createCellStyle();
        styleLabelLeft.setAlignment(CellStyle.ALIGN_LEFT);
        styleLabelLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	}
	
	public CellStyle get(COLUMN column)
	{
		return styleFallback;
	}
	
	public CellStyle get(ROW row)
	{
		return styleFallback;
	}
}