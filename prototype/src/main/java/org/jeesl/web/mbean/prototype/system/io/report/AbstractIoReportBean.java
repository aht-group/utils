package org.jeesl.web.mbean.prototype.system.io.report;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class AbstractIoReportBean <L extends JeeslLang,D extends JeeslDescription,
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
							
							CDT extends JeeslStatus<CDT,L,D>,
							CW extends JeeslStatus<CW,L,D>,
							RT extends JeeslStatus<RT,L,D>,
							ENTITY extends EjbWithId,
							ATTRIBUTE extends EjbWithId,
							TL extends JeeslTrafficLight<L,D,TLS>,
							TLS extends JeeslStatus<TLS,L,D>,
							FILLING extends JeeslStatus<FILLING,L,D>,
							TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>,
							RCAT extends JeeslRevisionCategory<L,D,RCAT,?>,
							RE extends JeeslRevisionEntity<L,D,RCAT,?,RA,?>,
							RA extends JeeslRevisionAttribute<L,D,RE,?,CDT>
							>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractIoReportBean.class);
	
	protected JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport;
	protected final ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport;
	
	protected AbstractIoReportBean(ReportFactoryBuilder<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,RCAT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fbReport)
	{
		super(fbReport.getClassL(),fbReport.getClassD());
		this.fbReport=fbReport;
	}
	
	protected void initSuperReport(JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage, JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fReport=fReport;
	}
}